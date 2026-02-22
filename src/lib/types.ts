export interface SimpleCard {
  category: string;
  text: string;
}

export interface ComparisonCard {
  category: "comparison";
  text1: string;
  text2: string;
}

export interface VirusCard {
  category: "virus" | "group_virus";
  text_start: string;
  text_end: string;
}

export type RawCard = SimpleCard | ComparisonCard | VirusCard;

export interface GameCard {
  id: string;
  category: string;
  text: string;
  type: "simple" | "comparison_a" | "comparison_b" | "virus_start" | "virus_end";
  /** For virus_end / comparison_b: references the start card's assigned players */
  linkedPlayers?: Record<string, string>;
  /** For virus cards: the end text to schedule */
  endText?: string;
  color: string;
}

export interface ActiveVirus {
  id: string;
  category: "virus" | "group_virus";
  text: string;
  remainingRounds: number;
  endCard: GameCard;
}

export interface GameSettings {
  minSips: number;
  maxSips: number;
  cardCounts: Record<string, number>;
}

export interface GameState {
  players: string[];
  settings: GameSettings;
  deck: GameCard[];
  currentIndex: number;
  activeViruses: ActiveVirus[];
  lastColorIndex: number;
}
