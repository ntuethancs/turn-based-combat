package entity.item;

import entity.action.ActionContext;
import entity.effect.SmokeBombEffect;

public class SmokeBomb extends Item {
    public SmokeBomb() { this.name = "Smoke Bomb"; }

    @Override
    public void use(ActionContext ctx) {
        SmokeBombEffect smoke = new SmokeBombEffect(2);
        ctx.actor.status.add(smoke, ctx.ui);
        used = true;
        ctx.ui.displayActionResult(ctx.actor.getName() + " throws a Smoke Bomb! Enemy attacks deal 0 damage for 2 turns.");
    }
}