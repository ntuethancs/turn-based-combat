package entity.action.enemy;

import entity.action.ActionContext;
import entity.action.interfaces.SpecialAttack;
import entity.action.interfaces.SplashAttack;
import entity.combatant.Combatant;
import entity.effect.StunEffect;

public class TailWhip extends SpecialAttack implements SplashAttack {
    public TailWhip() { super(3); }

    @Override
    public boolean executeOn(Combatant target, ActionContext ctx) {
        if (super.executeOn(target, ctx) && target.isAlive()) {
            StunEffect stun = new StunEffect(1);
            target.status.add(stun, ctx.ui);
            return true;
        } 
        return false;
    }

    @Override 
    public String getLabel() { return "Tail Whip"; }

    @Override 
    public String getVerb() { return "Tail Whips"; }
}
