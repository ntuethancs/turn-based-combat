package entity.equipment.artifact;

import entity.combatant.helpers.StatField;
import entity.equipment.Equipment;
import entity.equipment.EquipmentType;

public class IronShield extends Equipment {
    public IronShield() {
        super(EquipmentType.ARTIFACT);
        stats.add(StatField.defense, 15);
        stats.subtract(StatField.speed, 10);
    }

}
