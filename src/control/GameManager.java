package control;

import java.util.ArrayList;
import java.util.List;

import boundary.GameUI;
import control.mode.GameMode;
import control.strategy.SpeedBasedTurnOrder;
import entity.combatant.player.Player;
import entity.combatant.player.Warrior;
import entity.combatant.player.Wizard;
import entity.equipment.EquipManager;
import entity.equipment.Equipment;
import entity.item.Item;
import entity.item.Potion;
import entity.level.Level;

public class GameManager {

    private final GameUI ui = new GameUI();

    public void startGame() {
        ui.displayWelcome();

        GameMode mode = null;
        int playerType = -1;
        List<Item> itemChoices = null;
        Equipment weaponChoice = null;
        Equipment artifactChoice = null;
        boolean replayWithSame = false;

        while (true) {
            if (!replayWithSame) {
                mode = ui.selectGameMode();

                if (mode.allowClassSelection()) {
                    playerType = ui.selectPlayerType();
                } else {
                    playerType = 1;
                    ui.displayActionResult("Challenge Mode: Warrior selected as fixed class.");
                }

                if (mode.allowItemSelection()) {
                    itemChoices = ui.selectItems();
                } else {
                    itemChoices = List.of(new Potion(), new Potion());
                    ui.displayActionResult("Challenge Mode: 2x Potion assigned as fixed items.");
                }

                if (mode.allowEquipmentSelection()) {
                    weaponChoice = ui.selectWeapon();
                    artifactChoice = ui.selectArtifact();
                } else {
                    weaponChoice = null;
                    artifactChoice = null;
                    ui.displayActionResult("Challenge Mode: no equipment selected.");
                }
            }

            boolean won = runMode(mode, playerType, itemChoices, weaponChoice, artifactChoice);
            ui.displayModeEnd(won, mode);

            int choice = ui.askReplay();
            if (choice == 1) {
                replayWithSame = true;
            } else if (choice == 2) {
                replayWithSame = false;
            } else {
                System.out.println("Thanks for playing! Goodbye.");
                break;
            }
        }
    }

    private boolean runMode(GameMode mode, int playerType, List<Item> itemChoices,
                            Equipment weaponChoice, Equipment artifactChoice) {
        int levelNumber = 1;

        while (true) {
            Level level = mode.getNextLevel(levelNumber);
            if (level == null) return true;

            Player player = createPlayer(playerType, cloneItems(itemChoices), weaponChoice, artifactChoice);
            BattleEngine engine = new BattleEngine(ui, new SpeedBasedTurnOrder(), level, player, levelNumber);

            engine.startBattle();
            mode.onRoundEnd(engine, ui);

            if (mode.isBattleOver(engine)) {
                return player.isAlive();
            }
            levelNumber++;
        }
    }

    private Player createPlayer(int type, List<Item> items, Equipment weapon, Equipment artifact) {
        EquipManager equipment = new EquipManager(weapon, artifact);
        if (type == 1) return new Warrior(items, equipment);
        return new Wizard(items, equipment);
    }

    private List<Item> cloneItems(List<Item> items) {
        List<Item> copies = new ArrayList<>();
        for (Item item : items) {
            if (item instanceof Potion) {
                copies.add(new Potion());
            } else if (item instanceof entity.item.PowerStone) {
                copies.add(new entity.item.PowerStone());
            } else if (item instanceof entity.item.SmokeBomb) {
                copies.add(new entity.item.SmokeBomb());
            }
        }
        return copies;
    }
}
