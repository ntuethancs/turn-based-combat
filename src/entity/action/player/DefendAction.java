package entity.action.player;
import entity.action.ActionContext;
import entity.action.interfaces.SelfAction;
import entity.combatant.Combatant;
import entity.effect.DefendEffect;
import entity.effect.base.DurationStatEffect;

public class DefendAction implements SelfAction {

    @Override
    public boolean executeOn(Combatant target, ActionContext ctx) {
        DurationStatEffect defendEffect = new DefendEffect();
        target.status.add(defendEffect, ctx.ui);
        ctx.ui.displayActionResult(target.getName() + " defends! +10 DEF for 2 turns.");
        return true;
    }

    @Override public String getLabel() { return "Defend"; }
}