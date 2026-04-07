package entity.effect;

import boundary.GameUI;
import entity.combatant.Combatant;

public abstract class StatusEffect {
    protected String name;
    protected int duration;
    protected boolean begin = true;
    protected boolean stackable = false;

    public String getName() { return name; }
    public int getDuration() { return duration; }
    public boolean isExpired() { return duration <= 0; }
    public boolean isStackable() { return stackable; }
    public void decrementDuration() { duration--; }
    public boolean isBegin() { return begin; }

    public void onApply(Combatant c, GameUI ui) {};
    public void onExpire(Combatant c, GameUI ui) {}
    public String toString() {
        return "[" + name + " " + duration + "]";
    }
}