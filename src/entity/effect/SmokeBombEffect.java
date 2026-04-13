package entity.effect;

import boundary.GameUI;
import entity.action.ActionContext;
import entity.combatant.CombatEvent;
import entity.combatant.Combatant;
import entity.effect.base.DurationEffect;
import entity.effect.base.NonStackableEffect;

public class SmokeBombEffect extends DurationEffect implements NonStackableEffect {
    public SmokeBombEffect(int duration) { 
        super(duration);
        addTrigger(CombatEvent.ATTACKED, this::onDamageTaken);
        addTrigger(CombatEvent.TURN_START, this::tick);
    }

    public void apply(Combatant target, GameUI ui) {}

    private boolean onDamageTaken(ActionContext ctx) { 
        ctx.ui.displayActionResult("0 damage (Smoke Bomb active)!");
        return false; 
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends NonStackableEffect> T combine(T other) {
        if (!(other instanceof SmokeBombEffect)) return (T) this;
        SmokeBombEffect otherSmoke = (SmokeBombEffect) other;
        return (T) (this.getDuration() >= otherSmoke.getDuration() ? this : otherSmoke);
    }
}