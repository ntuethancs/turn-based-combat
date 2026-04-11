package entity.action.player.warrior;

import entity.action.ActionContext;
import entity.action.interfaces.PlayerSingleTargetAttack;
import entity.action.interfaces.SpecialAttack;
import entity.combatant.Combatant;
import entity.effect.StunEffect;

public class ShieldBash extends SpecialAttack implements PlayerSingleTargetAttack {
    public ShieldBash() { super(2); }

    @Override
    public void executeOn(Combatant target, ActionContext ctx) {
        super.executeOn(target, ctx);
        if (target.isAlive()) {
            StunEffect stun = new StunEffect(2);
            target.status.add(stun, ctx.ui);
        } 
    }

    @Override 
    public String getLabel() { return "Shield Bash"; }

    @Override 
    public String getVerb() { return "Shield Bashes"; }

}
