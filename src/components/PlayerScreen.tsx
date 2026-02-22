"use client";

import { useState } from "react";
import { motion, AnimatePresence } from "framer-motion";

interface PlayerScreenProps {
  players: string[];
  setPlayers: (p: string[]) => void;
  onNext: () => void;
  onBack: () => void;
}

const MIN_PLAYERS = 3;

export default function PlayerScreen({
  players,
  setPlayers,
  onNext,
  onBack,
}: PlayerScreenProps) {
  const [input, setInput] = useState("");
  const [error, setError] = useState("");

  const addPlayer = () => {
    const name = input.trim();
    if (!name) return;
    if (players.includes(name)) {
      setError("Name bereits vergeben!");
      return;
    }
    setPlayers([...players, name]);
    setInput("");
    setError("");
  };

  const removePlayer = (idx: number) => {
    setPlayers(players.filter((_, i) => i !== idx));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    addPlayer();
  };

  const canProceed = players.length >= MIN_PLAYERS;

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

      <h2 className="text-3xl font-bold font-display mb-6">Spieler</h2>

      <form onSubmit={handleSubmit} className="flex gap-3 mb-4">
        <input
          type="text"
          value={input}
          onChange={(e) => setInput(e.target.value)}
          placeholder="Name eingeben..."
          className="flex-1 px-4 py-3 rounded-2xl bg-[var(--color-surface)] dark:bg-[var(--color-surface-dark)] border-2 border-transparent focus:border-[var(--color-c1)] outline-none font-body text-lg"
          autoFocus
        />
        <button
          type="submit"
          className="px-5 py-3 bg-[var(--color-c4)] text-white rounded-2xl font-bold font-display text-lg active:scale-95 transition-transform"
        >
          +
        </button>
      </form>

      {error && (
        <p className="text-[var(--color-c1)] text-sm mb-2 font-body">{error}</p>
      )}

      <div className="flex-1 space-y-2 mb-6 overflow-y-auto">
        <AnimatePresence>
          {players.map((name, i) => (
            <motion.div
              key={name}
              initial={{ x: 50, opacity: 0 }}
              animate={{ x: 0, opacity: 1 }}
              exit={{ x: -50, opacity: 0 }}
              className="flex items-center justify-between px-4 py-3 bg-[var(--color-surface)] dark:bg-[var(--color-surface-dark)] rounded-2xl shadow-sm"
            >
              <span className="font-body text-lg font-medium">{name}</span>
              <button
                onClick={() => removePlayer(i)}
                className="w-8 h-8 flex items-center justify-center text-[var(--color-c1)] font-bold text-xl rounded-full hover:bg-red-50 dark:hover:bg-red-900/20 transition-colors"
              >
                &times;
              </button>
            </motion.div>
          ))}
        </AnimatePresence>
      </div>

      {!canProceed && players.length > 0 && (
        <p className="text-center text-[var(--color-c6)] font-body text-sm mb-4">
          Ihr müsst mindestens zu dritt sein! Such dir ein paar Freunde!
        </p>
      )}

      <button
        onClick={onNext}
        disabled={!canProceed}
        className={`w-full py-4 rounded-2xl font-bold font-display text-xl text-white transition-all ${
          canProceed
            ? "bg-[var(--color-c1)] active:scale-95 shadow-lg"
            : "bg-gray-300 dark:bg-gray-700 cursor-not-allowed"
        }`}
      >
        Los geht&apos;s!
      </button>
    </motion.div>
  );
}
