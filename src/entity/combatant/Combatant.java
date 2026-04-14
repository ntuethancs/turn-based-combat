package entity.combatant;

import entity.action.ActionContext;
import entity.action.interfaces.Action;
import entity.combatant.helpers.ActionMenu;
import entity.combatant.helpers.StatField;
import entity.combatant.helpers.Stats;
import entity.combatant.helpers.StatusManager;
import entity.interfaces.Describable;
import entity.interfaces.Named;

public abstract class Combatant implements Named, Describable {
    protected int hp;
    protected final Stats baseStats;
    public final Stats statEffects;
    public final StatusManager status;
    public final ActionMenu actions;


    public Combatant(int hp, int attack, int defense, int speed, int critRate, int critDamage) {
        this.hp = hp;
        this.baseStats = new Stats(hp, attack, defense, speed, critRate, critDamage);
        this.statEffects = new Stats();
        this.status = new StatusManager(this);
        this.actions = new ActionMenu();
    }

    public abstract ActionContext.Team getTeam();

    public abstract Action chooseAction(ActionContext ctx);

    public abstract String getDescription();

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

    public void takeDamage(int dmg, ActionContext ctx) {
        ctx.damage += dmg;
        hp = Math.max(0, hp - dmg);
        ctx.ui.displayActionResult(dmg + " dmg dealt! HP: " + hp + "/" + stats().get(StatField.maxHp));
        if (!isAlive()) {
            ctx.ui.displayActionResult(getName() + " is ELIMINATED!");
        } else {
            status.trigger(CombatEvent.DAMAGE_TAKEN, ctx);
        }
    }

    public boolean attackHit(ActionContext ctx) {
        return status.trigger(CombatEvent.ATTACKED, ctx);
    }

    public Stats stats() { return baseStats.add(statEffects); }
    public void setHp(int hp) { this.hp = hp; }

    public int getHp() { return hp; }
    public boolean isAlive() { return hp > 0; }
}
