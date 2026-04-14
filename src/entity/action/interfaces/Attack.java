package entity.action.interfaces;

import java.util.Random;

import entity.action.ActionContext;
import entity.combatant.Combatant;
import entity.combatant.helpers.StatField;

public interface Attack extends Action {
    static final Random random = new Random();

    default int getDamage(Combatant target, ActionContext ctx) {
        int dmg = ctx.actor.stats().get(StatField.attack);
        boolean isCrit = random.nextInt(100) < ctx.actor.stats().get(StatField.critRate);

        if (isCrit) { 
            dmg = (int) (dmg * (1 + ctx.actor.stats().get(StatField.critDamage) / 100.0)); 
            ctx.ui.displayActionResult("CRITICAL HIT!"); 
        }

        return Math.max(0, dmg - target.stats().get(StatField.defense));
    }

    default void displayAttack(Combatant target, ActionContext ctx) {
        ctx.ui.displayActionResult(ctx.actor.getName() + " " + getVerb() + " " + target.getName() + "!");
    }

    @Override
    default boolean executeOn(Combatant target, ActionContext ctx) {
        displayAttack(target, ctx);
        if (!target.attackHit(ctx)) return false;
        int dmg = getDamage(target, ctx);
        target.takeDamage(dmg, ctx);
        return true;
    }

    @Override
    default String getLabel() { return "Basic Attack"; }

    default String getVerb() { return "attacks"; }
}
