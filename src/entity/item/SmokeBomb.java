package entity.item;

import entity.action.ActionContext;
import entity.effect.SmokeBombEffect;

public class SmokeBomb extends Item {
    public SmokeBomb() { }

    @Override
    public Item copy() { return new SmokeBomb(); }

    @Override
    public String getDescription() { return "Enemy attacks deal 0 dmg this turn + next"; }

    @Override
    public void use(ActionContext ctx) {
        SmokeBombEffect smoke = new SmokeBombEffect(2);
        ctx.actor.status.add(smoke, ctx.ui);
        used = true;
        ctx.ui.displayActionResult(ctx.actor.getName() + " throws a Smoke Bomb! Enemy attacks deal 0 damage for 2 turns.");
    }
}