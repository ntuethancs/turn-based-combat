package entity.combatant.player;

import entity.action.ActionContext;
import entity.action.interfaces.Action;
import entity.action.player.DefendAction;
import entity.action.player.ItemAction;
import entity.combatant.Combatant;
import entity.combatant.helpers.EquipmentManager;
import entity.combatant.helpers.Inventory;
import entity.combatant.helpers.Stats;

public abstract class Player extends Combatant {
    public final Inventory inventory;
    public final EquipmentManager equipment;


    public Player(int hp, int attack, int defense, int speed, int critRate, int critDamage) {
        super(hp, attack, defense, speed, critRate, critDamage);
        actions.add(new DefendAction());
        actions.add(new ItemAction());
        this.inventory = new Inventory();
        this.equipment = new EquipmentManager(this);
    }

    @Override
    public Stats stats() {
        return super.stats().add(equipment.getTotalStatBonus());
    }

    @Override
    public ActionContext.Team getTeam() { return ActionContext.Team.PLAYER; }

    public Action chooseAction(ActionContext ctx) {
        Action chosen = ctx.ui.selectAction(actions.all(), actions.ready(ctx), this);
        if (chosen instanceof ItemAction) {
            ctx.selectedItem = ctx.ui.selectItem(inventory.usableItems());
        }
        return chosen;
    }
}
