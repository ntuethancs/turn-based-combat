package entity.effect.equipment;

import entity.action.ActionContext;
import entity.action.interfaces.SpecialAttack;
import entity.combatant.CombatEvent;

public class ManaGemEffect extends EquipmentEffect {
    public ManaGemEffect() { addTrigger(CombatEvent.TURN_END, this::onTurnEnd); }

    private boolean onTurnEnd(ActionContext ctx) {
        if (!(ctx.action instanceof SpecialAttack)) return true;
        SpecialAttack special = ctx.actor.actions.getSpecial();
        if (special == null || special.getCooldown() <= 0) return true;
        special.setCooldown(Math.max(0, special.getCooldown() - 1));
        if (ctx.ui != null) {
            ctx.ui.displayActionResult(ctx.actor.getName() + "'s Mana Gem reduces special cooldown by 1!");
        }
        return true;
    }
}
