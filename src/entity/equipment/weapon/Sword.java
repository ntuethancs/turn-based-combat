package entity.equipment.weapon;

import entity.combatant.helpers.StatField;
import entity.equipment.Equipment;
import entity.equipment.EquipmentType;

public class Sword extends Equipment {

    public Sword() {
        super(EquipmentType.WEAPON);
        stats.add(StatField.attack, 15);
    }

    @Override
    public String getDescription() { return "+15 ATK"; }
}
