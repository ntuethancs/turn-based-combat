package entity.action.player;

import entity.action.ActionContext;
import entity.action.interfaces.SelfAction;
import entity.combatant.Combatant;
import entity.combatant.player.Player;

public class ItemAction implements SelfAction {

    @Override
    public boolean isReady(ActionContext ctx) {
        return ctx.actor instanceof Player && !((Player) ctx.actor).inventory.isEmpty();
    }

    @Override
    public boolean executeOn(Combatant target, ActionContext ctx) {
        if (ctx.selectedItem == null) {
            ctx.ui.displayActionResult("No item selected!");
            return false;
        }
        ctx.selectedItem.use(ctx);
        return true;
    }

    @Override public String getLabel() { return "Use Item"; }
}