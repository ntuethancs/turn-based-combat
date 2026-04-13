package entity.equipment.weapon;

import entity.combatant.helpers.StatField;
import entity.equipment.Equipment;
import entity.equipment.EquipmentType;

public class Dagger extends Equipment {
    public Dagger() {
        super(EquipmentType.WEAPON);
        stats.add(StatField.attack, 10);
        stats.add(StatField.speed, 10);
    }
}
