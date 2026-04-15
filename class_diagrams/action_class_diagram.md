# Entity Action Module Class Diagram

The `entity.action` module defines the behavior of all combatants, including attacks, defensive maneuvers, and item usage.

```mermaid
classDiagram
    %% --- Core Interfaces ---
    class Action {
        <<interface>>
        +getLabel() String
        +selectTargets(ActionContext) List~Combatant~
        +executeOn(Combatant, ActionContext) boolean
        +execute(ActionContext) boolean
    }

    class Attack {
        <<interface>>
        +getDamage(Combatant, ActionContext) int
        +displayAttack(Combatant, ActionContext)
        +getVerb() String
    }
    Action <|-- Attack

    class SingleTargetAttack {
        <<interface>>
        +selectTarget(ActionContext) Combatant
    }
    Attack <|-- SingleTargetAttack

    class PlayerSingleTargetAttack {
        <<interface>>
    }
    SingleTargetAttack <|-- PlayerSingleTargetAttack

    class SplashAttack {
        <<interface>>
    }
    Attack <|-- SplashAttack

    class SelfAction {
        <<interface>>
    }
    Action <|-- SelfAction

    %% --- Special Attacks (Base Classes) ---
    class SpecialAttack {
        <<abstract>>
        #cooldown: int
        #specialCooldown: int
        +resetCooldown()
        +decrementCooldown()
    }
    Attack <|-- SpecialAttack

    %% --- Player Actions ---
    class WarriorBasicAttack { }
    class WizardBasicAttack { }
    PlayerSingleTargetAttack <|.. WarriorBasicAttack
    PlayerSingleTargetAttack <|.. WizardBasicAttack

    class ShieldBash { }
    SpecialAttack <|-- ShieldBash
    PlayerSingleTargetAttack <|.. ShieldBash

    class ArcaneBlast { }
    SpecialAttack <|-- ArcaneBlast
    SplashAttack <|.. ArcaneBlast

    class DefendAction { }
    class ItemAction { }
    SelfAction <|.. DefendAction
    SelfAction <|.. ItemAction

    %% --- Enemy Actions ---
    class EnemyBasicAttack { }
    SingleTargetAttack <|.. EnemyBasicAttack

    class FireBreath { }
    class TailWhip { }
    SpecialAttack <|-- FireBreath
    SplashAttack <|.. FireBreath
    SpecialAttack <|-- TailWhip
    SplashAttack <|.. TailWhip

    %% --- Context & State ---
    class ActionContext {
        +actor: Combatant
        +targets: List~Combatant~
        +curTarget: Combatant
        +ui: UserInterface
        +damage: int
        +getLivingOpponents() List~Combatant~
    }

    class CombatState { <<enumeration>> }
    class ExecutionState { <<enumeration>> }
    class InteractionProvider { <<interface>> }

    %% --- Relationships ---
    Action ..> ActionContext
    ActionContext --> InteractionProvider
```

### Module Responsibilities:
- **`Action` Hierarchy**: Uses the Strategy and Command patterns to define combat behaviors independently of the `Combatant` classes.
- **`Attack` Hierarchy**: Specialized logic for damage calculation, hit detection, and visual display of offensive moves.
- **`SpecialAttack`**: Adds resource management (cooldowns) to the basic attack logic.
- **`ActionContext`**: A parameter object that encapsulates all external dependencies (UI, state, targets) required to execute an action, ensuring that action implementations remain "pure" and decoupled.
- **`SelfAction` / `SingleTargetAttack` / `SplashAttack`**: These interfaces define the targeting logic (who is affected) separate from the execution logic (what happens).
