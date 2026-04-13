package entity.equipment.artifact;

import entity.effect.equipment.ThornArmourEffect;
import entity.equipment.EquipmentType;
import entity.equipment.SpecialEffectEquipment;

public class ThornArmour extends SpecialEffectEquipment {
    public ThornArmour() {
        super(EquipmentType.ARTIFACT, new ThornArmourEffect());
    }

    @Override
    public String getDescription() { return "reflect 5 damage when hit"; }
}
