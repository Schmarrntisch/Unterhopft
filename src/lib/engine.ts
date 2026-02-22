import type {
  RawCard,
  ComparisonCard,
  VirusCard,
  SimpleCard,
  GameCard,
  ActiveVirus,
  GameSettings,
} from "./types";
import type { CategoryData } from "./data-loader";

// ── Color system ──
const COLORS_LIGHT = [
  "var(--color-c1)", // index 0 = virus
  "var(--color-c2)", // index 1 = group_virus
  "var(--color-c3)",
  "var(--color-c4)",
  "var(--color-c5)",
  "var(--color-c6)",
  "var(--color-c7)",
  "var(--color-c8)",
];

// ── Helpers ──
function shuffle<T>(arr: T[]): T[] {
  const a = [...arr];
  for (let i = a.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [a[i], a[j]] = [a[j], a[i]];
  }
  return a;
}

function randomInt(min: number, max: number): number {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

let cardIdCounter = 0;
function nextId(): string {
  return `card-${cardIdCounter++}`;
}

function getRandomSips(settings: GameSettings): number {
  return randomInt(settings.minSips, settings.maxSips);
}

function formatSips(n: number): string {
  return n === 1 ? "1 Schluck" : `${n} Schlücke`;
}

/** Create a stateful player assigner that avoids repeating player1 too often */
function createPlayerAssigner(players: string[]) {
  const lastAsPlayer1 = new Map<string, number>();
  const cooldown = Math.max(1, Math.floor(players.length / 2));

  return function assignPlayers(cardIndex: number): Record<string, string> {
    // Filter to players not recently used as player1
    const eligible = players.filter((p) => {
      const last = lastAsPlayer1.get(p);
      return last === undefined || cardIndex - last >= cooldown;
    });
    const pool = eligible.length > 0 ? eligible : players;

    // Pick player1 randomly from eligible pool
    const player1 = pool[Math.floor(Math.random() * pool.length)];
    lastAsPlayer1.set(player1, cardIndex);

    // player2 and player3 from remaining players (shuffled)
    const remaining = shuffle(players.filter((p) => p !== player1));
    const mapping: Record<string, string> = { "{{player1}}": player1 };
    if (remaining.length >= 1) mapping["{{player2}}"] = remaining[0];
    if (remaining.length >= 2) mapping["{{player3}}"] = remaining[1];
    return mapping;
  };
}

function replacePlaceholders(
  text: string,
  playerMapping: Record<string, string>,
  settings: GameSettings
): string {
  let result = text;
  const sips = getRandomSips(settings);
  result = result.replace(/\{\{sips\}\}/g, formatSips(sips));
  for (const [placeholder, name] of Object.entries(playerMapping)) {
    result = result.replaceAll(placeholder, name);
  }
  return result;
}

function getColorForCategory(
  category: string,
  lastColorIdx: number
): { color: string; newIdx: number } {
  if (category === "virus") return { color: COLORS_LIGHT[0], newIdx: lastColorIdx };
  if (category === "group_virus") return { color: COLORS_LIGHT[1], newIdx: lastColorIdx };
  // Cycle through colors 3-8 (indices 2-7), no immediate repeat
  let idx = lastColorIdx;
  do {
    idx = randomInt(2, 7);
  } while (idx === lastColorIdx);
  return { color: COLORS_LIGHT[idx], newIdx: idx };
}

// ── Deck builder ──
function isComparison(card: RawCard): card is ComparisonCard {
  return card.category === "comparison" && "text1" in card;
}

function isVirus(card: RawCard): card is VirusCard {
  return (card.category === "virus" || card.category === "group_virus") && "text_start" in card;
}

export function buildDeck(
  categories: CategoryData[],
  players: string[],
  settings: GameSettings
): { deck: GameCard[]; lastColorIndex: number } {
  cardIdCounter = 0;

  // Build a pool using absolute card counts per category
  const pool: { card: RawCard; category: string }[] = [];
  for (const cat of categories) {
    const count = settings.cardCounts[cat.filename] ?? 0;
    if (count === 0) continue;
    const shuffledCards = shuffle(cat.cards);
    for (let i = 0; i < count && i < shuffledCards.length; i++) {
      pool.push({ card: shuffledCards[i], category: cat.category });
    }
  }

  if (pool.length === 0) return { deck: [], lastColorIndex: 2 };

  // Separate virus cards from normal cards
  const virusItems = pool.filter(({ card }) => isVirus(card));
  const normalItems = pool.filter(({ card }) => !isVirus(card));

  // Shuffle each group independently
  const shuffledNormal = shuffle(normalItems);
  const shuffledViruses = shuffle(virusItems);

  // Place virus start cards in the 30-70% zone of the deck, evenly spaced
  const zoneStart = Math.floor(shuffledNormal.length * 0.30);
  const zoneEnd = Math.floor(shuffledNormal.length * 0.70);
  const zoneLength = zoneEnd - zoneStart;

  if (shuffledViruses.length > 0 && zoneLength > 0) {
    const spacing = zoneLength / (shuffledViruses.length + 1);
    const positions: number[] = [];
    for (let v = 0; v < shuffledViruses.length; v++) {
      const basePos = zoneStart + Math.round(spacing * (v + 1));
      const jitter = Math.max(1, Math.floor(spacing * 0.2));
      const offset = randomInt(-jitter, jitter);
      positions.push(Math.max(zoneStart, Math.min(zoneEnd, basePos + offset)));
    }
    // Insert from back to front so earlier indices stay stable
    positions.sort((a, b) => b - a);
    for (let v = 0; v < positions.length; v++) {
      shuffledNormal.splice(positions[v], 0, shuffledViruses[v]);
    }
  } else {
    // Fallback: insert viruses near the middle
    const mid = Math.floor(shuffledNormal.length / 2);
    for (const v of shuffledViruses) {
      shuffledNormal.splice(mid, 0, v);
    }
  }

  const limited = shuffledNormal;

  // Convert to GameCards
  const deck: GameCard[] = [];
  let lastColorIdx = 2;
  const pendingVirusEnds: { card: GameCard; insertAfter: number }[] = [];
  const assignPlayersForCard = createPlayerAssigner(players);

  for (let i = 0; i < limited.length; i++) {
    const { card } = limited[i];
    const playerMapping = assignPlayersForCard(i);
    const { color, newIdx } = getColorForCategory(card.category, lastColorIdx);
    lastColorIdx = newIdx;

    if (isComparison(card)) {
      // Part A
      const cardA: GameCard = {
        id: nextId(),
        category: "comparison",
        text: replacePlaceholders(card.text1, playerMapping, settings),
        type: "comparison_a",
        linkedPlayers: playerMapping,
        color,
      };
      // Part B — same players
      const cardB: GameCard = {
        id: nextId(),
        category: "comparison",
        text: replacePlaceholders(card.text2, playerMapping, settings),
        type: "comparison_b",
        linkedPlayers: playerMapping,
        color,
      };
      deck.push(cardA, cardB);
    } else if (isVirus(card)) {
      // Start card
      const startCard: GameCard = {
        id: nextId(),
        category: card.category,
        text: replacePlaceholders(card.text_start, playerMapping, settings),
        type: "virus_start",
        linkedPlayers: playerMapping,
        color,
      };
      deck.push(startCard);

      // Schedule end card 3-8 rounds later (at least 5 cards distance)
      const duration = randomInt(3, 8);
      const minDistance = 5;
      const insertAt = deck.length - 1 + Math.max(duration, minDistance);

      const endCard: GameCard = {
        id: nextId(),
        category: card.category,
        text: replacePlaceholders(card.text_end, playerMapping, settings),
        type: "virus_end",
        linkedPlayers: playerMapping,
        color,
      };
      pendingVirusEnds.push({ card: endCard, insertAfter: insertAt });
    } else {
      const simpleCard = card as SimpleCard;
      deck.push({
        id: nextId(),
        category: simpleCard.category,
        text: replacePlaceholders(simpleCard.text, playerMapping, settings),
        type: "simple",
        color,
      });
    }
  }

  // Insert virus end cards at their scheduled positions
  // Sort by position descending so insertions don't shift earlier indices
  pendingVirusEnds.sort((a, b) => b.insertAfter - a.insertAfter);
  for (const { card, insertAfter } of pendingVirusEnds) {
    const pos = Math.min(insertAfter, deck.length);
    deck.splice(pos, 0, card);
  }

  return { deck, lastColorIndex: lastColorIdx };
}

/** Extract active viruses at a given card index */
export function getActiveViruses(deck: GameCard[], currentIndex: number): ActiveVirus[] {
  const active: ActiveVirus[] = [];

  for (let i = 0; i <= currentIndex; i++) {
    const card = deck[i];
    if (card.type === "virus_start") {
      // Find the corresponding end card
      const endIdx = deck.findIndex(
        (c, j) => j > i && c.type === "virus_end" && c.linkedPlayers === card.linkedPlayers && c.category === card.category
      );
      if (endIdx > currentIndex) {
        // Still active
        active.push({
          id: card.id,
          category: card.category as "virus" | "group_virus",
          text: card.text,
          remainingRounds: endIdx - currentIndex,
          endCard: deck[endIdx],
        });
      }
    }
  }

  return active;
}
