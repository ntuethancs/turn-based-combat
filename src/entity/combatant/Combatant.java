package entity.combatant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import boundary.GameUI;
import entity.effect.StatusEffect;

public abstract class Combatant {
    protected String name;
    protected int hp;
    protected int maxHp;
    protected int attack;
    protected int defense;
    protected int speed;
    protected boolean alive = true;
    protected final List<StatusEffect> statusEffects = new ArrayList<>();

    public void takeDamage(int dmg) {
        hp = Math.max(0, hp - dmg);
        if (hp == 0) alive = false;
    }
    
    public int getEffectiveDefense() {
        int bonus = statusEffects.stream()
            .filter(e -> e instanceof entity.effect.DefendEffect)
            .mapToInt(e -> ((entity.effect.DefendEffect) e).getBonus())
            .sum();
        return defense + bonus;
    }

    public void addStatusEffect(StatusEffect effect) { 
        if (effect.isStackable()) {
            statusEffects.add(effect);
        } else {
            int existingDuration = statusEffects.stream()
                    .filter(e -> e.getClass() == effect.getClass())
                    .mapToInt(StatusEffect::getDuration)
                    .findFirst()
                    .orElse(0);
            if (existingDuration < effect.getDuration()) {
                statusEffects.removeIf(e -> e.getClass() == effect.getClass());
                statusEffects.add(effect);
            }
        }
    }

    public List<StatusEffect> getStatusEffects() { return statusEffects; }
    

    public void tickStatusEffects(GameUI ui, boolean begin) {
        Iterator<StatusEffect> it = statusEffects.iterator();
        while (it.hasNext()) {
            StatusEffect e = it.next();
            if (e.isBegin() != begin) { continue; }
            e.decrementDuration();
            if (e.isExpired()) {
                e.onExpire(this, ui);
                it.remove();
            }
        }
    }


    public String getStatusSummary() {
        if (statusEffects.isEmpty()) return "";
            return statusEffects.stream()
                .map(StatusEffect::toString)
                .collect(java.util.stream.Collectors.joining(" "));
    }

    public void setHp(int hp) { this.hp = hp; }

    // Getters
    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getAttack() { return attack; }
    public int getSpeed() { return speed; }
    public boolean isAlive() { return alive; }
}