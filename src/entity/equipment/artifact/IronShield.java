package entity.equipment.artifact;

import entity.combatant.helpers.StatField;
import entity.equipment.Equipment;
import entity.equipment.EquipmentType;

public class IronShield extends Equipment {
    public IronShield() {
        super(EquipmentType.ARTIFACT);
        stats.add(StatField.defense, 15)
            .subtract(StatField.speed, 10);
    }

    @Override
    public String getDescription() { return "+15 DEF, -10 SPD"; }

}
