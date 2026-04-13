package entity.effect.base;

import boundary.GameUI;
import entity.combatant.Combatant;
import entity.interfaces.Named;
import entity.interfaces.Observer;

public abstract class StatusEffect extends Observer implements Named {
    public String getName() { 
        String[] parts = Named.super.getName().split(" ");
        if (parts.length > 1) {
            return String.join(" ", java.util.Arrays.copyOfRange(parts, 0, parts.length - 1));
        }
        return parts[0];
    }

    public abstract void apply(Combatant target, GameUI ui);
    public abstract void remove(Combatant target, GameUI ui);
    public abstract boolean isExpired();

    public String toString() {
        return "[" + getName() + "]";
    }
}
