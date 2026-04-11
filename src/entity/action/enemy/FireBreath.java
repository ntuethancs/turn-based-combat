package entity.action.enemy;

import entity.action.ActionContext;
import entity.action.interfaces.SpecialAttack;
import entity.action.interfaces.SplashAttack;
import entity.combatant.Combatant;
import entity.effect.BurnEffect;

public class FireBreath extends SpecialAttack implements SplashAttack {
    public FireBreath() { super(2); }

    @Override
    public boolean executeOn(Combatant target, ActionContext ctx) {
        if (super.executeOn(target, ctx) && target.isAlive()) {
            BurnEffect burn = new BurnEffect("Burn", 2, 20);
            target.status.add(burn, ctx.ui);
            return true;
        } 
        return false;
    }

    @Override
    public String getLabel() {
        return "Fire Breath";
    }

    @Override
    public String getVerb() {
        return "uses Fire Breath on";
    }
}