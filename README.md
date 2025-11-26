# knowledge demonstarted

A complete, well-structured Java implementation of the classic 1-2 Nim game. This project includes both a text-based interface and a Swing GUI, unit tests, and save/load/undo features. 

Highlights
- Two front ends:
  - Text-based UI (console) for quick play and easy integration with automated tests.
  - Swing-based GUI (GameGUI) for an interactive user experience.
- Save and load game state using Java serialization.
- Undo support implemented with a move history structure.
- Pluggable computer strategies (Strategy pattern): RandomStrategy and YourStrategy.
- Unit tests written with JUnit 5 for core classes (NimGame, Player, Move, strategies).
- Defensive input validation and exception handling around I/O and user interaction.

What this project demonstrates (skills and practices)
- Object-Oriented Design: clear separation of responsibilities (NimGame core, Player/Strategy abstraction, UI layers).
- Design Patterns: Strategy pattern to swap computer behaviors cleanly.
- State Management & Persistence: serialization, reinitializing transient fields, and restoring runtime-only resources.
- Testing: unit tests covering constructor behavior, key methods, persistence, and undo.
- GUI programming: Swing-based GUI that integrates with the same game core used by the CLI.
- Robustness: validation, defensive coding for save/load, and user prompts that guide valid moves.

Core classes quick overview
- NimGame: the game core — holds state (marble count, whose turn), move history (undo), logs, save/load, and reset.
- Player: a participant with optional MoveStrategy (human players may use GUI/console strategies).
- Move: a serializable record of a move for undo functionality.
- MoveStrategy: strategy interface implemented by RandomStrategy, YourStrategy, and GUI/console human strategies.
- TextBasedUI: console-based game loop, uses HumanUserStrategy for user input.
- GameGUI: Swing UI that shows marbles, allows removing 1 or 2, and manages save/load/undo interactions.

Run it locally
Note: filenames include spaces in the repository ("NIM-GAME Final"). From the repository root you may want to cd into that folder first.

1. Change into the source folder:
```bash
cd "NIM-GAME Final"
```

2. Compile:
```bash
javac *.java
```

3. Run the text UI:
```bash
java TextBasedUI
```

4. Or run the GUI:
```bash
java GameGUI
```

Notes:
- Saving writes to `save_game.dat` in the working directory via Java object serialization.
- The GUI uses buttons to make human moves; the human move implementation for GUI throws an UnsupportedOperationException in nextMove(), because GUI-driven interactions trigger moves directly through GUI controls.


If you prefer a quick CLI test run without a build tool, download the JUnit platform console JAR and run tests on the classpath (IDE approach is simplest for most users).

Design & implementation notes (why these choices)
- Strategy pattern for computer player logic keeps NimGame free of AI code and makes adding smarter strategies straightforward (e.g., minimax/dynamic programming).
- Move history (stack/ArrayList) enables reliable undo that can revert human+computer moves as needed.
- Transient fields (Scanner/Random) are reinitialized after serialization so loading a saved game restores runtime-only resources correctly.
- Unit tests focus on behavioral correctness (state changes, save/load symmetry, undo semantics) — a habit I follow to keep refactors safe.

Possible improvements and extensions
- Add an optimal AI (minimax or DP) as an additional MoveStrategy.
- Support multiple piles or configurable rules (higher max remove, multiple heaps).
- Enhance save/load UX (named save slots, file picker).
- Add CI (GitHub Actions) to run tests on push and a build file (Maven/Gradle) for reproducible builds.



Contributing and contact
Feel free to open issues or PRs if you'd like changes; I welcome feedback and will iterate quickly.
