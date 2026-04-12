package entity.effect.equipment;

import boundary.GameUI;
import entity.combatant.CombatEvent;
import entity.combatant.Combatant;
import entity.effect.base.PermanentEffect;

public class ThornArmourEffect extends PermanentEffect {
    private static final int REFLECT_DAMAGE = 5;

    public ThornArmourEffect() {
        super("Thorn Armour");
        addTrigger(CombatEvent.DAMAGE_TAKEN, this::onDamageTaken);
    }

    @Override
    public void apply(Combatant target, GameUI ui) {
        // passive permanent effect; no immediate stat change
    }

    @Override
    public void remove(Combatant target, GameUI ui) {
        // never expires by default
    }

    private boolean onDamageTaken(Combatant target, GameUI ui) {
        Combatant attacker = target.getLastAttacker();
        if (attacker == null || !attacker.isAlive()) return true;

        attacker.takeDamage(REFLECT_DAMAGE, ui);
        if (ui != null) {
            ui.displayActionResult(target.getName() + "'s Thorn Armour reflects " + REFLECT_DAMAGE + " damage to " + attacker.getName() + "!");
        }
        return true;
    }
}
