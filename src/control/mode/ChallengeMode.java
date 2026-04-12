package control.mode;

import boundary.GameUI;
import control.BattleEngine;
import entity.level.Difficulty;
import entity.level.Level;

public class ChallengeMode extends GameMode {
    @Override
    public String getName() { return "Challenge Mode"; }

    @Override
    public boolean allowClassSelection() {
        return false;
    }

    @Override
    public boolean allowItemSelection() {
        return false;
    }

    @Override
    public boolean allowEquipmentSelection() {
        return false;
    }

    @Override
    public Level getNextLevel(int roundNumber) {
        if (roundNumber == 1) return new Level(Difficulty.BOSS);
        return null;
    }

    @Override
    public boolean isBattleOver(BattleEngine engine) {
        return engine.isBattleOver();
    }

    @Override
    public void onRoundEnd(BattleEngine engine, GameUI ui) {
        if (engine.getPlayer().isAlive()) {
            ui.displayActionResult("--- Challenge complete! You conquered Boss mode with a fixed loadout! ---");
        } else {
            ui.displayActionResult("--- Challenge failed. Adapt your tactics and try again! ---");
        }
    }
}
