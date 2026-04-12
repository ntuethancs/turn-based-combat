package entity.action.interfaces;

import entity.action.ActionContext;
import entity.combatant.Combatant;
import entity.combatant.helpers.StatField;

public interface Attack extends Action {
    default int getDamage(Combatant target, ActionContext ctx) {
        return Math.max(0, ctx.actor.stats().get(StatField.attack) - target.stats().get(StatField.defense));
    }

    default void displayAttack(Combatant target, ActionContext ctx) {
        ctx.ui.displayActionResult(ctx.actor.getName() + " " + getVerb() + " " + target.getName() + "!");
    }

    @Override
    default boolean executeOn(Combatant target, ActionContext ctx) {
        int dmg = getDamage(target, ctx);
        displayAttack(target, ctx);
        boolean hit = target.takeAttack(ctx.actor, dmg, ctx.ui);
        if (hit) ctx.actor.recordDamageDealt(dmg);
        return hit;
    }

    @Override
    default String getLabel() { return "Basic Attack"; }

    default String getVerb() { return "attacks"; }
}
