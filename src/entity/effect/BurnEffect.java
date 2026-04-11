package entity.effect;

import boundary.GameUI;
import entity.combatant.CombatEvent;
import entity.combatant.Combatant;
import entity.effect.base.DurationEffect;

public class BurnEffect extends DurationEffect {
    public final int damage;
    
    public BurnEffect(String name, int duration, int damage) {
        super(name, duration);
        this.damage = damage;
        addTrigger(CombatEvent.TURN_END, this::tick);
    }

    public void apply(Combatant target, GameUI ui) {}

    public boolean tick(Combatant target, GameUI ui) {
        ui.displayActionResult(target.getName() + " is BURNING!");
        target.takeDamage(damage, ui);
        return super.tick(target, ui);
    }


}
