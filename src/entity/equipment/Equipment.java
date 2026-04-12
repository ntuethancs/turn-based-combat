package entity.equipment;

import entity.combatant.helpers.Stats;

public abstract class Equipment {
    protected final String name;
    protected final Stats statBonus;
    protected final EquipmentType equipmentType;

    protected Equipment(String name, Stats statBonus, EquipmentType equipmentType) {
        this.name = name;
        this.statBonus = statBonus;
        this.equipmentType = equipmentType;
    }

    public String getName() {
        return name;
    }

    public Stats getStatBonus() {
        return statBonus;
    }

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    @Override
    public String toString() {
        return name;
    }
}
