# Control Module Class Diagram

The control module orchestrates the game flow, manages battle logic, and defines different game modes.

```mermaid
classDiagram
    %% --- Core Control Classes ---
    class GameManager {
        -ui: UserInterface
        +startGame()
        -runMode(...) boolean
    }

    class BattleEngine {
        -ui: UserInterface
        -turnStrategy: TurnOrderStrategy
        -level: Level
        -player: Player
        -allCombatants: List~Combatant~
        -currentRound: int
        -levelNumber: int
        -enemiesKilled: int
        +startBattle() boolean
        +takeTurn(Combatant)
        +isBattleOver() boolean
        +getRound() int
    }

    class Registry~T~ {
        <<abstract>>
        -entries: Map~String, Entry~
        +register(Class~T~)
        +getNames() List~String~
        +getType(int) Class~T~
    }

    class GameModeRegistry {
        -instance: GameModeRegistry
        +getInstance() GameModeRegistry
    }
    Registry <|-- GameModeRegistry

    %% --- Game Modes ---
    class GameMode {
        <<abstract>>
        #levelGenerator: LevelGenerator
        +getName() String
        +getDescription() String
        +getPlayerSelection(UserInterface)
        +getItemSelection(UserInterface)
        +getWeaponSelection(UserInterface)
        +getArtifactSelection(UserInterface)
        +isBattleOver(BattleEngine) boolean
        +onRoundEnd(BattleEngine, UserInterface)*
    }

    class StoryMode { }
    class SurvivalMode { }
    class ChallengeMode { }
    class TimedMode { }
    GameMode <|-- StoryMode
    GameMode <|-- SurvivalMode
    GameMode <|-- ChallengeMode
    GameMode <|-- TimedMode

    %% --- Level Generation ---
    class LevelGenerator {
        <<interface>>
        +iterator() Iterator~Level~
    }

    class StoryLevelGenerator { }
    class SurvivalLevelGenerator { }
    class ChallengeLevelGenerator { }
    class TimedLevelGenerator { }
    LevelGenerator <|.. StoryLevelGenerator
    LevelGenerator <|.. SurvivalLevelGenerator
    LevelGenerator <|.. ChallengeLevelGenerator
    LevelGenerator <|.. TimedLevelGenerator

    %% --- Turn Strategy ---
    class TurnOrderStrategy {
        <<interface>>
        +determineTurnOrder(List~Combatant~) List~Combatant~
    }

    class SpeedBasedTurnOrder {
        +determineTurnOrder(List~Combatant~) List~Combatant~
    }
    TurnOrderStrategy <|.. SpeedBasedTurnOrder

    %% --- Relationships ---
    GameManager "1" --> "1" UserInterface
    GameManager ..> BattleEngine
    GameManager ..> GameMode
    
    BattleEngine "1" --> "1" UserInterface
    BattleEngine "1" --> "1" TurnOrderStrategy
    BattleEngine "1" --> "1" Level
    BattleEngine "1" --> "1" Player
    
    GameMode "1" *-- "1" LevelGenerator
    GameModeRegistry ..> GameMode
```

### Module Responsibilities:
- **`GameManager`**: The top-level orchestrator. It manages the main game loop, setup, and mode transitions.
- **`BattleEngine`**: The core logic engine for combat encounters. It handles turn sequencing, wave spawning, and win/loss detection.
- **`GameMode`**: An abstraction representing a specific gameplay experience (e.g., fixed loadout, endless waves). It delegates level generation to a `LevelGenerator`.
- **`Registry`**: A generic system for dynamically discovering and creating game components (players, modes, items) at runtime.
- **`TurnOrderStrategy`**: Employs the Strategy pattern to allow different ways of calculating turn sequences (e.g., speed-based, initiative-based).
