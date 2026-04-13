package entity.equipment.artifact;

import control.Registry;
import entity.equipment.Equipment;

public class ArtifactRegistry extends Registry<Equipment> {
    private static final ArtifactRegistry instance = new ArtifactRegistry();

    private ArtifactRegistry() {
        Class<?>[] classes = {
            IronShield.class, SwiftBoots.class, VampiricAmulet.class,
            ThornArmour.class, ManaGem.class
        };
        for (Class<?> cls : classes) {
            @SuppressWarnings("unchecked")
            Class<? extends Equipment> equipClass = (Class<? extends Equipment>) cls;
            register(equipClass);
        }
    }

    public static ArtifactRegistry getInstance() {
        return instance;
    }
}
