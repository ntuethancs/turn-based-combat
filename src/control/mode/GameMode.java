package control.mode;

import boundary.GameUI;
import control.BattleEngine;
import entity.level.Level;

public interface GameMode {
    String getName();
    Level getNextLevel(int roundNumber);
    boolean isBattleOver(BattleEngine engine);
    void onRoundEnd(BattleEngine engine, GameUI ui);
    boolean allowItemSelection();
    boolean allowWeaponSelection();
}
