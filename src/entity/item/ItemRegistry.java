package entity.item;

import control.Registry;

public class ItemRegistry extends Registry<Item> {
    private static final ItemRegistry instance = new ItemRegistry();

    private ItemRegistry() {
        Class<?>[] classes = {
            Potion.class,
            PowerStone.class,
            SmokeBomb.class
        };
        for (Class<?> cls : classes) {
            @SuppressWarnings("unchecked")
            Class<? extends Item> itemClass = (Class<? extends Item>) cls;
            register(itemClass);
        }
    }

    public static ItemRegistry getInstance() {
        return instance;
    }
}
