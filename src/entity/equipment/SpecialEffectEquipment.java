package entity.equipment;

import entity.effect.equipment.EquipmentEffect;

public abstract class SpecialEffectEquipment extends Equipment {
    public final EquipmentEffect effect;

    public SpecialEffectEquipment(EquipmentType type, EquipmentEffect effect) {
        super(type);
        this.effect = effect;
    }
}
