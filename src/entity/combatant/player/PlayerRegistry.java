package entity.combatant.player;

import java.util.List;

import control.Registry;
import entity.combatant.helpers.EquipmentManager;
import entity.item.Item;

public class PlayerRegistry extends Registry<Player> {
    private static final PlayerRegistry instance = new PlayerRegistry();

    private PlayerRegistry() {
        Class<?>[] classes = {
            Warrior.class,
            Wizard.class
        };
        for (Class<?> cls : classes) {
            @SuppressWarnings("unchecked")
            Class<? extends Player> playerClass = (Class<? extends Player>) cls;
            register(playerClass);
        }
    }

    public static PlayerRegistry getInstance() {
        return instance;
    }

    public Player create(int index, List<Item> items, EquipmentManager equipment) {
        List<Entry<Player>> list = getEntries();
        if (index < 0 || index >= list.size()) return null;
        try {
            return list.get(index).type
                .getDeclaredConstructor(List.class, EquipmentManager.class)
                .newInstance(items, equipment);
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate " + list.get(index).type.getName(), e);
        }
    }
}
