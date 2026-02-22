"use client";

import { useCallback, useMemo, useState } from "react";
import type { GameCard, GameSettings, ActiveVirus } from "./types";
import type { CategoryData } from "./data-loader";
import { buildDeck, getActiveViruses } from "./engine";
import { DEFAULT_CARD_COUNTS } from "./constants";

export type Screen = "home" | "players" | "settings" | "info" | "game" | "end";

const DEFAULT_SETTINGS: GameSettings = {
  minSips: 1,
  maxSips: 3,
  cardCounts: { ...DEFAULT_CARD_COUNTS },
};

export function useGameStore() {
  const [screen, setScreen] = useState<Screen>("home");
  const [players, setPlayers] = useState<string[]>([]);
  const [settings, setSettings] = useState<GameSettings>(DEFAULT_SETTINGS);
  const [categories, setCategories] = useState<CategoryData[]>([]);
  const [deck, setDeck] = useState<GameCard[]>([]);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [showVirusDrawer, setShowVirusDrawer] = useState(false);

  const activeViruses: ActiveVirus[] = useMemo(
    () => (deck.length > 0 ? getActiveViruses(deck, currentIndex) : []),
    [deck, currentIndex]
  );

  const currentCard = deck[currentIndex] ?? null;
  const progress = deck.length > 0 ? ((currentIndex + 1) / deck.length) * 100 : 0;

  const gameLength = useMemo(
    () => Object.values(settings.cardCounts).reduce((a, b) => a + b, 0),
    [settings.cardCounts]
  );

  const initCardCounts = useCallback((cats: CategoryData[]) => {
    setSettings((s) => {
      const counts = { ...s.cardCounts };
      for (const cat of cats) {
        if (!(cat.filename in counts)) {
          counts[cat.filename] = DEFAULT_CARD_COUNTS[cat.filename] ?? 5;
        }
      }
      return { ...s, cardCounts: counts };
    });
  }, []);

  const startGame = useCallback(() => {
    const { deck: newDeck } = buildDeck(categories, players, settings);
    setDeck(newDeck);
    setCurrentIndex(0);
    setScreen("game");
  }, [categories, players, settings]);

  const nextCard = useCallback(() => {
    if (currentIndex + 1 >= deck.length) {
      setScreen("end");
    } else {
      setCurrentIndex((i) => i + 1);
    }
  }, [currentIndex, deck.length]);

  const prevCard = useCallback(() => {
    setCurrentIndex((i) => Math.max(0, i - 1));
  }, []);

  const resetGame = useCallback(() => {
    setDeck([]);
    setCurrentIndex(0);
    setScreen("home");
  }, []);

  return {
    screen,
    setScreen,
    players,
    setPlayers,
    settings,
    setSettings,
    categories,
    setCategories,
    deck,
    currentIndex,
    currentCard,
    progress,
    gameLength,
    activeViruses,
    showVirusDrawer,
    setShowVirusDrawer,
    initCardCounts,
    startGame,
    nextCard,
    prevCard,
    resetGame,
  };
}
