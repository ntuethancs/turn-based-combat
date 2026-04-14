package boundary;

import boundary.input.ConsoleInputHandler;
import boundary.input.InputHandler;
import boundary.output.CombatantRenderer;
import boundary.output.OutputBuilder;
import boundary.output.colours.ClassicColourPalette;
import boundary.output.colours.ColourPalette;
import boundary.output.colours.ColourPaletteRegistry;
import control.Registry;
import control.mode.GameMode;
import control.mode.challenge.ChallengeMode;
import control.mode.story.StoryMode;
import control.mode.survival.SurvivalMode;
import control.mode.timed.TimedMode;
import entity.action.ActionContext;
import entity.action.interfaces.Action;
import entity.combatant.Combatant;
import entity.combatant.helpers.StatField;
import entity.combatant.player.Player;
import entity.interfaces.Describable;
import entity.interfaces.Named;
import entity.item.Item;
import entity.level.Difficulty;
import java.util.ArrayList;
import java.util.List;

public class GameUI implements UserInterface {

    private InputHandler inputHandler;
    private OutputBuilder builder;
    private CombatantRenderer combatantRenderer;

    public GameUI() {
        initialize(new ClassicColourPalette());
    }

    // Constructor for dependency injection
    public GameUI(InputHandler inputHandler, OutputBuilder builder, CombatantRenderer combatantRenderer) {
        this.inputHandler = inputHandler;
        this.builder = builder;
        this.combatantRenderer = combatantRenderer;
    }

    private void initialize(ColourPalette palette) {
        this.builder = new OutputBuilder(palette);
        this.inputHandler = new ConsoleInputHandler("> ", palette);
        this.combatantRenderer = new CombatantRenderer(builder);
    }

    private ColourPalette palette() { return builder.getPalette(); }

    @Override
    public void displayWelcome() {
        builder.newLine()
            .divider()
            .setColour(palette().primary())
            .appendLine("████████╗██╗   ██╗██████╗ ███╗   ██╗    ██████╗  █████╗ ███████╗███████╗██████╗ ")
            .appendLine("╚══██╔══╝██║   ██║██╔══██╗████╗  ██║    ██╔══██╗██╔══██╗██╔════╝██╔════╝██╔══██╗")
            .appendLine("   ██║   ██║   ██║██████╔╝██╔██╗ ██║    ██████╔╝███████║███████╗█████╗  ██║  ██║")
            .appendLine("   ██║   ██║   ██║██╔══██╗██║╚██╗██║    ██╔══██╗██╔══██║╚════██║██╔══╝  ██║  ██║")
            .appendLine("   ██║   ╚██████╔╝██║  ██║██║ ╚████║    ██████╔╝██║  ██║███████║███████╗██████╔╝")
            .appendLine("   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═══╝    ╚═════╝ ╚═╝  ╚═╝╚══════╝╚══════╝╚═════╝ ")
            .setColour(palette().accent())
            .appendLine("        ██████╗ ██████╗ ███╗   ███╗██████╗  █████╗ ████████╗")
            .appendLine("       ██╔════╝██╔═══██╗████╗ ████║██╔══██╗██╔══██╗╚══██╔══╝")
            .appendLine("       ██║     ██║   ██║██╔████╔██║██████╔╝███████║   ██║   ")
            .appendLine("       ██║     ██║   ██║██║╚██╔╝██║██╔══██╗██╔══██║   ██║   ")
            .appendLine("       ╚██████╗╚██████╔╝██║ ╚═╝ ██║██████╔╝██║  ██║   ██║   ")
            .appendLine("        ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚═════╝ ╚═╝  ╚═╝   ╚═╝   ")
            .divider()
            .appendLine("                 Enter the arena. Survive the fight.", palette().neutral())
            .divider()
            .print();
    }

    @Override
    public GameMode selectGameMode() {
        List<GameMode> modes = List.of(
                new StoryMode(),
                new SurvivalMode(),
                new TimedMode(),
                new ChallengeMode()
        );

        while (true) {
            builder.newLine()
                .sectionTitle("MAIN MENU", palette().primary())
                .softDivider()
                .print();

            for (int i = 0; i < modes.size(); i++) {
                GameMode mode = modes.get(i);
                builder.append((i + 1) + ". ", palette().primary())
                       .append(String.format("%-16s", mode.getName()), palette().secondary())
                       .append("  ")
                       .appendLine(mode.getDescription(), palette().neutral());
            }

            builder.append((modes.size() + 1) + ". ", palette().primary())
                   .append(String.format("%-16s", "Settings"), palette().accent())
                   .appendLine("Change theme and other options.", palette().neutral())
                   .print();

            builder.softDivider().print();
            int pick = inputHandler.readChoice(1, modes.size() + 1);

            if (pick <= modes.size()) {
                return modes.get(pick - 1);
            } else {
                displaySettings();
            }
        }
    }

    private void displaySettings() {
        while (true) {
            builder.newLine()
                .sectionTitle("SETTINGS", palette().primary())
                .softDivider()
                .append("1.", palette().primary()).appendLine(" Change Theme", palette().secondary())
                .append("2.", palette().primary()).appendLine(" Back to Main Menu", palette().secondary())
                .softDivider()
                .print();

            int choice = inputHandler.readChoice(1, 2);
            if (choice == 1) {
                changeTheme();
            } else {
                break;
            }
        }
    }

    private void changeTheme() {
        ColourPaletteRegistry registry = ColourPaletteRegistry.getInstance();
        Class<? extends ColourPalette> paletteClass = selectFromRegistry(registry, "SELECT THEME");
        try {
            initialize(paletteClass.getDeclaredConstructor().newInstance());
            builder.newLine()
                .appendLine("Theme applied successfully!", palette().success())
                .print();
        } catch (Exception e) {
            builder.newLine()
                .appendLine("Failed to apply theme.", palette().danger())
                .print();
        }
    }

    @Override
    public void displayModeEnd(boolean playerWon, GameMode mode) {
        builder.newLine()
            .divider()
            .append("MODE", palette().primary())
            .appendLine("  " + mode.getName().toUpperCase(), palette().bold() + palette().neutral())
            .append("RESULT", palette().primary());

        if (playerWon) {
            builder.appendLine("  " + "VICTORY", palette().bold() + palette().success());
        } else {
            builder.appendLine("  " + "DEFEATED", palette().bold() + palette().danger());
        }
        builder.divider()
            .print();
    }

    @Override
    public <T extends Named & Describable> Class<? extends T> selectFromRegistry(Registry<T> registry, String title) {
        displayRegistry(registry, title);
        int choice = inputHandler.readChoice(1, registry.getNames().size());
        return registry.getType(choice - 1);
    }

    @Override
    public <T extends Named & Describable> List<Class<? extends T>> selectMultipleFromRegistry(
            Registry<T> registry, String title, int count) {

        displayRegistry(registry, title);
        List<Class<? extends T>> chosen = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            builder.append("Choice " + i + ": ", palette().primary()).print();
            int pick = inputHandler.readChoice(1, registry.getNames().size());
            chosen.add(registry.getType(pick - 1));
        }

        return chosen;
    }

    private <T extends Named & Describable> void displayRegistry(Registry<T> registry, String title) {
        List<String> names = registry.getNames();
        List<Registry.Entry<T>> entries = registry.getEntries();

        builder.newLine()
            .sectionTitle(title.toUpperCase(), palette().primary())
            .softDivider()
            .print();

        for (int i = 0; i < entries.size(); i++) {
            builder.append((i + 1) + ". ", palette().primary())
                   .append(names.get(i), palette().secondary())
                   .append("  ")
                   .appendLine(entries.get(i).description, palette().neutral());
        }
        builder.print();

        builder.softDivider().print();
    }

    @Override
    public Difficulty selectDifficulty() {
        builder.newLine()
            .sectionTitle("DIFFICULTY", palette().primary())
            .softDivider()
            .print();

        Difficulty[] diffs = Difficulty.values();
        for (int i = 0; i < diffs.length; i++) {
            String d = diffs[i].name();
            String difficultycolour = palette().success();

            if ("MEDIUM".equalsIgnoreCase(d)) {
                difficultycolour = palette().warning();
            } else if ("HARD".equalsIgnoreCase(d)) {
                difficultycolour = palette().danger();
            }

            builder.append((i + 1) + ". ", palette().primary())
                   .appendLine(d, difficultycolour);
        }
        builder.print();

        builder.softDivider().print();
        int pick = inputHandler.readChoice(1, diffs.length);
        return diffs[pick - 1];
    }

    @Override
    public void displayRoundStart(int round, List<Combatant> combatants) {
        builder.newLine()
            .divider()
            .bold("ROUND " + round, palette().accent()).newLine()
            .divider()
            .print();

        List<Combatant> players = new ArrayList<>();
        List<Combatant> enemies = new ArrayList<>();

        for (Combatant c : combatants) {
            if (!c.isAlive()) continue;

            if (c.getTeam() == ActionContext.Team.PLAYER) {
                players.add(c);
            } else {
                enemies.add(c);
            }
        }

        if (!players.isEmpty()) {
            builder.sectionTitle("ALLIES", palette().player()).print();
            for (Combatant c : players) {
                combatantRenderer.printCombatantCard(c, 0, false);
            }
        }

        if (!players.isEmpty() && !enemies.isEmpty()) {
            builder.softDivider().print();
        }

        if (!enemies.isEmpty()) {
            builder.sectionTitle("ENEMIES", palette().enemy()).print();
            for (Combatant c : enemies) {
                combatantRenderer.printCombatantCard(c, 0, false);
            }
        }

        builder.divider().print();
    }

    @Override
    public Combatant selectTarget(List<Combatant> combatants) {
        builder.newLine()
            .sectionTitle("SELECT TARGET", palette().primary())
            .softDivider()
            .print();

        for (int i = 0; i < combatants.size(); i++) {
            combatantRenderer.printCombatantCard(combatants.get(i), i + 1, true);
            if (i < combatants.size() - 1) {
                builder.newLine().print();
            }
        }

        builder.softDivider().print();
        int idx = inputHandler.readChoice(1, combatants.size()) - 1;
        return combatants.get(idx);
    }

    @Override
    public Item selectItem(List<Item> items) {
        builder.newLine()
            .sectionTitle("SELECT ITEM", palette().primary())
            .softDivider()
            .print();

        for (int i = 0; i < items.size(); i++) {
            builder.append((i + 1) + ". ", palette().primary())
                   .appendLine(items.get(i).getName(), palette().secondary());
        }
        builder.print();

        builder.softDivider().print();
        return items.get(inputHandler.readChoice(1, items.size()) - 1);
    }

    @Override
    public void displayActionResult(String msg) {
        String lower = msg.toLowerCase();
        String chosencolour = palette().neutral();

        if (lower.contains("victory") || lower.contains("heal") || lower.contains("recover")
                || lower.contains("restored") || lower.contains("gain")) {
            chosencolour = palette().success();
        } else if (lower.contains("defeated") || lower.contains("eliminated")
                || lower.contains("dmg") || lower.contains("damage")
                || lower.contains("hit") || lower.contains("attacks")
                || lower.contains("critical hit")) {
            chosencolour = palette().danger();
        } else if (lower.contains("stun") || lower.contains("cooldown")
                || lower.contains("buff") || lower.contains("debuff")
                || lower.contains("prepare")) {
            chosencolour = palette().warning();
        }

        builder.appendLine(">> " + msg, chosencolour).print();
    }

    @Override
    public void displayBattleEnd(boolean playerWon, Player player, int rounds) {
        builder.setColour(palette().primary()).newLine().divider();
        
        if (playerWon) {
            builder
                .append("BATTLE RESULT")
                .appendLine("  " + "VICTORY", palette().bold() + palette().success())
                .appendLine("You defeated all enemies.", palette().success())
                .append("Final HP")
                .appendLine("  " + player.getHp() + "/" + player.stats().get(StatField.maxHp), palette().success())
                .append("Rounds", palette().primary())
                .appendLine("    " + String.valueOf(rounds), palette().accent());
        } else {
            builder
                .append("BATTLE RESULT")
                .appendLine("  " + "DEFEATED", palette().bold() + palette().danger())
                .appendLine("The enemy team has won this battle.", palette().danger())
                .append("Rounds")
                .appendLine("    " + String.valueOf(rounds), palette().accent());
        }

        builder.divider().print();
    }

    @Override
    public int askReplay() {
        builder
            .setColour(palette().primary())
            .newLine()
            .sectionTitle("NEXT STEP", palette().primary())
            .softDivider()
            .append("1.").appendLine(" Replay with same settings", palette().neutral())
            .append("2.").appendLine(" Start a new game", palette().neutral())
            .append("3.").appendLine(" Exit", palette().neutral())
            .softDivider()
            .print();
        return inputHandler.readChoice(1, 3);
    }
    @Override
public Action selectAction(List<Action> allActions,
                           List<Action> readyActions,
                           Combatant owner) {

    builder.newLine()
        .sectionTitle("TURN", palette().primary())
        .bold(owner.getName(), combatantRenderer.colourFor(owner)).newLine()
        .softDivider()
        .print();

    combatantRenderer.printCombatantCard(owner, 0, false);
    builder.softDivider().print();

    for (int i = 0; i < allActions.size(); i++) {
        Action action = allActions.get(i);
        boolean ready = readyActions.contains(action);

        if (ready) {
            builder.append((i + 1) + ". ", palette().primary())
                   .appendLine(action.getLabel(), palette().secondary());
        } else {
            builder.append((i + 1) + ". ", palette().primary())
                   .appendLine(action.getLabel() + " [UNAVAILABLE]", palette().danger());
        }
    }
    builder.print();

    builder.softDivider().print();

    while (true) {
        int input = inputHandler.readChoice(1, allActions.size());
        Action chosen = allActions.get(input - 1);
        if (readyActions.contains(chosen)) {
            return chosen;
        }
        builder.appendLine("Invalid choice.", palette().danger()).print();
    }
}
    
    
}


