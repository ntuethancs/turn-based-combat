package entity.effect.base;


public abstract class PermanentEffect extends StatusEffect {
    public boolean isExpired() { return false; }
}
