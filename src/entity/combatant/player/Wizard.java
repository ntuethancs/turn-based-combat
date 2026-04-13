package entity.combatant.player;

import java.util.List;

import entity.action.player.wizard.ArcaneBlast;
import entity.action.player.wizard.WizardBasicAttack;
import entity.combatant.helpers.EquipmentManager;
import entity.item.Item;

public class Wizard extends Player {
    public Wizard(List<Item> items) {
        this(items, new EquipmentManager());
    }

    public Wizard(List<Item> items, EquipmentManager equipment) {
        super("Wizard", 200, 50, 10, 20, items, equipment);
        actions.add(new WizardBasicAttack());
        actions.add(new ArcaneBlast());
    }
}
