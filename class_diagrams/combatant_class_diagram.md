# Entity Combatant Module Class Diagram

The `entity.combatant` module contains the core character models, their stat management, status effect handling, and character-creation factories.

```mermaid
classDiagram
    %% --- Core Hierarchy ---
    class Combatant {
        <<abstract>>
        #hp: int
        +baseStats: Stats
        +statEffects: Stats
        +status: StatusManager
        +actions: ActionMenu
        +getTeam() Team*
        +chooseAction(ActionContext) Action*
        +takeTurn(ActionContext)
        +takeDamage(int, ActionContext)
        +isAlive() boolean
        +stats() Stats
    }

    class Player {
        <<abstract>>
        +inventory: Inventory
        +equipment: EquipmentManager
        +chooseAction(ActionContext) Action
    }
    Combatant <|-- Player

    class Warrior { }
    class Wizard { }
    Player <|-- Warrior
    Player <|-- Wizard

    class Enemy {
        #tag: char
        +chooseAction(ActionContext) Action
    }
    Combatant <|-- Enemy

    class Goblin { }
    class Wolf { }
    class Dragon { }
    Enemy <|-- Goblin
    Enemy <|-- Wolf
    Enemy <|-- Dragon

    %% --- Helpers (Composition) ---
    class Stats {
        -values: Map~StatField, Integer~
        +get(StatField) int
        +add(Stats) Stats
        +add(StatField, int) Stats
    }

    class StatusManager {
        -activeEffects: List~StatusEffect~
        -owner: Combatant
        +add(StatusEffect, UserInterface)
        +trigger(CombatEvent, ActionContext) boolean
        +removeExpired()
    }

    class ActionMenu {
        -actions: List~Action~
        +add(Action)
        +ready(ActionContext) List~Action~
        +decrementCooldowns()
    }

    class Inventory {
        -items: List~Item~
        +add(Item)
        +usableItems() List~Item~
    }

    class EquipmentManager {
        -equipped: Map~EquipmentType, Equipment~
        -owner: Combatant
        +equip(Equipment)
        +getTotalStatBonus() Stats
    }

    class StatField { <<enumeration>> }
    class CombatEvent { <<enumeration>> }

    %% --- Creation & Registry ---
    class PlayerFactory {
        +createPlayer(Class~Player~) PlayerBuilder
    }
    
    class EnemyFactory {
        +createEnemy(Difficulty) List~Enemy~
    }

    class PlayerRegistry {
        -instance: PlayerRegistry
        +getInstance() PlayerRegistry
    }
    class EnemyRegistry {
        -instance: EnemyRegistry
        +getInstance() EnemyRegistry
    }

    %% --- Relationships ---
    Combatant "1" *-- "1" Stats : baseStats
    Combatant "1" *-- "1" Stats : statEffects
    Combatant "1" *-- "1" StatusManager
    Combatant "1" *-- "1" ActionMenu
    
    Player "1" *-- "1" Inventory
    Player "1" *-- "1" EquipmentManager
    
    StatusManager ..> CombatEvent
    Stats ..> StatField
    
    PlayerRegistry --|> Registry
    EnemyRegistry --|> Registry
```

### Module Responsibilities:
- **`Combatant`**: The foundational abstraction for anything that can participate in battle. It handles health, turn execution, and delegating stats/status logic to specialized helpers.
- **`Player` vs `Enemy`**: Players support equipment and inventory systems and rely on user input for actions. Enemies use simple AI (or fixed logic) and have unique tag identifiers.
- **Stat System (`Stats`, `StatField`)**: Decouples numerical data from the combatants. Supports additive bonuses, making it easy to stack effects and equipment.
- **Status System (`StatusManager`, `CombatEvent`)**: An event-driven system that allows status effects to hook into various points in the turn cycle (e.g., `TURN_START`, `DAMAGE_TAKEN`).
- **Factories & Registries**: Centralizes character creation, ensuring that complex objects (like a Player with starting items and gear) are built consistently.
