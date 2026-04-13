package entity.effect.equipment;

import entity.action.ActionContext;
import entity.combatant.CombatEvent;
import entity.combatant.helpers.StatField;

public class VampiricAmuletEffect extends EquipmentEffect {
    public VampiricAmuletEffect() { addTrigger(CombatEvent.TURN_END, this::onTurnEnd); }

    private boolean onTurnEnd(ActionContext ctx) {
        int healAmount = ctx.damage / 4;
        if (healAmount <= 0) return true;

        int before = ctx.actor.getHp();
        int maxHp = ctx.actor.stats().get(StatField.maxHp);
        ctx.actor.setHp(Math.min(maxHp, before + healAmount));
        int healed = ctx.actor.getHp() - before;
        if (healed > 0 && ctx.ui != null) {
            ctx.ui.displayActionResult(ctx.actor.getName() + " absorbs life and heals " + healed + " HP!");
        }
        return true;
    }
}
