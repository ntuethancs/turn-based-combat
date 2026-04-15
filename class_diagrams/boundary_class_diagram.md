# Boundary Module Class Diagram

The boundary module handles all user interaction, including input reading, output formatting, and visual rendering.

```mermaid
classDiagram
    %% --- Interfaces ---
    class UserInterface {
        <<interface>>
        +displayWelcome()
        +selectGameMode() GameMode
        +selectAction(...) Action
        +displayActionResult(String)
        +selectTarget(List~Combatant~) Combatant
        +selectItem(List~Item~) Item
        +displayBattleEnd(...)
        +askReplay() int
    }

    class InputHandler {
        <<interface>>
        +readChoice(int, int) int
        +readLine() String
    }

    class ColourPalette {
        <<interface>>
        +primary() String
        +secondary() String
        +accent() String
        +success() String
        +danger() String
        +warning() String
        +info() String
        +neutral() String
        +player() String
        +enemy() String
        +boss() String
        +high() String
        +medium() String
        +low() String
        +divider() String
        +softDivider() String
        +reset() String
        +bold() String
    }

    %% --- Implementations ---
    class GameUI {
        -inputHandler: InputHandler
        -builder: OutputBuilder
        -combatantRenderer: CombatantRenderer
        +initialize(ColourPalette)
        -displaySettings()
        -changeTheme()
    }
    UserInterface <|.. GameUI

    class ConsoleInputHandler {
        -scanner: Scanner
        -prompt: String
        -builder: OutputBuilder
        -palette: ColourPalette
    }
    InputHandler <|.. ConsoleInputHandler

    class OutputBuilder {
        +PANEL_WIDTH: int
        -palette: ColourPalette
        -buffer: StringBuilder
        -currentColour: String
        +append(String) OutputBuilder
        +appendLine(String) OutputBuilder
        +bold(String, String) OutputBuilder
        +divider() OutputBuilder
        +softDivider() OutputBuilder
        +print()
        +println()
        +clear()
    }

    class CombatantRenderer {
        -builder: OutputBuilder
        -BAR_LENGTH: int
        -CARD_WIDTH: int
        +printCombatantCard(Combatant, int, boolean)
        -healthBar(int, int)
        -cardBorder()
        -cardLine(String, String)
    }

    class ClassicColourPalette { }
    class CyberpunkColourPalette { }
    class MidnightColourPalette { }
    class SolarizedColourPalette { }
    ColourPalette <|.. ClassicColourPalette
    ColourPalette <|.. CyberpunkColourPalette
    ColourPalette <|.. MidnightColourPalette
    ColourPalette <|.. SolarizedColourPalette

    class ColourPaletteRegistry {
        -instance: ColourPaletteRegistry
        +getInstance() ColourPaletteRegistry
    }

    %% --- Relationships ---
    GameUI "1" *-- "1" InputHandler
    GameUI "1" *-- "1" OutputBuilder
    GameUI "1" *-- "1" CombatantRenderer
    
    ConsoleInputHandler --> OutputBuilder
    ConsoleInputHandler --> ColourPalette
    
    CombatantRenderer --> OutputBuilder
    OutputBuilder --> ColourPalette
    
    GameUI ..> ColourPaletteRegistry
```

### Module Responsibilities:
- **`UserInterface` & `GameUI`**: The primary facade for all user-facing operations.
- **`InputHandler` & `ConsoleInputHandler`**: Encapsulates user input logic, providing validation and error handling.
- **`OutputBuilder`**: A fluent API for building complex, colored terminal output with support for ANSI escapes.
- **`CombatantRenderer`**: Specialized logic for drawing character cards, health bars, and ASCII art.
- **`ColourPalette`**: Defines the visual theme of the application, allowing for runtime theme switching via different implementations.
