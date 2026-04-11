package entity.combatant.player;

import java.util.List;

import entity.action.ActionContext;
import entity.action.interfaces.Action;
import entity.action.player.DefendAction;
import entity.action.player.ItemAction;
import entity.combatant.Combatant;
import entity.combatant.helpers.Inventory;
import entity.item.Item;

public abstract class Player extends Combatant {
    public final Inventory inventory;

    public Player(String name, int hp, int attack, int defense, int speed, List<Item> items) {
        super(name, hp, attack, defense, speed);
        actions.add(new DefendAction());
        actions.add(new ItemAction());
        this.inventory = new Inventory(items);
    }

    public Action chooseAction(ActionContext ctx) { 
        Action chosen = ctx.ui.selectAction(actions.all(), actions.ready(ctx), this);
        if (chosen instanceof ItemAction) {
            ctx.selectedItem = ctx.ui.selectItem(inventory.usableItems());
        }
        return chosen;
    }
}