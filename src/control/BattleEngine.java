package control;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boundary.GameUI;
import control.strategy.TurnOrderStrategy;
import entity.action.ActionContext;
import entity.combatant.Combatant;
import entity.combatant.enemy.Enemy;
import entity.combatant.player.Player;
import entity.level.Level;

public class BattleEngine {

    private final GameUI ui;
    private final TurnOrderStrategy turnStrategy;
    private final Level level;
    private final Player player;
    private final List<Combatant> allCombatants = new ArrayList<>();
    private int currentRound = 0;
    private int levelNumber = 1;
    private int enemiesKilled = 0;

    public BattleEngine(GameUI ui, TurnOrderStrategy turnStrategy, Level level, Player player) {
        this(ui, turnStrategy, level, player, 1);
    }

    public BattleEngine(GameUI ui, TurnOrderStrategy turnStrategy, Level level, Player player, int levelNumber) {
        this.ui = ui;
        this.turnStrategy = turnStrategy;
        this.level = level;
        this.player = player;
        this.levelNumber = levelNumber;
        allCombatants.add(player);
        allCombatants.addAll(level.getInitialEnemies());
    }

    public boolean startBattle() {
        while (true) {
            currentRound++;

            if (getLivingEnemies().isEmpty() && level.isBackupAvailable()) {
                List<Enemy> backup = level.triggerBackup();
                allCombatants.addAll(backup);
                ui.displayActionResult("--- BACKUP SPAWN! " + backup.stream()
                        .map(Enemy::getName).collect(Collectors.joining(", ")) + " enter the arena! ---");
            }

            ui.displayRoundStart(currentRound, allCombatants);

            List<Combatant> turnOrder = turnStrategy.determineTurnOrder(
                    allCombatants.stream().filter(Combatant::isAlive).collect(Collectors.toList()));

            for (Combatant combatant : turnOrder) {
                if (!combatant.isAlive()) continue;
                takeTurn(combatant);
                if (isBattleOver()) {
                    return player.isAlive();
                }
            }
        }
    }

    public void takeTurn(Combatant combatant) {
        ActionContext ctx = new ActionContext(combatant, allCombatants, ui);
        int aliveBefore = getLivingEnemies().size();
        combatant.takeTurn(ctx);
        int aliveAfter = getLivingEnemies().size();
        enemiesKilled += (aliveBefore - aliveAfter);
    }

    public boolean isBattleOver() {
        if (!player.isAlive()) return true;
        if (getLivingEnemies().isEmpty() && !level.isBackupAvailable()) return true;
        return false;
    }

    private List<Enemy> getLivingEnemies() {
        return allCombatants.stream()
            .filter(c -> c instanceof Enemy && c.isAlive())
            .map(c -> (Enemy) c)
            .collect(Collectors.toList());
    }

    public int getRound()        { return currentRound; }
    public int getLevelNumber()  { return levelNumber;  }
    public int getEnemiesKilled(){ return enemiesKilled; }
    public Player getPlayer()    { return player; }
}