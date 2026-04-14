package entity.effect.equipment;

import entity.action.ActionContext;
import entity.combatant.CombatEvent;

public class ThornArmourEffect extends EquipmentEffect {
    private static final int REFLECT_DAMAGE = 5;

    public ThornArmourEffect() { addTrigger(CombatEvent.DAMAGE_TAKEN, this::onDamageTaken); }

    private boolean onDamageTaken(ActionContext ctx) {
        ctx.ui.displayActionResult(ctx.curTarget.getName() + "'s Thorn Armour reflects " + REFLECT_DAMAGE + " damage to " + ctx.actor.getName() + "!");
        ctx.actor.takeDamage(REFLECT_DAMAGE, ctx);
        return true;
    }
}
