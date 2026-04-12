package entity.equipment.weapon;

import entity.combatant.helpers.Stats;
import entity.equipment.Equipment;
import entity.equipment.EquipmentType;

public class Sword extends Equipment {
    public Sword() {
        super("Sword", new Stats(0, 15, 0, 0), EquipmentType.WEAPON);
    }
}
