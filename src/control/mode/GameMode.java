package control.mode;

import java.util.Iterator;
import java.util.List;

import boundary.UserInterface;
import control.BattleEngine;
import entity.combatant.player.Player;
import entity.combatant.player.PlayerRegistry;
import entity.equipment.Equipment;
import entity.equipment.artifact.ArtifactRegistry;
import entity.equipment.weapon.WeaponRegistry;
import entity.item.Item;
import entity.item.ItemRegistry;
import entity.level.Level;

public abstract class GameMode implements Iterable<Level> {
    protected final LevelGenerator levelGenerator;

    protected GameMode(LevelGenerator levelGenerator) {
        this.levelGenerator = levelGenerator;
    }

    public String getName() { return getClass().getSimpleName(); }
    public abstract String getDescription();

    @Override
    public Iterator<Level> iterator() {
        return levelGenerator.iterator();
    }

    public Class<? extends Player> getPlayerSelection(UserInterface ui) {
        return ui.selectFromRegistry(PlayerRegistry.getInstance(), "SELECT YOUR PLAYER");
    }

    public List<Class<? extends Item>> getItemSelection(UserInterface ui) {
        return ui.selectMultipleFromRegistry(ItemRegistry.getInstance(), "SELECT 2 ITEMS", 2);
    }

    public Class<? extends Equipment> getWeaponSelection(UserInterface ui) {
        return ui.selectFromRegistry(WeaponRegistry.getInstance(), "SELECT 1 WEAPON");
    }

    public Class<? extends Equipment> getArtifactSelection(UserInterface ui) {
        return ui.selectFromRegistry(ArtifactRegistry.getInstance(), "SELECT 1 ARTIFACT");
    }

    public boolean isBattleOver(BattleEngine engine) {
        return engine.isBattleOver();
    }

    public abstract void onRoundEnd(BattleEngine engine, UserInterface ui);
}
