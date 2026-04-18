package entity.combatant.player;

import entity.equipment.Equipment;
import entity.interfaces.Factory;
import entity.item.Item;

public class PlayerBuilder extends Factory {
    private Player player;

    public PlayerBuilder createPlayer(Class<? extends Player> selection) {
        player = instantiate(selection);
        return this;
    }

    public PlayerBuilder addItems(java.util.List<Class<? extends Item>> selection) {
        for (Class<? extends Item> s : selection) {
            Item item = instantiate(s);
            player.inventory.addItem(item);
        }
        return this;
    }

    public PlayerBuilder addEquipment(Class<? extends Equipment> selection) {
        Equipment equipment = instantiate(selection);
        player.equipment.equip(equipment);
        return this;
    }

    public Player build() {
        return player;
    }
}
