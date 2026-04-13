package entity.combatant.player;

import java.util.List;

import entity.action.player.warrior.ShieldBash;
import entity.action.player.warrior.WarriorBasicAttack;
import entity.combatant.helpers.EquipmentManager;
import entity.item.Item;

public class Warrior extends Player {
    public Warrior(List<Item> items) {
        this(items, new EquipmentManager());
    }

    public Warrior(List<Item> items, EquipmentManager equipment) {
        super("Warrior", 260, 40, 20, 30, items, equipment);
        actions.add(new WarriorBasicAttack());
        actions.add(new ShieldBash());
    }
}
