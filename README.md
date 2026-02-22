# Unterhopft

A mobile-first drinking game web app built with Next.js. Players take turns drawing cards with various challenges, rules, and mini-games — all in German.

## Features

- Card-based gameplay with 11+ categories (Virus, Democracy, Tongue Twisters, Mini-Games, and more)
- Dynamic player name insertion and sip counts
- Persistent virus/rule tracking across rounds
- Swipe controls with haptic feedback
- Installable as a Progressive Web App (PWA)
- Light and dark mode

## Tech Stack

- **Next.js 16** with App Router
- **React 19** & **TypeScript**
- **Tailwind CSS** & **Framer Motion**
- **Docker** for deployment

## Getting Started

### Prerequisites

- Node.js 20+

### Development

```bash
npm install
npm run dev
```

Open [http://localhost:3000](http://localhost:3000).

### Production

```bash
npm run build
npm start
```

### Docker

```bash
docker build -t unterhopft .
docker run -p 3000:3000 unterhopft
```

## Game Categories

| Category | Description |
|---|---|
| Brands | Name brands in a given category |
| Comparison | Vote on which player matches a description |
| Virus | Time-limited rule for one player |
| Group Virus | Time-limited rule for the whole group |
| Action Card | Direct challenges |
| Condition | Rules based on player traits |
| Democracy | Group votes — minority drinks |
| Dictator | One player sets the rules |
| Game | Mini-games (Rock-Paper-Scissors, Waterfall, etc.) |
| Most Likely | Vote on who's most likely to… |
| Tongue Twister | Pronunciation challenges |

Categories and card counts are fully customizable in the settings screen.
