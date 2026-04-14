package entity.combatant.helpers;

public enum StatField {
    maxHp,
    attack,
    defense,
    speed,
    critRate,
    critDamage;

    @Override
    public String toString() {
        return this.name(); // already returns "maxHp", "attack", etc.
    }
}
