package entity.equipment.artifact;

import entity.combatant.helpers.Stats;
import entity.equipment.Equipment;
import entity.equipment.EquipmentType;

public class IronShield extends Equipment {
    public IronShield() {
        super("Iron Shield", new Stats(0, 0, 5, 0), EquipmentType.ARTIFACT);
    }
}
