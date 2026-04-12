package entity.combatant.player;

import java.util.List;

import entity.action.ActionContext;
import entity.action.interfaces.Action;
import entity.action.player.DefendAction;
import entity.action.player.ItemAction;
import entity.combatant.Combatant;
import entity.combatant.helpers.Inventory;
import entity.combatant.helpers.Stats;
import entity.effect.base.PermanentEffect;
import entity.equipment.EquipManager;
import entity.item.Item;

public abstract class Player extends Combatant {
    public final Inventory inventory;
    private final EquipManager equipment;

    public Player(String name, int hp, int attack, int defense, int speed, List<Item> items) {
        this(name, hp, attack, defense, speed, items, new EquipManager());
    }

    public Player(String name, int hp, int attack, int defense, int speed, List<Item> items, EquipManager equipment) {
        super(name, hp, attack, defense, speed);
        actions.add(new DefendAction());
        actions.add(new ItemAction());
        this.inventory = new Inventory(items);
        this.equipment = (equipment == null) ? new EquipManager() : equipment;
        for (PermanentEffect effect : this.equipment.getSpecialEffects()) {
            status.add(effect, null);
        }
    }

    @Override
    public Stats stats() {
        return super.stats().add(equipment.getTotalStatBonus());
    }

    public EquipManager getEquipment() {
        return equipment;
    }

    public Action chooseAction(ActionContext ctx) {
        Action chosen = ctx.ui.selectAction(actions.all(), actions.ready(ctx), this);
        if (chosen instanceof ItemAction) {
            ctx.selectedItem = ctx.ui.selectItem(inventory.usableItems());
        }
        return chosen;
    }
}
