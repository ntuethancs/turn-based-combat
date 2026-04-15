# Entity Effect Module Class Diagram

The `entity.effect` module provides a flexible, event-driven system for applying temporary or permanent status changes to combatants.

```mermaid
classDiagram
    %% --- Core Interfaces & Base Classes ---
    class StatusEffect {
        <<abstract>>
        -name: String
        +onApplied(Combatant, UserInterface)
        +onRemoved(Combatant, UserInterface)
        +trigger(CombatEvent, ActionContext) boolean
        +isExpired() boolean
    }
    Named <|.. StatusEffect

    class NonStackableEffect {
        <<interface>>
        +getConflictId() String
    }

    %% --- Temporal Hierarchies ---
    class DurationEffect {
        <<abstract>>
        #duration: int
        +decrement()
    }
    StatusEffect <|-- DurationEffect

    class PermanentEffect {
        <<abstract>>
    }
    StatusEffect <|-- PermanentEffect

    %% --- Stat-Modifying Effects ---
    class DurationStatEffect {
        <<abstract>>
        #bonus: Stats
    }
    DurationEffect <|-- DurationStatEffect

    class PermanentStatEffect {
        <<abstract>>
        #bonus: Stats
    }
    PermanentEffect <|-- PermanentStatEffect

    %% --- Combat Effects ---
    class BurnEffect { }
    class StunEffect { }
    class SmokeBombEffect { }
    DurationEffect <|-- BurnEffect
    DurationEffect <|-- StunEffect
    DurationEffect <|-- SmokeBombEffect
    NonStackableEffect <|.. StunEffect
    NonStackableEffect <|.. SmokeBombEffect

    class DefendEffect { }
    class ArcaneBlastEffect { }
    DurationStatEffect <|-- DefendEffect
    PermanentStatEffect <|-- ArcaneBlastEffect

    %% --- Equipment Effects ---
    class EquipmentEffect {
        <<abstract>>
    }
    PermanentEffect <|-- EquipmentEffect

    class ManaGemEffect { }
    class ThornArmourEffect { }
    class VampiricAmuletEffect { }
    EquipmentEffect <|-- ManaGemEffect
    EquipmentEffect <|-- ThornArmourEffect
    EquipmentEffect <|-- VampiricAmuletEffect

    %% --- Relationships ---
    StatusEffect ..> CombatEvent
    StatusEffect ..> ActionContext
```

### Module Responsibilities:
- **`StatusEffect` Hierarchy**: Implements an Observer-like pattern where effects "subscribe" to combat events (e.g., `TURN_START`, `DAMAGE_TAKEN`) and can modify or cancel actions.
- **Stat Modification**: `DurationStatEffect` and `PermanentStatEffect` provide a clean way to apply temporary or permanent buffs/debuffs that are automatically calculated into a combatant's total stats.
- **Stacking Logic**: The `NonStackableEffect` interface allows the system to identify and resolve conflicts when multiple effects of the same type are applied to the same combatant.
- **Equipment Integration**: `EquipmentEffect` allows specialized gear to inject custom logic into the combat cycle (e.g., reflecting damage or restoring mana on specific triggers).
