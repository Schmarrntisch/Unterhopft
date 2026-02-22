"use client";

import { motion } from "framer-motion";
import Image from "next/image";
import { APP_NAME } from "@/lib/constants";

interface HomeScreenProps {
  onStart: () => void;
  onSettings: () => void;
  onInfo: () => void;
}

export default function HomeScreen({ onStart, onSettings, onInfo }: HomeScreenProps) {
  return (
    <motion.div
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      exit={{ opacity: 0 }}
      className="flex flex-col items-center justify-center min-h-dvh px-6 gap-8"
    >
      <motion.div
        initial={{ scale: 0.8, opacity: 0 }}
        animate={{ scale: 1, opacity: 1 }}
        transition={{ type: "spring", stiffness: 200, damping: 20 }}
      >
        <Image
          src="/main_menu.png"
          alt={APP_NAME}
          width={320}
          height={320}
          className="rounded-2xl"
          priority
        />
      </motion.div>

      <motion.h1
        className="text-4xl font-bold font-display text-center"
        initial={{ y: 20, opacity: 0 }}
        animate={{ y: 0, opacity: 1 }}
        transition={{ delay: 0.2 }}
      >
        {APP_NAME}
      </motion.h1>

      <motion.div
        className="flex flex-col gap-3 w-full max-w-xs"
        initial={{ y: 20, opacity: 0 }}
        animate={{ y: 0, opacity: 1 }}
        transition={{ delay: 0.4 }}
      >
        <motion.button
          onClick={onStart}
          className="w-full py-4 px-8 bg-[var(--color-c1)] text-white text-xl font-bold font-display rounded-2xl shadow-lg active:scale-95 transition-transform"
          whileTap={{ scale: 0.95 }}
        >
          Spiel starten
        </motion.button>

        <motion.button
          onClick={onSettings}
          className="w-full py-3 px-8 bg-[var(--color-surface)] dark:bg-[var(--color-surface-dark)] text-lg font-bold font-display rounded-2xl shadow-sm active:scale-95 transition-transform"
          whileTap={{ scale: 0.95 }}
        >
          Einstellungen
        </motion.button>

        <motion.button
          onClick={onInfo}
          className="w-full py-3 px-8 bg-[var(--color-surface)] dark:bg-[var(--color-surface-dark)] text-lg font-bold font-display rounded-2xl shadow-sm active:scale-95 transition-transform"
          whileTap={{ scale: 0.95 }}
        >
          Info
        </motion.button>
      </motion.div>
    </motion.div>
  );
}
