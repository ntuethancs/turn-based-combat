package entity.equipment.artifact;

import entity.effect.equipment.VampiricAmuletEffect;
import entity.equipment.EquipmentType;
import entity.equipment.SpecialEffectEquipment;

public class VampiricAmulet extends SpecialEffectEquipment {
    public VampiricAmulet() {
        super(EquipmentType.ARTIFACT, new VampiricAmuletEffect());
    }
}
