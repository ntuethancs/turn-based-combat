package entity.equipment.artifact;

import entity.combatant.helpers.Stats;
import entity.effect.base.PermanentEffect;
import entity.effect.equipment.ManaGemEffect;
import entity.equipment.Equipment;
import entity.equipment.EquipmentType;
import entity.equipment.SpecialEffectEquipment;

public class ManaGem extends Equipment implements SpecialEffectEquipment {
    public ManaGem() {
        super("Mana Gem", new Stats(0, 0, 0, 0), EquipmentType.ARTIFACT);
    }

    @Override
    public PermanentEffect createSpecialEffect() {
        return new ManaGemEffect();
    }
}
