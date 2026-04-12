package entity.equipment.artifact;

import entity.combatant.helpers.Stats;
import entity.equipment.Equipment;
import entity.equipment.EquipmentType;

public class SwiftBoots extends Equipment {
    public SwiftBoots() {
        super("Swift Boots", new Stats(0, 0, 0, 8), EquipmentType.ARTIFACT);
    }
}
