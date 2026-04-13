package boundary;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import control.mode.GameMode;
import control.mode.challenge.ChallengeMode;
import control.mode.story.StoryMode;
import control.mode.survival.SurvivalMode;
import control.mode.timed.TimedMode;
import entity.action.interfaces.Action;
import entity.combatant.Combatant;
import entity.combatant.helpers.StatField;
import entity.combatant.player.Player;
import entity.equipment.Equipment;
import entity.equipment.artifact.ArtifactRegistry;
import entity.equipment.weapon.WeaponRegistry;
import entity.item.Item;
import entity.item.ItemRegistry;
import entity.level.Difficulty;

public class GameUI implements UserInterface {

    private final Scanner scanner = new Scanner(System.in);

    public void displayWelcome() {
        System.out.println("===========================================");
        System.out.println("   WELCOME TO TURN-BASED COMBAT ARENA");
        System.out.println("===========================================");
    }

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

    public int selectPlayerType() {
        System.out.println("\n--- SELECT YOUR PLAYER ---");
        System.out.println("1. Warrior  [HP:260 | ATK:40 | DEF:20 | SPD:30]");
        System.out.println("   Special: Shield Bash -- deal damage + stun target 2 turns");
        System.out.println("2. Wizard   [HP:200 | ATK:50 | DEF:10 | SPD:20]");
        System.out.println("   Special: Arcane Blast -- damage all enemies; +10 ATK per kill");
        return readChoice(1, 2);
    }

    public List<Item> selectItems() {
        List<Item> chosen = new ArrayList<>();
        ItemRegistry registry = ItemRegistry.getInstance();
        List<ItemRegistry.Entry<Item>> entries = registry.getEntries();
        List<String> names = registry.getNames();

        System.out.println("\n--- SELECT 2 ITEMS (duplicates allowed) ---");
        for (int i = 0; i < entries.size(); i++) {
            System.out.printf("%d. %-12s -- %s%n", i + 1, names.get(i), entries.get(i).description);
        }

        for (int i = 1; i <= 2; i++) {
            System.out.print("Item " + i + ": ");
            int pick = readChoice(1, entries.size());
            chosen.add(registry.create(pick - 1));
        }
        return chosen;
    }

    public Equipment selectWeapon() {
        WeaponRegistry registry = WeaponRegistry.getInstance();
        List<WeaponRegistry.Entry<Equipment>> entries = registry.getEntries();
        List<String> names = registry.getNames();

        System.out.println("\n--- SELECT 1 WEAPON ---");
        for (int i = 0; i < entries.size(); i++) {
            System.out.printf("%d. %-8s -- %s%n", i + 1, names.get(i), entries.get(i).description);
        }
        int pick = readChoice(1, entries.size());
        return registry.create(pick - 1);
    }

    public Equipment selectArtifact() {
        ArtifactRegistry registry = ArtifactRegistry.getInstance();
        List<ArtifactRegistry.Entry<Equipment>> entries = registry.getEntries();
        List<String> names = registry.getNames();

        System.out.println("\n--- SELECT 1 ARTIFACT ---");
        for (int i = 0; i < entries.size(); i++) {
            System.out.printf("%d. %-18s -- %s%n", i + 1, names.get(i), entries.get(i).description);
        }
        int pick = readChoice(1, entries.size());
        return registry.create(pick - 1);
    }

    public Difficulty selectDifficulty() {
        System.out.println("\n--- SELECT DIFFICULTY ---");
        Difficulty[] diffs = Difficulty.values();
        for (int i = 0; i < diffs.length; i++) {
            System.out.printf("%d. %s%n", i + 1, diffs[i].name());
        }
        int pick = readChoice(1, diffs.length);
        return diffs[pick - 1];
    }

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

    public Combatant selectTarget(List<Combatant> combatants) {
        System.out.println("Select target:");
        for (int i = 0; i < combatants.size(); i++) {
            Combatant c = combatants.get(i);
            System.out.printf("  %d. %-10s HP: %d%n", i + 1, c.getName(), c.getHp());
        }
        int idx = readChoice(1, combatants.size()) - 1;
        return combatants.get(idx);
    }

    public Item selectItem(List<Item> items) {
        System.out.println("Select item:");
        for (int i = 0; i < items.size(); i++) {
            System.out.printf("  %d. %s%n", i + 1, items.get(i).getName());
        }
        return items.get(readChoice(1, items.size()) - 1);
    }

    public void displayActionResult(String msg) {
        System.out.println("  >> " + msg);
    }

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
