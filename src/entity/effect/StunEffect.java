package entity.effect;

import boundary.GameUI;
import entity.action.ActionContext;
import entity.combatant.CombatEvent;
import entity.combatant.Combatant;
import entity.effect.base.DurationEffect;
import entity.effect.base.NonStackableEffect;

public class StunEffect extends DurationEffect implements NonStackableEffect {
    public StunEffect(int duration) { 
        super(duration);
        addTrigger(CombatEvent.TURN_START, this::tick);
    }

    public void apply(Combatant target, GameUI ui) { 
        ui.displayActionResult(target.getName() + " is STUNNED for " + duration + " turns!");
    }

    @Override
    public boolean tick(ActionContext ctx) {
        ctx.ui.displayActionResult(ctx.actor.getName() + " is STUNNED -- turn skipped!");
        super.tick(ctx);
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends NonStackableEffect> T combine(T other) {
        if (!(other instanceof StunEffect)) return (T) this;
        StunEffect otherStun = (StunEffect) other;
        return (T) (this.getDuration() >= otherStun.getDuration() ? this : otherStun);
    }
}