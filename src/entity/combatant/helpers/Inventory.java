package entity.combatant.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import entity.item.Item;

public class Inventory {
    protected List<Item> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    public Inventory(List<Item> items) {
        this.items = new ArrayList<>(items);
    }

    public List<Item> usableItems() {
        return items.stream().filter(i -> !i.isUsed()).collect(Collectors.toList());
    }

    public boolean isEmpty() { return usableItems().isEmpty(); }

    public void addItem(Item item) { items.add(item); }
}
