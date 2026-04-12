package entity.equipment.artifact;

import entity.combatant.helpers.Stats;
import entity.effect.base.PermanentEffect;
import entity.effect.equipment.VampiricAmuletEffect;
import entity.equipment.Equipment;
import entity.equipment.EquipmentType;
import entity.equipment.SpecialEffectEquipment;

public class VampiricAmulet extends Equipment implements SpecialEffectEquipment {
    public VampiricAmulet() {
        super("Vampiric Amulet", new Stats(0, 0, 0, 0), EquipmentType.ARTIFACT);
    }

    @Override
    public PermanentEffect createSpecialEffect() {
        return new VampiricAmuletEffect();
    }
}
