import fs from "fs";
import path from "path";
import type { RawCard } from "./types";

export interface CategoryData {
  filename: string;
  category: string;
  cards: RawCard[];
}

export function loadAllCategories(): CategoryData[] {
  const textsDir = path.join(process.cwd(), "texts");
  const files = fs.readdirSync(textsDir).filter((f) => f.endsWith(".json"));

  return files.map((file) => {
    const raw = fs.readFileSync(path.join(textsDir, file), "utf-8");
    const cards: RawCard[] = JSON.parse(raw);
    const category = cards[0]?.category ?? file.replace(".json", "");
    return { filename: file.replace(".json", ""), category, cards };
  });
}
