package entity.effect;

import boundary.GameUI;
import entity.action.ActionContext;
import entity.combatant.CombatEvent;
import entity.combatant.Combatant;
import entity.effect.base.DurationEffect;

public class BurnEffect extends DurationEffect {
    public final int damage;
    
    public BurnEffect(String name, int duration, int damage) {
        super(duration);
        this.damage = damage;
        addTrigger(CombatEvent.TURN_END, this::tick);
    }

    public void apply(Combatant target, GameUI ui) {}

    @Override
    public boolean tick(ActionContext ctx) {
        ctx.ui.displayActionResult(ctx.actor.getName() + " is BURNING!");
        ctx.actor.takeDamage(damage, ctx.ui);
        return super.tick(ctx);
    }


}
