package entity.equipment;

import entity.combatant.helpers.Stats;
import entity.interfaces.Named;

public abstract class Equipment implements Named {
    public final Stats stats;
    public final EquipmentType type;

    public Equipment(EquipmentType type) {
        this.stats = new Stats();
        this.type = type;
    }
}
