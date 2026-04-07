package entity.combatant.interfaces;

public interface Healable {
    int getHp();
    int getMaxHp();
    void setHp(int hp);
    String getName();

    /**
     * Heals the combatant by the given amount, capped at maxHp.
     * Default implementation delegates to the concrete heal logic.
     */
    default void heal(int amount) {
        int new_hp = Math.min(getMaxHp(), getHp() + amount);
        setHp(new_hp);
    }
}