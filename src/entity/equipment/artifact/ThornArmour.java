package entity.equipment.artifact;

import entity.combatant.helpers.Stats;
import entity.effect.base.PermanentEffect;
import entity.effect.equipment.ThornArmourEffect;
import entity.equipment.Equipment;
import entity.equipment.EquipmentType;
import entity.equipment.SpecialEffectEquipment;

public class ThornArmour extends Equipment implements SpecialEffectEquipment {
    public ThornArmour() {
        super("Thorn Armour", new Stats(0, 0, 0, 0), EquipmentType.ARTIFACT);
    }

    @Override
    public PermanentEffect createSpecialEffect() {
        return new ThornArmourEffect();
    }
}
