package entity.equipment.weapon;

import control.Registry;
import entity.equipment.Equipment;

public class WeaponRegistry extends Registry<Equipment> {
    private static final WeaponRegistry instance = new WeaponRegistry();

    private WeaponRegistry() {
        Class<?>[] classes = { Sword.class, Staff.class, Dagger.class };
        for (Class<?> cls : classes) {
            @SuppressWarnings("unchecked")
            Class<? extends Equipment> equipClass = (Class<? extends Equipment>) cls;
            register(equipClass);
        }
    }

    public static WeaponRegistry getInstance() {
        return instance;
    }
}
