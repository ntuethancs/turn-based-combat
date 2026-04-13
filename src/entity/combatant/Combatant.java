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


    public Combatant(String name, int hp, int attack, int defense, int speed) {
        this.name = name;
        this.hp = hp;
        this.baseStats = new Stats(hp, attack, defense, speed);
        this.statEffects = new Stats();
        this.status = new StatusManager(this);
        this.actions = new ActionMenu();
    }

    public abstract Action chooseAction(ActionContext ctx);

    public void takeTurn(ActionContext ctx) {
        if (status.trigger(CombatEvent.TURN_START, ctx)) {
            ctx.action = chooseAction(ctx);
            actions.decrementCooldowns();
            ctx.action.execute(ctx);
        } else {
            actions.decrementCooldowns();
        }
        status.trigger(CombatEvent.TURN_END, ctx);
        status.removeExpired();
    }

    public void takeDamage(int dmg, GameUI ui) {
        hp = Math.max(0, hp - dmg);
        if (ui != null) {
            ui.displayActionResult(dmg + " dmg dealt! HP: " + hp + "/" + stats().get(StatField.maxHp));
            if (!isAlive()) ui.displayActionResult(name + " is ELIMINATED!");
        }
    }

    public boolean takeAttack(int dmg, ActionContext ctx) {
        if (!status.trigger(CombatEvent.ATTACKED, ctx)) return false;
        takeDamage(dmg, ctx.ui);
        status.trigger(CombatEvent.DAMAGE_TAKEN, ctx);
        return true;
    }

    public Stats stats() { return baseStats.add(statEffects); }
    public void setHp(int hp) { this.hp = hp; }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public boolean isAlive() { return hp > 0; }
}
