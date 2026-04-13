package entity.effect.equipment;

import boundary.GameUI;
import entity.combatant.Combatant;
import entity.effect.base.NonStackableEffect;
import entity.effect.base.PermanentEffect;

public class EquipmentEffect extends PermanentEffect implements NonStackableEffect {

    public void apply(Combatant target, GameUI ui) {}

    public void remove(Combatant target, GameUI ui) {}
}
