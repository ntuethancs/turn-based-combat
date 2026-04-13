package entity.effect;

import entity.combatant.helpers.StatField;
import entity.effect.base.DurationStatEffect;

public class DefendEffect extends DurationStatEffect{
    public DefendEffect() {
        super(2, 10, StatField.defense);
    }
}
