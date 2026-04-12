package control.mode;

import boundary.GameUI;
import control.BattleEngine;
import entity.level.Level;

public abstract class GameMode {
    public String getName() { return getClass().getSimpleName(); }
    public abstract Level getNextLevel(int roundNumber);

    public boolean allowClassSelection() {
        return true;
    }

    public boolean allowItemSelection() {
        return true;
    }

    public boolean allowEquipmentSelection() {
        return true;
    }

    public boolean isBattleOver(BattleEngine engine) {
        return engine.isBattleOver();
    }

    public abstract void onRoundEnd(BattleEngine engine, GameUI ui);
}
