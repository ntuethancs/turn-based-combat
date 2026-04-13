package entity.effect.base;

import boundary.GameUI;
import entity.combatant.Combatant;
import entity.combatant.helpers.StatField;

public class PermanentStatEffect extends PermanentEffect {
    protected final int value;
    protected final StatField stat;
    
    public PermanentStatEffect(int value, StatField stat) {
        this.value = value;
        this.stat = stat;
    }

    @Override
    public void apply(Combatant target, GameUI ui) {
        target.statEffects.add(stat, value);
    }

    @Override
    public void remove(Combatant target, GameUI ui) {
        target.statEffects.subtract(stat, value);
    }
}
