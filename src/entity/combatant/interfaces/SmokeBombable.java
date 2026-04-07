package entity.combatant.interfaces;

import java.util.List;

import boundary.GameUI;
import entity.combatant.Combatant;
import entity.effect.SmokeBombEffect;
import entity.effect.StatusEffect;

public interface SmokeBombable {

    /**
     * Grants access to the combatant's active status effects.
     * Implemented by Combatant — no duplication needed.
     */
    List<entity.effect.StatusEffect> getStatusEffects();
    void addStatusEffect(StatusEffect e);
    String getName();


    /**
     * Returns true if a SmokeBombEffect is currently active.
     * Default implementation checks the status effect list —
     * no state field needed in any class.
     */
    default boolean isSmokeBombActive() {
        return getStatusEffects().stream()
                .anyMatch(e -> e instanceof SmokeBombEffect);
    }

    default void applySmokeBomb(int duration) { addStatusEffect(new SmokeBombEffect(duration)); }

    default void attackedWithSmokeBomb(Combatant attacker, GameUI ui) {
        ui.displayActionResult(attacker.getName() + " attacks " + getName() + " -- 0 damage (Smoke Bomb active)!");
    }
}
