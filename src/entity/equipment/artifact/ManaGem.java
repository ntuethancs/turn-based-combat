package entity.equipment.artifact;

import entity.effect.equipment.ManaGemEffect;
import entity.equipment.EquipmentType;
import entity.equipment.SpecialEffectEquipment;

public class ManaGem extends SpecialEffectEquipment {
    public ManaGem() {
        super(EquipmentType.ARTIFACT, new ManaGemEffect());
    }

    @Override
    public String getDescription() { return "reduce special cooldown by 1 at end of turn"; }
}
