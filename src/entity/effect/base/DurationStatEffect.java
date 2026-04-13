package entity.effect.base;

import boundary.GameUI;
import entity.combatant.CombatEvent;
import entity.combatant.Combatant;
import entity.combatant.helpers.StatField;

public class DurationStatEffect extends DurationEffect {
    protected final int value;
    protected final StatField stat;

    public DurationStatEffect(int duration, int value, StatField stat) {
        super(duration);
        this.value = value;
        this.stat = stat;
        addTrigger(CombatEvent.TURN_START, this::tick);
    }

    @Override
    public void apply(Combatant target, GameUI ui) {
        target.statEffects.add(stat, value);
    }

    @Override
    public void remove(Combatant target, GameUI ui) {
        target.statEffects.subtract(stat, value);
        super.remove(target, ui);
    }
}
