package entity.equipment.weapon;

import entity.combatant.helpers.Stats;
import entity.equipment.Equipment;
import entity.equipment.EquipmentType;

public class Staff extends Equipment {
    public Staff() {
        super("Staff", new Stats(0, 20, -5, 0), EquipmentType.WEAPON);
    }
}
