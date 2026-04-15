# Entity Level Module Class Diagram

The `entity.level` module handles the configuration of battle stages, including enemy wave generation and difficulty scaling.

```mermaid
classDiagram
    %% --- Core Components ---
    class Level {
        -waveIterator: Iterator~List~Enemy~~
        +getInitialEnemies() List~Enemy~
        +isNextWaveAvailable() boolean
        +getNextWave() List~Enemy~
    }

    class Spawner {
        <<interface>>
        +getWaves() Iterator~List~Enemy~~
    }

    %% --- Implementations ---
    class DifficultySpawner {
        -difficulty: Difficulty
        +getWaves() Iterator~List~Enemy~~
        -getInitialGroup() Map
        -getBackupGroup() Map
    }
    Spawner <|.. DifficultySpawner

    class LevelBuilder {
        -waves: List~Map~
        -currentWave: Map
        +addEnemyToCurrentWave(Class~Enemy~) LevelBuilder
        +nextWave() LevelBuilder
        +getWaves() Iterator~List~Enemy~~
        +build() Level
    }
    Spawner <|.. LevelBuilder

    %% --- Enumerations ---
    class Difficulty {
        <<enumeration>>
        EASY
        MEDIUM
        HARD
        BOSS
    }

    %% --- Relationships ---
    Level "1" --> "1" Spawner : uses
    DifficultySpawner --> Difficulty
    DifficultySpawner ..> EnemyFactory : uses
    LevelBuilder ..> EnemyFactory : uses
    LevelBuilder ..> Level : creates
```

### Module Responsibilities:
- **`Level`**: A runtime container for a battle's progression. It doesn't know *how* enemies are created, only how to request the next wave from its `Spawner`.
- **`Spawner` Hierarchy**: Employs the Strategy pattern to define different enemy generation algorithms.
- **`DifficultySpawner`**: Implements fixed wave logic based on the `Difficulty` level. It maps difficulties to specific enemy compositions and wave counts.
- **`LevelBuilder`**: A implementation of the Builder pattern that allows for programmatic construction of custom levels with multiple waves.
- **`Difficulty`**: A central enumeration used by the control layer and the level module to scale game challenge.
