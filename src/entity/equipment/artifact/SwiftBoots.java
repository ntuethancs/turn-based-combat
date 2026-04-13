package entity.equipment.artifact;

import entity.combatant.helpers.StatField;
import entity.equipment.Equipment;
import entity.equipment.EquipmentType;

public class SwiftBoots extends Equipment {
    public SwiftBoots() {
        super(EquipmentType.ARTIFACT);
        stats.add(StatField.speed, 8);
    }

    @Override
    public String getDescription() { return "+8 SPD permanently"; }
}
