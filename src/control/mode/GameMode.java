package control.mode;

import java.util.Iterator;
import java.util.List;

import boundary.UserInterface;
import control.BattleEngine;
import entity.equipment.Equipment;
import entity.item.Item;
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

    public int selectPlayerType(UserInterface ui) {
        return ui.selectPlayerType();
    }

    public List<Item> selectItems(UserInterface ui) {
        return ui.selectItems();
    }

    public Equipment selectWeapon(UserInterface ui) {
        return ui.selectWeapon();
    }

    public Equipment selectArtifact(UserInterface ui) {
        return ui.selectArtifact();
    }

    public boolean isBattleOver(BattleEngine engine) {
        return engine.isBattleOver();
    }

    public abstract void onRoundEnd(BattleEngine engine, UserInterface ui);
}
