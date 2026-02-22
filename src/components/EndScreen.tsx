"use client";

import { motion } from "framer-motion";
import Image from "next/image";

interface EndScreenProps {
  onRestart: () => void;
  onHome: () => void;
}

export default function EndScreen({ onRestart, onHome }: EndScreenProps) {
  return (
    <motion.div
      initial={{ opacity: 0, scale: 0.9 }}
      animate={{ opacity: 1, scale: 1 }}
      exit={{ opacity: 0 }}
      className="flex flex-col items-center justify-center min-h-dvh px-6 gap-8"
    >
      <motion.div
        initial={{ y: -20, opacity: 0 }}
        animate={{ y: 0, opacity: 1 }}
        transition={{ delay: 0.2, type: "spring" }}
      >
        <Image
          src="/game_ended.png"
          alt="Spiel vorbei"
          width={280}
          height={280}
          className="rounded-2xl"
        />
      </motion.div>

      <motion.h2
        className="text-3xl font-bold font-display text-center"
        initial={{ y: 20, opacity: 0 }}
        animate={{ y: 0, opacity: 1 }}
        transition={{ delay: 0.4 }}
      >
        Spiel vorbei!
      </motion.h2>

      <motion.p
        className="text-[var(--color-text-muted)] dark:text-[var(--color-text-muted-dark)] font-body text-center"
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ delay: 0.6 }}
      >
        Hoffentlich seid ihr noch nüchtern genug, das hier zu lesen.
      </motion.p>

      <motion.div
        className="flex flex-col gap-3 w-full max-w-xs"
        initial={{ y: 20, opacity: 0 }}
        animate={{ y: 0, opacity: 1 }}
        transition={{ delay: 0.8 }}
      >
        <button
          onClick={onRestart}
          className="w-full py-4 bg-[var(--color-c1)] text-white rounded-2xl font-bold font-display text-xl shadow-lg active:scale-95 transition-transform"
        >
          Nochmal spielen
        </button>
        <button
          onClick={onHome}
          className="w-full py-4 bg-[var(--color-surface)] dark:bg-[var(--color-surface-dark)] rounded-2xl font-bold font-display text-lg shadow-sm active:scale-95 transition-transform"
        >
          Zum Menü
        </button>
      </motion.div>
    </motion.div>
  );
}
