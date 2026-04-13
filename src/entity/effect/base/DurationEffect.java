package entity.effect.base;

import boundary.GameUI;
import entity.action.ActionContext;
import entity.combatant.Combatant;

public abstract class DurationEffect extends StatusEffect {
    protected int duration;

    public DurationEffect(int duration) { this.duration = duration; }

    public int getDuration() { return duration; }
    public boolean isExpired() { return duration <= 0; }

    public void remove(Combatant target, GameUI ui) {
        ui.displayActionResult(getName() + " effect on " + target.getName() + " has expired.");
    }

    public boolean tick(ActionContext ctx) {
        duration--;
        if (isExpired()) remove(ctx.actor, ctx.ui);
        return true;
    }

    @Override
    public String toString() {
        return "[" + getName() + " " + duration + "]";
    }
}