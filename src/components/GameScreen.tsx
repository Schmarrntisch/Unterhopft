"use client";

import { useState, useCallback } from "react";
import { motion, AnimatePresence, type PanInfo } from "framer-motion";
import type { GameCard, ActiveVirus } from "@/lib/types";
import Image from "next/image";

interface GameScreenProps {
  currentCard: GameCard | null;
  currentIndex: number;
  totalCards: number;
  progress: number;
  activeViruses: ActiveVirus[];
  showVirusDrawer: boolean;
  setShowVirusDrawer: (v: boolean) => void;
  onNext: () => void;
  onPrev: () => void;
  onQuit: () => void;
}

const SWIPE_THRESHOLD = 50;

function triggerHaptic() {
  if (typeof navigator !== "undefined" && "vibrate" in navigator) {
    navigator.vibrate(15);
  }
}

import { CATEGORY_LABELS } from "@/lib/constants";

export default function GameScreen({
  currentCard,
  currentIndex,
  totalCards,
  progress,
  activeViruses,
  showVirusDrawer,
  setShowVirusDrawer,
  onNext,
  onPrev,
  onQuit,
}: GameScreenProps) {
  const [direction, setDirection] = useState(0);

  const handleDragEnd = useCallback(
    (_: MouseEvent | TouchEvent | PointerEvent, info: PanInfo) => {
      if (info.offset.x < -SWIPE_THRESHOLD) {
        setDirection(1);
        triggerHaptic();
        onNext();
      } else if (info.offset.x > SWIPE_THRESHOLD && currentIndex > 0) {
        setDirection(-1);
        triggerHaptic();
        onPrev();
      }
    },
    [onNext, onPrev, currentIndex]
  );

  const handleTap = useCallback(() => {
    setDirection(1);
    triggerHaptic();
    onNext();
  }, [onNext]);

  if (!currentCard) return null;

  const categoryLabel = CATEGORY_LABELS[currentCard.category] ?? currentCard.category;
  const isVirusType = currentCard.category === "virus" || currentCard.category === "group_virus";
  const isStart = currentCard.type === "virus_start";
  const isEnd = currentCard.type === "virus_end";

  return (
    <div className="flex flex-col min-h-dvh">
      {/* Header */}
      <div className="flex items-center justify-between px-4 py-3 bg-[var(--color-surface)] dark:bg-[var(--color-surface-dark)] shadow-sm">
        <button
          onClick={onQuit}
          className="text-[var(--color-text-muted)] dark:text-[var(--color-text-muted-dark)] font-display font-semibold text-sm"
        >
          Beenden
        </button>
        <Image src="/logo.png" alt="Logo" width={32} height={32} className="rounded-lg" />
        {activeViruses.length > 0 ? (
          <button
            onClick={() => setShowVirusDrawer(!showVirusDrawer)}
            className="relative px-3 py-1 bg-[var(--color-c2)] text-white rounded-2xl text-xs font-bold font-display"
          >
            Viren ({activeViruses.length})
          </button>
        ) : (
          <div className="w-16" />
        )}
      </div>

      {/* Progress bar */}
      <div className="h-1 bg-gray-200 dark:bg-gray-700">
        <motion.div
          className="h-full bg-[var(--color-c1)]"
          initial={{ width: 0 }}
          animate={{ width: `${progress}%` }}
          transition={{ duration: 0.3 }}
        />
      </div>

      {/* Card area */}
      <div className="flex-1 flex items-center justify-center px-4 py-6 overflow-hidden">
        <AnimatePresence mode="popLayout" custom={direction}>
          <motion.div
            key={currentCard.id}
            custom={direction}
            variants={{
              enter: (d: number) => ({ x: d >= 0 ? 300 : -300, opacity: 0, rotate: d >= 0 ? 10 : -10 }),
              center: { x: 0, opacity: 1, rotate: 0 },
              exit: (d: number) => ({ x: d >= 0 ? -300 : 300, opacity: 0, rotate: d >= 0 ? -10 : 10 }),
            }}
            initial="enter"
            animate="center"
            exit="exit"
            transition={{ type: "spring", stiffness: 300, damping: 30 }}
            drag="x"
            dragConstraints={{ left: 0, right: 0 }}
            dragElastic={0.2}
            onDragEnd={handleDragEnd}
            onClick={handleTap}
            className="w-full max-w-md cursor-grab active:cursor-grabbing"
            style={{ touchAction: "pan-y" }}
          >
            <div
              className="rounded-2xl p-8 shadow-xl min-h-[50vh] flex flex-col justify-between"
              style={{ backgroundColor: currentCard.color }}
            >
              {/* Category badge */}
              <div className="flex items-center justify-between mb-6">
                <span className="px-3 py-1 bg-white/20 rounded-2xl text-white text-xs font-bold font-display uppercase tracking-wide">
                  {categoryLabel}
                </span>
                {isVirusType && (
                  <span className="px-3 py-1 bg-white/20 rounded-2xl text-white text-xs font-bold font-display">
                    {isStart ? "START" : isEnd ? "ENDE" : ""}
                  </span>
                )}
              </div>

              {/* Card text */}
              <div className="flex-1 flex items-center">
                <p className="text-white text-xl leading-relaxed font-body font-medium">
                  {currentCard.text}
                </p>
              </div>

              {/* Card counter */}
              <div className="text-white/60 text-sm font-display text-right mt-6">
                {currentIndex + 1} / {totalCards}
              </div>
            </div>
          </motion.div>
        </AnimatePresence>
      </div>

      {/* Virus Drawer */}
      <AnimatePresence>
        {showVirusDrawer && activeViruses.length > 0 && (
          <motion.div
            initial={{ y: "100%" }}
            animate={{ y: 0 }}
            exit={{ y: "100%" }}
            transition={{ type: "spring", stiffness: 300, damping: 30 }}
            className="fixed bottom-0 left-0 right-0 bg-[var(--color-surface)] dark:bg-[var(--color-surface-dark)] rounded-t-3xl shadow-2xl p-6 max-h-[60vh] overflow-y-auto z-50"
          >
            <div className="flex items-center justify-between mb-4">
              <h3 className="font-display font-bold text-lg">Aktive Regeln</h3>
              <button
                onClick={() => setShowVirusDrawer(false)}
                className="w-8 h-8 flex items-center justify-center text-[var(--color-text-muted)] dark:text-[var(--color-text-muted-dark)] text-xl"
              >
                &times;
              </button>
            </div>
            <div className="space-y-3">
              {activeViruses.map((v) => (
                <div
                  key={v.id}
                  className="p-4 rounded-2xl text-white text-sm font-body"
                  style={{
                    backgroundColor:
                      v.category === "virus" ? "var(--color-c1)" : "var(--color-c2)",
                  }}
                >
                  <p className="mb-2">{v.text}</p>
                  <p className="text-white/70 text-xs font-display">
                    Noch {v.remainingRounds} {v.remainingRounds === 1 ? "Karte" : "Karten"}
                  </p>
                </div>
              ))}
            </div>
          </motion.div>
        )}
      </AnimatePresence>
    </div>
  );
}
