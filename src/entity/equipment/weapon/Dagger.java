package entity.equipment.weapon;

import entity.combatant.helpers.Stats;
import entity.equipment.Equipment;
import entity.equipment.EquipmentType;

public class Dagger extends Equipment {
    public Dagger() {
        super("Dagger", new Stats(0, 10, 0, 10), EquipmentType.WEAPON);
    }
}
