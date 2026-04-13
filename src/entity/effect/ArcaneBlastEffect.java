package entity.effect;

import boundary.GameUI;
import entity.combatant.Combatant;
import entity.combatant.helpers.StatField;
import entity.effect.base.NonStackableEffect;
import entity.effect.base.PermanentStatEffect;

public class ArcaneBlastEffect extends PermanentStatEffect implements NonStackableEffect {
    public ArcaneBlastEffect(int bonus) { super(bonus, StatField.attack); }

    @Override
    public void apply(Combatant target, GameUI ui) {
        super.apply(target, ui);
        ui.displayActionResult("Arcane Bonus! ATK +" + value + " --> ATK now " + target.stats().get(StatField.attack));
    }
}
