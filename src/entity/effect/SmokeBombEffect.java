package entity.effect;

import boundary.GameUI;
import entity.combatant.Combatant;

public class SmokeBombEffect extends StatusEffect {
    public SmokeBombEffect(int duration) { this.name = "SmokeBomb"; this.duration = duration; }

    @Override
    public void onApply(Combatant c, GameUI ui) {}

    @Override
    public void onExpire(Combatant c, GameUI ui) {
        ui.displayActionResult("Smoke Bomb effect on " + c.getName() + " has expired.");
    }
}