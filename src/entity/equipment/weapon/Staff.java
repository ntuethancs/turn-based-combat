package entity.equipment.weapon;

import entity.combatant.helpers.StatField;
import entity.equipment.Equipment;
import entity.equipment.EquipmentType;

public class Staff extends Equipment {
    public Staff() {
        super(EquipmentType.WEAPON);
        stats.add(StatField.attack, 20);
        stats.subtract(StatField.defense, 5);
    }
}
