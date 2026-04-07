package entity.combatant.interfaces;

import java.util.List;

import boundary.GameUI;
import entity.effect.StatusEffect;
import entity.effect.StunEffect;

public interface Stunnable {

    /**
     * Applies a stun of the given duration in turns.
     * Implementations should take the max of current and new duration.
     */
    List<StatusEffect> getStatusEffects();
    void addStatusEffect(StatusEffect e);
    String getName();

    /**
     * Returns true if a StunEffect is currently present in the status effect list.
     */
    default boolean isStunned() {
        return getStatusEffects().stream()
                .anyMatch(e -> e instanceof StunEffect);
    }

    default void applyStun(int duration) {
        addStatusEffect(new StunEffect(duration));
    }

    default void showStun(GameUI ui) {
        ui.displayActionResult(getName() + " is STUNNED -- turn skipped!");
    }


}