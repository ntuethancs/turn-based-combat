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
        this.statEffects = new Stats(0, 0, 0, 0);
        this.status = new StatusManager(this);
        this.actions = new ActionMenu();
    }


    public abstract Action chooseAction(ActionContext ctx);

    public void takeTurn(ActionContext ctx) {
        if (status.trigger(CombatEvent.TURN_START, ctx.ui)) {
            Action chosen = chooseAction(ctx);
            actions.decrementCooldowns();
            chosen.execute(ctx);
        } else {
            actions.decrementCooldowns();
        }
        status.trigger(CombatEvent.TURN_END, ctx.ui);
        status.removeExpired();
    }

    public void takeDamage(int dmg, GameUI ui) {
        hp = Math.max(0, hp - dmg);
        ui.displayActionResult(dmg + " dmg dealt! HP: " + hp + "/" + stats().get(StatField.maxHp));
        if (!isAlive())
            ui.displayActionResult(name + " is ELIMINATED!");
    }

    public boolean takeAttack(int dmg, GameUI ui) {
        if (!status.trigger(CombatEvent.ATTACKED, ui)) return false;
        takeDamage(dmg, ui);
        return true;
    }
    
    public Stats stats() { return baseStats.add(statEffects); }
    public void setHp(int hp) { this.hp = hp; }
    
    // Getters
    public String getName() { return name; }
    public int getHp() { return hp; }
    public boolean isAlive() { return hp > 0; }
}