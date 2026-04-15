# SC2002 Turn-based Combat Arena: Architecture & Design Explanation

This document provides an in-depth explanation of the design considerations, implementation details, and the thinking process behind the Turn-based Combat Arena, with a particular focus on how the project meets and exceeds the SC2002 assignment requirements.

## 1. Design Consideration [15 Marks]

### Usage of Object-Oriented Concepts and Principles
The system was designed from the ground up to embody the four core pillars of Object-Oriented Programming (OOP):

1.  **Abstraction:** We extensively use abstract classes (e.g., `Combatant`, `Action`, `StatusEffect`, `GameMode`) to define core behaviors and properties without dictating implementation details. For example, `Combatant` defines that every entity must be able to `chooseAction()` and `takeTurn()`, but leaves the exact logic up to the specific `Player` or `Enemy` subclass. This allows the core `BattleEngine` to treat all combatants uniformly. Interfaces like `Named` and `Describable` provide further abstraction, ensuring any object that needs a name or description can be handled generically.
2.  **Encapsulation:** Internal state is protected. The `Combatant` class does not expose its raw HP or stats directly for modification; instead, it uses methods like `takeDamage()`. More complex state management, such as tracking buffs and debuffs, is fully encapsulated within the `StatusManager`. The manager handles the logic of stacking, duration tracking, and event triggering, hiding this complexity from the `Combatant` class.
3.  **Inheritance:** The class hierarchy promotes high code reuse. `Warrior` and `Wizard` inherit from `Player`, which inherits from `Combatant`. The base `Combatant` class handles standard turn flow and HP management, while `Player` handles inventory and equipment logic, and `Warrior`/`Wizard` handle specific base stats and unique actions. This hierarchy is mirrored in `Enemy`, `StatusEffect`, and `Action`.
4.  **Polymorphism:** The `BattleEngine` relies heavily on polymorphism. When iterating through the turn order, the engine simply calls `combatant.takeTurn(context)`. The JVM dynamically resolves this to the appropriate subclass method. Similarly, when an action is executed via `action.execute(context)`, it could be a `ShieldBash`, a `FireBreath`, or a `Potion` use—the engine does not need to know.

### Explanation of Design Choices & Project Requirements
The core requirement was to build a turn-based system with extensible Actions, Items, Status Effects, and Turn Order Strategies.
1. Singleton Pattern
   * Where it's used: PlayerRegistry, ItemRegistry, EnemyRegistry, WeaponRegistry, ArtifactRegistry, GameModeRegistry, and ColourPaletteRegistry.
   * How it works: Each registry class has a private constructor, a private static instance, and a public getInstance() method.
   * Design Justification: In a game, you need a single "source of truth" for all available entities. Using the Singleton pattern ensures that there is only    
     ever one instance of each registry. This provides a global point of access to the game’s "catalog" while preventing the overhead and bugs associated with  
     passing registry instances between every class.

  2. Builder Pattern
   * Where it's used: OutputBuilder and PlayerFactory.
   * How it works:
       * OutputBuilder: It uses a Fluent API style where methods like append(), newLine(), and divider() return this. This allows for "chained" calls:
         builder.newLine().divider().append("Victory", color).print();.
       * PlayerFactory: Although named a "Factory," it functions as a Builder by allowing the GameManager to configure a player incrementally: new
         PlayerFactory().createPlayer(type).addItems(list).addEquipment(weapon).build();.
   * Design Justification:
       * Readability: Building complex multi-line CLI strings with ANSI colors is messy. The Builder pattern hides the complexity of escape codes and string    
         concatenation behind a clean, readable interface.
       * Step-by-Step Construction: Creating a Player is a complex process involving multiple dependencies (Items, Weapons, Artifacts). The Builder pattern     
         allows us to construct the player object step-by-step, ensuring the final object is always in a valid state when build() is called.

  3. Strategy Pattern
   * Where it's used: TurnOrderStrategy, LevelGenerator, and GameMode.
   * How it works: We define an interface (the Strategy) and multiple concrete implementations. The BattleEngine holds a reference to a TurnOrderStrategy and   
     calls its determineOrder() method without knowing how the order is calculated.
   * Design Justification: This allows the game to support different logic for turn order (e.g., SpeedBasedTurnOrder, RoundRobinTurnOrder) or different logic   
     for level spawning (StoryLevelGenerator vs. SurvivalLevelGenerator) without modifying the core engine or game mode classes. This is a perfect example of   
     the Open/Closed Principle.

  4. Observer Pattern (Trigger System)
   * Where it's used: StatusManager, StatusEffect, and CombatEvent.
   * How it works: The StatusManager acts as a "Subject" that notifies "Observers" (StatusEffects) when specific CombatEvents (like TURN_START, ATTACKED,       
     DAMAGE_TAKEN, or TURN_END) occur.
   * Design Justification: This pattern completely decouples the BattleEngine and Combatant from the specific logic of status effects. Instead of hardcoding    
     checks for "isStunned" or "isBurning" into the combat loop, the engine simply "fires" an event. For example, SmokeBombEffect listens for ATTACKED and      
     returns false to nullify damage, while BurnEffect listens to TURN_END to apply damage. This makes adding complex, multi-stage status effects trivial       
     without modifying core logic.

  5. Registry Pattern (Extensibility)
   * Where it's used: The base Registry<T> class and its specific implementations.
   * How it works: We implemented a generic Registry<T> system that acts as a centralized catalog. Each registry maintains a mapping of names/descriptions to   
     specific classes.
   * Design Justification: To avoid hardcoding lists of available players, enemies, items, and game modes throughout the UI and engine, registries allow for    
     "plug-and-play" extensibility. Adding a new feature (like a new GameMode or Weapon) simply requires creating the class and registering it. This directly  

### Coupling and Cohesion
*   **High Cohesion:** Classes are highly focused. The `OutputBuilder` strictly handles text formatting and ANSI color generation. The `LevelGenerator` is solely responsible for instantiating enemy waves. The `ActionContext` bundles all relevant data for an action into a single object, keeping action signatures clean.
*   **Low Coupling:** The UI (`GameUI`) is completely decoupled from the game logic (`BattleEngine`, `GameManager`) via the `UserInterface` interface. The engine does not know it is printing to a console; it only knows it is sending strings to a `UserInterface`. The `TurnOrderStrategy` is injected into the engine, meaning we can swap between `SpeedBasedTurnOrder` and any other strategy without touching the engine code.

---

## 2. Implementation Code [20 Marks]

### Code Correctness, Readability, and Convention
*   The codebase strictly adheres to Java naming conventions (PascalCase for classes, camelCase for methods and variables).
*   Code is highly modularized to avoid "god classes" and "spaghetti code." The recent refactoring of `CombatantRenderer` removed clunky string-matching and `switch` statements, migrating specific rendering data (like ASCII art and colors) directly into the `Combatant` subclasses where it belongs.
*   Exceptions are handled gracefully, particularly within the `Registry` system where reflection is used to instantiate classes dynamically.

### Adherence to SOLID Principles
1.  **Single Responsibility Principle (SRP):** Every class has one reason to change. `GameManager` handles the high-level loop (menu -> game -> replay), `BattleEngine` handles the combat loop, and `ConsoleInputHandler` handles user input validation.
2.  **Open/Closed Principle (OCP):** As demonstrated by the Registry and Observer patterns, the system is open for extension (adding new items, effects, or combatants) but closed for modification (you do not need to change the `BattleEngine` to support a new type of attack).
3.  **Liskov Substitution Principle (LSP):** Any subclass of `Combatant` can be substituted into the `BattleEngine` without breaking the game. The engine expects a `Combatant`, and whether it is given a `Warrior`, `Goblin`, or a newly created `Dragon`, the game functions correctly.
4.  **Interface Segregation Principle (ISP):** Interfaces are small and focused. `Named` only requires a `getName()` method. `Describable` requires `getDescription()`. This prevents forcing classes to implement methods they don't need.
5.  **Dependency Inversion Principle (DIP):** High-level modules (like `BattleEngine`) depend on abstractions (like `TurnOrderStrategy` and `UserInterface`), not concrete implementations (like `SpeedBasedTurnOrder` or `GameUI`).

---

## 3. Thinking and Development Process [20 Marks]

### Coverage of Application Essentials
The system implements all base requirements from the PDF:
*   Two player classes (Warrior, Wizard) with basic attacks and special skills (Shield Bash, Arcane Blast).
*   Two enemy types (Goblin, Wolf) with basic attacks.
*   Required status effects: Stun, Arcane Blast buff, Defend buff, Smoke Bomb invulnerability.
*   Required items: Potion, Power Stone, Smoke Bomb.
*   Three difficulty levels with backup spawning logic.
*   Speed-based turn order execution.

### Innovation and Additional Features
To demonstrate robustness and creativity while strictly adhering to OODP, several additional features were implemented *beyond* the scope of the PDF:

#### 1. Dynamic Game Modes
Instead of a single linear game, the system uses a `GameMode` abstraction, supported by `GameModeRegistry` and `LevelGenerator` strategies.
*   **Story Mode:** Progresses through the predefined Easy, Medium, and Hard levels.
*   **Survival Mode:** Generates endless waves of enemies, testing the player's endurance.
*   **Timed Mode:** A score-attack mode with a strict 10-round limit, tracking enemies killed.
*   **Challenge Mode:** Forces a specific loadout (e.g., Warrior with a Sword and Iron Shield) against a custom Boss encounter (`Dragon`).

#### 2. Advanced Equipment System (Weapons and Artifacts)
The PDF only requires one-time use Items. We expanded this by implementing persistent `Equipment`.
*   Players can select a **Weapon** (e.g., `Sword`, `Dagger`, `Staff`) and an **Artifact** (e.g., `IronShield`, `SwiftBoots`, `ManaGem`).
*   Equipment modifies base stats (via the `EquipmentManager`) and can provide complex passive abilities using the same `StatusEffect` observer system used for temporary buffs. For instance, the `VampiricAmulet` listens for attacks and heals the player, while the `ManaGem` reduces cooldowns passively.

#### 3. Expanded Enemy Roster (The Boss)
To test the extensibility of the combat system, a `Dragon` enemy was added. Unlike basic enemies, the Dragon has multiple actions (`FireBreath`, `TailWhip`) and utilizes an AI method (`chooseAction`) that prioritizes special attacks over basic attacks when they are off cooldown, proving the `Enemy` abstraction can support complex AI behaviors.

#### 4. Thematic User Interface (Colour Palettes)
While a GUI was not required, we wanted the CLI to be highly polished. We implemented a `ColourPalette` interface and an `OutputBuilder`.
*   The UI is entirely separated from the ANSI escape codes.
*   The player can swap themes (Classic, Cyberpunk, Midnight, Solarized) via a Settings menu at runtime. This demonstrates Dependency Injection and the Strategy pattern applied to UI rendering.
*   The `CombatantRenderer` parses `Combatant` state and generates dynamic, visually distinct player/enemy "cards" with ASCII art, dynamic health bars (changing color based on HP ratio), and status indicators.

#### 5. Robust Status Effect Engine
As mentioned in the Design Consideration section, the event-driven `StatusManager` handles both **Stackable** and **Non-Stackable** effects. It allows for complex interactions, such as buffs that expire on specific triggers rather than just round counts, or effects that combine duration rather than stacking indefinitely.

### Conclusion
The architecture of this Turn-Based Combat Arena was explicitly designed to prioritize clean code, modularity, and strict adherence to SOLID principles. The additional features were not implemented merely for complexity, but to prove that the core abstractions (Registries, Observers, Strategies) are robust enough to handle significant expansion without requiring rewrites of the foundational engine.