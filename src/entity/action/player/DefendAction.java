package entity.action.player;
import entity.action.ActionContext;
import entity.action.interfaces.SelfAction;
import entity.combatant.Combatant;
import entity.combatant.helpers.StatField;
import entity.effect.base.DurationStatEffect;

public class DefendAction implements SelfAction {

    @Override
    public void executeOn(Combatant target, ActionContext ctx) {
        DurationStatEffect defendEffect = new DurationStatEffect("Defend", 2, 10, StatField.defense);
        target.status.add(defendEffect, ctx.ui);
        ctx.ui.displayActionResult(target.getName() + " defends! +10 DEF for 2 turns.");
    }

    @Override public String getLabel() { return "Defend"; }
}