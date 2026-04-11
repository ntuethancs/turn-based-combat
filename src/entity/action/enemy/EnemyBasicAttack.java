package entity.action.enemy;

import entity.action.ActionContext;
import entity.action.interfaces.SingleTargetAttack;
import entity.combatant.Combatant;

public class EnemyBasicAttack implements SingleTargetAttack {
    @Override
    public Combatant selectTarget(ActionContext ctx) {
        return ctx.getLivingOpponents().get(0);
    }

    @Override public String getLabel() { return "Basic Attack"; }
}