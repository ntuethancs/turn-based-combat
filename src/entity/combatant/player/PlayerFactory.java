package entity.combatant.player;

import java.util.List;

import entity.equipment.Equipment;
import entity.item.Item;

public class PlayerFactory {
    private Player player;

    public PlayerFactory createPlayer(int type) {
        player = PlayerRegistry.getInstance().create(type - 1);
        return this;
    }

    public PlayerFactory addItems(List<Item> items) {
        for (Item item : items) player.inventory.addItem(item);
        return this;
    }

    public PlayerFactory addEquipment(Equipment equipment) {
        player.equipment.equip(equipment);
        return this;
    }

    public Player build() {
        return player;
    }
}
