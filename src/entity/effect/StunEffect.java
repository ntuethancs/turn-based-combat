package entity.effect;

import boundary.GameUI;
import entity.combatant.Combatant;

public class StunEffect extends StatusEffect {
    public StunEffect(int duration) { 
        name = "Stun"; 
        this.duration = duration;
        begin = false; 
    }

    @Override
    public void onExpire(Combatant c, GameUI ui) {
        ui.displayActionResult("Stun effect on " + c.getName() + " has expired.");
    }
}