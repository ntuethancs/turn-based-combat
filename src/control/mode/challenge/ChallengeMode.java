package control.mode.challenge;

import java.util.List;

import boundary.UserInterface;
import control.BattleEngine;
import control.mode.GameMode;
import entity.combatant.player.Player;
import entity.combatant.player.Warrior;
import entity.equipment.Equipment;
import entity.equipment.artifact.IronShield;
import entity.equipment.weapon.Sword;
import entity.item.Item;
import entity.item.Potion;

public class ChallengeMode extends GameMode {
    public ChallengeMode() {
        super(new ChallengeLevelGenerator());
    }

    @Override
    public String getName() { return "Challenge Mode"; }

    @Override
    public String getDescription() { return "Fixed loadout, Boss battle"; }

    @Override
    public Class<? extends Player> getPlayerSelection(UserInterface ui) {
        ui.displayActionResult("Challenge Mode: Warrior selected as fixed class.");
        return Warrior.class;
    }

    @Override
    public List<Class<? extends Item>> getItemSelection(UserInterface ui) {
        ui.displayActionResult("Challenge Mode: 2x Potion assigned as fixed items.");
        return List.of(Potion.class, Potion.class);
    }

    @Override
    public Class<? extends Equipment> getWeaponSelection(UserInterface ui) {
        ui.displayActionResult("Challenge Mode: sword selected as fixed weapon.");
        return Sword.class;
    }

    @Override
    public Class<? extends Equipment> getArtifactSelection(UserInterface ui) {
        ui.displayActionResult("Challenge Mode: Iron Shield selected as fixed artifact.");
        return IronShield.class;
    }

    @Override
    public boolean isBattleOver(BattleEngine engine) {
        return engine.isBattleOver();
    }

    @Override
    public void onRoundEnd(BattleEngine engine, UserInterface ui) {
        if (engine.getPlayer().isAlive()) {
            ui.displayActionResult("--- Challenge complete! You conquered Boss mode with a fixed loadout! ---");
        } else {
            ui.displayActionResult("--- Challenge failed. Adapt your tactics and try again! ---");
        }
    }
}
