"use client";

import { useEffect } from "react";
import { AnimatePresence } from "framer-motion";
import { useGameStore } from "@/lib/game-store";
import HomeScreen from "@/components/HomeScreen";
import PlayerScreen from "@/components/PlayerScreen";
import SettingsScreen from "@/components/SettingsScreen";
import InfoScreen from "@/components/InfoScreen";
import GameScreen from "@/components/GameScreen";
import EndScreen from "@/components/EndScreen";

export default function Home() {
  const store = useGameStore();

  useEffect(() => {
    fetch("/api/categories")
      .then((r) => r.json())
      .then((cats) => {
        store.setCategories(cats);
        store.initCardCounts(cats);
      });
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <main className="max-w-lg mx-auto min-h-dvh">
      <AnimatePresence mode="wait">
        {store.screen === "home" && (
          <HomeScreen
            key="home"
            onStart={() => store.setScreen("players")}
            onSettings={() => store.setScreen("settings")}
            onInfo={() => store.setScreen("info")}
          />
        )}

        {store.screen === "players" && (
          <PlayerScreen
            key="players"
            players={store.players}
            setPlayers={store.setPlayers}
            onNext={store.startGame}
            onBack={() => store.setScreen("home")}
          />
        )}

        {store.screen === "settings" && (
          <SettingsScreen
            key="settings"
            settings={store.settings}
            setSettings={store.setSettings}
            categories={store.categories}
            onBack={() => store.setScreen("home")}
          />
        )}

        {store.screen === "info" && (
          <InfoScreen
            key="info"
            onBack={() => store.setScreen("home")}
          />
        )}

        {store.screen === "game" && (
          <GameScreen
            key="game"
            currentCard={store.currentCard}
            currentIndex={store.currentIndex}
            totalCards={store.deck.length}
            progress={store.progress}
            activeViruses={store.activeViruses}
            showVirusDrawer={store.showVirusDrawer}
            setShowVirusDrawer={store.setShowVirusDrawer}
            onNext={store.nextCard}
            onPrev={store.prevCard}
            onQuit={store.resetGame}
          />
        )}

        {store.screen === "end" && (
          <EndScreen
            key="end"
            onRestart={store.startGame}
            onHome={store.resetGame}
          />
        )}
      </AnimatePresence>
    </main>
  );
}
