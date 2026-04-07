package entity.item;

import java.util.List;

import boundary.GameUI;
import entity.combatant.Combatant;
import entity.combatant.interfaces.SmokeBombable;

public class SmokeBomb extends Item {
    public SmokeBomb() { this.name = "Smoke Bomb"; }

    @Override
    public void use(Combatant actor, List<Combatant> targets, GameUI ui) {
        if (actor instanceof SmokeBombable && !used) {
            ((SmokeBombable) actor).applySmokeBomb(2);
            used = true;
            ui.displayActionResult(actor.getName() + " throws a Smoke Bomb! Enemy attacks deal 0 damage for 2 turns.");
        }
    }
}