package entity.action.player.wizard;

import java.util.List;

import entity.action.ActionContext;
import entity.action.interfaces.SpecialAttack;
import entity.action.interfaces.SplashAttack;
import entity.combatant.Combatant;
import entity.effect.ArcaneBlastEffect;

public class ArcaneBlast extends SpecialAttack implements SplashAttack {

    public ArcaneBlast() { super(2); }

    @Override
    public String getVerb() { return "Arcane Blasts"; }

    protected int getArcaneBonus(List<Combatant> targets) {
        int kills = 0;
        for (Combatant t : targets) {
            if (!t.isAlive()) { kills++; }
        }
        return kills * 10;
    }

    @Override
    public boolean execute(ActionContext ctx) {
        if (super.execute(ctx)) { 
            int bonus = getArcaneBonus(ctx.targets);
            ArcaneBlastEffect buff = new ArcaneBlastEffect(bonus);
            ctx.actor.status.add(buff, ctx.ui);
            return true; 
        }
        return false;
    }

    @Override public String getLabel() { return "Arcane Blast"; }
}
