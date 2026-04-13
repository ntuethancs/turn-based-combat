package entity.equipment.weapon;

import entity.combatant.helpers.StatField;
import entity.equipment.Equipment;
import entity.equipment.EquipmentType;

public class Staff extends Equipment {
    public Staff() {
        super(EquipmentType.WEAPON);
        stats.add(StatField.attack, 20);
        stats.add(StatField.defense, -5);
    }

    @Override
    public String getDescription() { return "+20 ATK, -5 DEF"; }
}
