package entity.combatant;

import boundary.GameUI;
import entity.action.ActionContext;
import entity.action.interfaces.Action;
import entity.combatant.helpers.ActionMenu;
import entity.combatant.helpers.StatField;
import entity.combatant.helpers.Stats;
import entity.combatant.helpers.StatusManager;

public abstract class Combatant {
    protected final String name;
    protected int hp;
    protected final Stats baseStats;
    public final Stats statEffects;
    public final StatusManager status;
    public final ActionMenu actions;

    private Combatant lastAttacker;
    private int damageDealtThisTurn;
    private boolean usedSpecialThisTurn;

    public Combatant(String name, int hp, int attack, int defense, int speed) {
        this.name = name;
        this.hp = hp;
        this.baseStats = new Stats(hp, attack, defense, speed);
        this.statEffects = new Stats(0, 0, 0, 0);
        this.status = new StatusManager(this);
        this.actions = new ActionMenu();
    }

    public abstract Action chooseAction(ActionContext ctx);

    public void takeTurn(ActionContext ctx) {
        resetTurnFlags();
        if (status.trigger(CombatEvent.TURN_START, ctx.ui)) {
            Action chosen = chooseAction(ctx);
            actions.decrementCooldowns();
            chosen.execute(ctx);
        } else {
            actions.decrementCooldowns();
        }
        status.trigger(CombatEvent.TURN_END, ctx.ui);
        status.removeExpired();
        lastAttacker = null;
    }

    public void takeDamage(int dmg, GameUI ui) {
        hp = Math.max(0, hp - dmg);
        if (ui != null) {
            ui.displayActionResult(dmg + " dmg dealt! HP: " + hp + "/" + stats().get(StatField.maxHp));
            if (!isAlive()) ui.displayActionResult(name + " is ELIMINATED!");
        }
    }

    public boolean takeAttack(int dmg, GameUI ui) {
        return takeAttack(null, dmg, ui);
    }

    public boolean takeAttack(Combatant attacker, int dmg, GameUI ui) {
        lastAttacker = attacker;
        if (!status.trigger(CombatEvent.ATTACKED, ui)) return false;
        takeDamage(dmg, ui);
        status.trigger(CombatEvent.DAMAGE_TAKEN, ui);
        return true;
    }

    public void recordDamageDealt(int dmg) {
        if (dmg > 0) damageDealtThisTurn += dmg;
    }

    public int consumeDamageDealtThisTurn() {
        int value = damageDealtThisTurn;
        damageDealtThisTurn = 0;
        return value;
    }

    public void markUsedSpecialThisTurn() {
        usedSpecialThisTurn = true;
    }

    public boolean consumeUsedSpecialThisTurn() {
        boolean value = usedSpecialThisTurn;
        usedSpecialThisTurn = false;
        return value;
    }

    protected void resetTurnFlags() {
        damageDealtThisTurn = 0;
        usedSpecialThisTurn = false;
        lastAttacker = null;
    }

    public Combatant getLastAttacker() {
        return lastAttacker;
    }

    public Stats stats() { return baseStats.add(statEffects); }
    public void setHp(int hp) { this.hp = hp; }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public boolean isAlive() { return hp > 0; }
}
