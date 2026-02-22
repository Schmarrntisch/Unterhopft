"use client";

import { useMemo } from "react";
import { motion } from "framer-motion";
import type { GameSettings } from "@/lib/types";
import type { CategoryData } from "@/lib/data-loader";
import { CATEGORY_LABELS } from "@/lib/constants";

interface SettingsScreenProps {
  settings: GameSettings;
  setSettings: (s: GameSettings) => void;
  categories: CategoryData[];
  onBack: () => void;
}

export default function SettingsScreen({
  settings,
  setSettings,
  categories,
  onBack,
}: SettingsScreenProps) {
  const gameLength = useMemo(
    () => Object.values(settings.cardCounts).reduce((a, b) => a + b, 0),
    [settings.cardCounts]
  );

  const updateCardCount = (filename: string, value: number) => {
    setSettings({
      ...settings,
      cardCounts: { ...settings.cardCounts, [filename]: value },
    });
  };

  return (
    <motion.div
      initial={{ x: 300, opacity: 0 }}
      animate={{ x: 0, opacity: 1 }}
      exit={{ x: -300, opacity: 0 }}
      transition={{ type: "spring", stiffness: 300, damping: 30 }}
      className="flex flex-col min-h-dvh px-6 py-8"
    >
      <button
        onClick={onBack}
        className="self-start text-[var(--color-text-muted)] dark:text-[var(--color-text-muted-dark)] mb-4 font-display font-semibold"
      >
        &larr; Zurück
      </button>

      <h2 className="text-3xl font-bold font-display mb-6">Einstellungen</h2>

      <div className="space-y-6 flex-1 overflow-y-auto pb-6">
        {/* Sip range */}
        <div className="bg-[var(--color-surface)] dark:bg-[var(--color-surface-dark)] rounded-2xl p-5 shadow-sm">
          <h3 className="font-display font-bold text-lg mb-4">Schlücke</h3>

          <div className="space-y-3">
            <div>
              <div className="flex justify-between mb-1">
                <span className="text-sm font-body text-[var(--color-text-muted)] dark:text-[var(--color-text-muted-dark)]">Minimum</span>
                <span className="font-bold font-display">{settings.minSips}</span>
              </div>
              <input
                type="range"
                min={1}
                max={5}
                value={settings.minSips}
                onChange={(e) =>
                  setSettings({
                    ...settings,
                    minSips: Math.min(Number(e.target.value), settings.maxSips),
                  })
                }
                className="w-full accent-[var(--color-c1)]"
              />
            </div>

            <div>
              <div className="flex justify-between mb-1">
                <span className="text-sm font-body text-[var(--color-text-muted)] dark:text-[var(--color-text-muted-dark)]">Maximum</span>
                <span className="font-bold font-display">{settings.maxSips}</span>
              </div>
              <input
                type="range"
                min={2}
                max={10}
                value={settings.maxSips}
                onChange={(e) =>
                  setSettings({
                    ...settings,
                    maxSips: Math.max(Number(e.target.value), settings.minSips),
                  })
                }
                className="w-full accent-[var(--color-c1)]"
              />
            </div>
          </div>
        </div>

        {/* Category Card Counts */}
        <div className="bg-[var(--color-surface)] dark:bg-[var(--color-surface-dark)] rounded-2xl p-5 shadow-sm">
          <div className="flex justify-between items-center mb-4">
            <h3 className="font-display font-bold text-lg">Kategorien</h3>
            <span className="text-sm font-body text-[var(--color-text-muted)] dark:text-[var(--color-text-muted-dark)]">
              Spiellänge: <span className="font-bold font-display text-[var(--color-text)] dark:text-[var(--color-text-dark)]">{gameLength} Karten</span>
            </span>
          </div>
          <div className="space-y-3">
            {categories.map((cat) => (
              <div key={cat.filename}>
                <div className="flex justify-between mb-1">
                  <span className="text-sm font-body">
                    {CATEGORY_LABELS[cat.filename] ?? cat.filename}
                  </span>
                  <span className="text-sm font-bold font-display">
                    {settings.cardCounts[cat.filename] ?? 0}
                  </span>
                </div>
                <input
                  type="range"
                  min={0}
                  max={Math.min(15, cat.cards.length)}
                  value={settings.cardCounts[cat.filename] ?? 0}
                  onChange={(e) => updateCardCount(cat.filename, Number(e.target.value))}
                  className="w-full accent-[var(--color-c5)]"
                />
              </div>
            ))}
          </div>
        </div>
      </div>

      <button
        onClick={onBack}
        className="w-full py-4 bg-[var(--color-c1)] text-white rounded-2xl font-bold font-display text-xl shadow-lg active:scale-95 transition-transform mt-4"
      >
        Fertig
      </button>
    </motion.div>
  );
}
