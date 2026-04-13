package boundary;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import control.Registry;
import control.mode.GameMode;
import control.mode.challenge.ChallengeMode;
import control.mode.story.StoryMode;
import control.mode.survival.SurvivalMode;
import control.mode.timed.TimedMode;
import entity.action.interfaces.Action;
import entity.combatant.Combatant;
import entity.combatant.helpers.StatField;
import entity.combatant.player.Player;
import entity.interfaces.Describable;
import entity.interfaces.Named;
import entity.item.Item;
import entity.level.Difficulty;

public class GameUI implements UserInterface {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void displayWelcome() {
        System.out.println("===========================================");
        System.out.println("   WELCOME TO TURN-BASED COMBAT ARENA");
        System.out.println("===========================================");
    }

    @Override
    public GameMode selectGameMode() {
        List<GameMode> modes = List.of(
            new StoryMode(),
            new SurvivalMode(),
            new TimedMode(),
            new ChallengeMode()
        );

        System.out.println("\n--- SELECT GAME MODE ---");
        for (int i = 0; i < modes.size(); i++) {
            GameMode mode = modes.get(i);
            System.out.printf("%d. %-15s -- %s%n", i + 1, mode.getName(), mode.getDescription());
        }

        int pick = readChoice(1, modes.size());
        return modes.get(pick - 1);
    }

    @Override
    public void displayModeEnd(boolean playerWon, GameMode mode) {
        System.out.println("\n=====================================================");
        System.out.println("[ " + mode.getName().toUpperCase() + " ]");
        if (playerWon) {
            System.out.println("VICTORY! Well done!");
        } else {
            System.out.println("DEFEATED. Better luck next time!");
        }
        System.out.println("=====================================================");
    }

    @Override
    public <T extends Named & Describable> Class<? extends T> selectFromRegistry(Registry<T> registry, String title) {
        displayRegistry(registry, title);
        int choice = readChoice(1, registry.getNames().size());
        return registry.getType(choice - 1);
    }

    @Override
    public <T extends Named & Describable> List<Class<? extends T>> selectMultipleFromRegistry(
            Registry<T> registry, String title, int count) {
        displayRegistry(registry, title);
        List<Class<? extends T>> chosen = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            System.out.print("Choice " + i + ": ");
            int pick = readChoice(1, registry.getNames().size());
            chosen.add(registry.getType(pick - 1));
        }
        return chosen;
    }

    private <T extends Named & Describable> void displayRegistry(
            Registry<T> registry, String title) {
        List<String> names = registry.getNames();
        List<Registry.Entry<T>> entries = registry.getEntries();

        System.out.println("\n--- " + title + " ---");
        for (int i = 0; i < entries.size(); i++) {
            System.out.printf("%d. %-20s -- %s%n", i + 1, names.get(i), entries.get(i).description);
        }
    }

    @Override
    public Difficulty selectDifficulty() {
        System.out.println("\n--- SELECT DIFFICULTY ---");
        Difficulty[] diffs = Difficulty.values();
        for (int i = 0; i < diffs.length; i++) {
            System.out.printf("%d. %s%n", i + 1, diffs[i].name());
        }
        int pick = readChoice(1, diffs.length);
        return diffs[pick - 1];
    }

    @Override
    public void displayRoundStart(int round, List<Combatant> combatants) {
        System.out.println("\n=================== ROUND " + round + " ===================");
        for (Combatant c : combatants) {
            if (c.isAlive()) {
                System.out.printf("  %-14s HP: %3d/%-3d  %s%n",
                        c.getName(), c.getHp(), c.stats().get(StatField.maxHp), c.status.toString());
            }
        }
        System.out.println("=====================================================");
    }

    @Override
    public Combatant selectTarget(List<Combatant> combatants) {
        System.out.println("Select target:");
        for (int i = 0; i < combatants.size(); i++) {
            Combatant c = combatants.get(i);
            System.out.printf("  %d. %-10s HP: %d%n", i + 1, c.getName(), c.getHp());
        }
        int idx = readChoice(1, combatants.size()) - 1;
        return combatants.get(idx);
    }

    @Override
    public Item selectItem(List<Item> items) {
        System.out.println("Select item:");
        for (int i = 0; i < items.size(); i++) {
            System.out.printf("  %d. %s%n", i + 1, items.get(i).getName());
        }
        return items.get(readChoice(1, items.size()) - 1);
    }

    @Override
    public void displayActionResult(String msg) {
        System.out.println("  >> " + msg);
    }

    @Override
    public void displayBattleEnd(boolean playerWon, Player player, int rounds) {
        System.out.println("\n=====================================================");
        if (playerWon) {
            System.out.println("VICTORY! Congratulations, you defeated all enemies!");
            System.out.printf("  Remaining HP: %d/%d  |  Total Rounds: %d%n",
                    player.getHp(), player.stats().get(StatField.maxHp), rounds);
        } else {
            System.out.println("DEFEATED. Don't give up, try again!");
            System.out.printf("  Total Rounds Survived: %d%n", rounds);
        }
        System.out.println("=====================================================");
    }

    @Override
    public int askReplay() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("1. Replay with same settings");
        System.out.println("2. Start a new game");
        System.out.println("3. Exit");
        return readChoice(1, 3);
    }

    private int readChoice(int min, int max) {
        while (true) {
            System.out.print("> ");
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                if (val >= min && val <= max) return val;
            } catch (NumberFormatException ignored) {}
            System.out.println("Please enter a number between " + min + " and " + max + ".");
        }
    }

    @Override
    public Action selectAction(List<Action> allActions,
                               List<Action> readyActions,
                               Combatant owner) {
        System.out.println("\n" + owner.getName() + "'s turn -- choose an action:");

        for (int i = 0; i < allActions.size(); i++) {
            Action action = allActions.get(i);
            boolean ready = readyActions.contains(action);
            if (ready) {
                System.out.printf("  %d. %s%n", i + 1, action.getLabel());
            } else {
                System.out.printf("  %d. %s  [UNAVAILABLE]%n", i + 1, action.getLabel());
            }
        }

        while (true) {
            System.out.print("> ");
            try {
                int input = Integer.parseInt(scanner.nextLine().trim());
                if (input >= 1 && input <= allActions.size()) {
                    Action chosen = allActions.get(input - 1);
                    if (readyActions.contains(chosen)) {
                        return chosen;
                    }
                }
            } catch (NumberFormatException ignored) {}
            System.out.println("Invalid choice. Please select a ready action.");
        }
    }
}
