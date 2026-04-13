package entity.item;

import entity.action.ActionContext;
import entity.combatant.helpers.StatField;

public class Potion extends Item {
    public Potion() { }

    @Override
    public Item copy() { return new Potion(); }

    @Override
    public String getDescription() { return "Heal 100 HP"; }

    @Override
    public void use(ActionContext ctx) {
        int before = ctx.actor.getHp();
        int maxHp = ctx.actor.stats().get(StatField.maxHp);
        int after = Math.min(maxHp, before + 100);
        ctx.actor.setHp(after);
        used = true;
        ctx.ui.displayActionResult(ctx.actor.getName() + " uses Potion! HP: " + before +
                " --> " + ctx.actor.getHp() + "/" + ctx.actor.stats().get(StatField.maxHp));
    }
}
