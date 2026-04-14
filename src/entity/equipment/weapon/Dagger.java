package entity.equipment.weapon;

import entity.combatant.helpers.StatField;
import entity.equipment.Equipment;
import entity.equipment.EquipmentType;

public class Dagger extends Equipment {
    public Dagger() {
        super(EquipmentType.WEAPON);
        stats.add(StatField.attack, 10)
            .add(StatField.speed, 10);
    }

    @Override
    public String getDescription() { return "+10 ATK, +10 SPD"; }
}
