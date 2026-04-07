package entity.item;

import java.util.List;

import boundary.GameUI;
import entity.combatant.Combatant;
import entity.combatant.interfaces.Healable;;

public class Potion extends Item {
    public Potion() { this.name = "Potion"; }

    @Override
    public void use(Combatant actor, List<Combatant> targets, GameUI ui) {
        int before = actor.getHp();
        if (actor instanceof Healable && !used) {
            ((Healable) actor).heal(100);
            used = true;
            ui.displayActionResult(actor.getName() + " uses Potion! HP: " + before +
                    " → " + actor.getHp() + "/" + actor.getMaxHp());
        }
    }
}
