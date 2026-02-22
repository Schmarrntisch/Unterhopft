"use client";

import { motion } from "framer-motion";
import { APP_NAME } from "@/lib/constants";

interface InfoScreenProps {
  onBack: () => void;
}

export default function InfoScreen({ onBack }: InfoScreenProps) {
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

      <h2 className="text-3xl font-bold font-display mb-6">Info</h2>

      <div className="space-y-6 flex-1">
        <div className="bg-[var(--color-surface)] dark:bg-[var(--color-surface-dark)] rounded-2xl p-5 shadow-sm">
          <h3 className="font-display font-bold text-lg mb-3">Zum Homescreen hinzufügen</h3>
          <p className="font-body text-[var(--color-text-muted)] dark:text-[var(--color-text-muted-dark)] leading-relaxed">
            Öffne {APP_NAME} im Browser, tippe auf &quot;Teilen&quot; (iOS) oder das Menü (Android) und wähle &quot;Zum Homescreen hinzufügen&quot;. So hast du die App immer griffbereit.
          </p>
        </div>

        <div className="bg-[var(--color-surface)] dark:bg-[var(--color-surface-dark)] rounded-2xl p-5 shadow-sm">
          <h3 className="font-display font-bold text-lg mb-3">Hinweis</h3>
          <p className="font-body text-[var(--color-text-muted)] dark:text-[var(--color-text-muted-dark)] leading-relaxed">
            {APP_NAME} ist eine reine Spaß-App. Bitte trinkt verantwortungsvoll und kennt eure Grenzen. Niemand sollte zum Trinken gezwungen werden.
          </p>
        </div>

        <div className="bg-[var(--color-surface)] dark:bg-[var(--color-surface-dark)] rounded-2xl p-5 shadow-sm">
          <h3 className="font-display font-bold text-lg mb-3">Quellcode &amp; Hosting</h3>
          <p className="font-body text-[var(--color-text-muted)] dark:text-[var(--color-text-muted-dark)] leading-relaxed">
            Der Quellcode ist auf{" "}
            <a
              href="https://github.com/schmarrntisch/unterhopft"
              target="_blank"
              rel="noopener noreferrer"
              className="text-[var(--color-c3)] underline font-semibold"
            >
              GitHub
            </a>{" "}
            verfügbar. Deployed via{" "}
            <span className="font-semibold">Vercel</span>.
          </p>
        </div>
      </div>
    </motion.div>
  );
}
