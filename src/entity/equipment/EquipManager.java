package entity.equipment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import entity.combatant.helpers.Stats;
import entity.effect.base.PermanentEffect;

public class EquipManager {
    private final Map<EquipmentType, Equipment> equipped;

    public EquipManager() {
        this.equipped = new EnumMap<>(EquipmentType.class);
    }

    public EquipManager(Equipment weapon, Equipment artifact) {
        this();
        equip(weapon);
        equip(artifact);
    }

    public void equip(Equipment equipment) {
        if (equipment == null) return;
        equipped.put(equipment.getEquipmentType(), equipment);
    }

    public Equipment unequip(EquipmentType type) {
        return equipped.remove(type);
    }

    public Equipment get(EquipmentType type) {
        return equipped.get(type);
    }

    public boolean has(EquipmentType type) {
        return equipped.containsKey(type);
    }

    public Equipment getWeapon() {
        return get(EquipmentType.WEAPON);
    }

    public Equipment getArtifact() {
        return get(EquipmentType.ARTIFACT);
    }

    public Collection<Equipment> getAllEquipped() {
        return equipped.values();
    }

    public Stats getTotalStatBonus() {
        Stats total = new Stats(0, 0, 0, 0);
        for (Equipment equipment : equipped.values()) {
            total = total.add(equipment.getStatBonus());
        }
        return total;
    }

    public List<PermanentEffect> getSpecialEffects() {
        List<PermanentEffect> effects = new ArrayList<>();
        for (Equipment equipment : equipped.values()) {
            if (equipment instanceof SpecialEffectEquipment) {
                effects.add(((SpecialEffectEquipment) equipment).createSpecialEffect());
            }
        }
        return effects;
    }
}
