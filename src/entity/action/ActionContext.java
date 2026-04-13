
package entity.action;

import java.util.List;
import java.util.stream.Collectors;

import boundary.UserInterface;
import entity.action.interfaces.Action;
import entity.combatant.Combatant;
import entity.item.Item;

/**
 * Carries all runtime data an Action needs during execution.
 * Target resolution is handled here — Actions read from context, never prompt UI directly.
 */
public class ActionContext implements CombatState, InteractionProvider, ExecutionState {

    public final Combatant actor;
    public final List<Combatant> allCombatants;
    public final UserInterface ui;
    public Item selectedItem;
    public List<Combatant> targets;
    public Combatant curTarget;
    public Action action;
    public int damage = 0;

    public ActionContext(Combatant actor, List<Combatant> allCombatants, UserInterface ui) {
        this.actor          = actor;
        this.allCombatants  = allCombatants;
        this.ui             = ui;
    }

    // ── CombatState implementations ──────────────────────────

    @Override public Combatant getActor() { return actor; }
    @Override public List<Combatant> getAllCombatants() { return allCombatants; }

    /**
     * Returns all living combatants on the same team as the actor.
     * Player → all living Players
     * Enemy  → all living Enemies
     */
    @Override
    public List<Combatant> getLivingAllies() {
        return allCombatants.stream()
                .filter(c -> c.isAlive() && isSameTeam(actor, c))
                .collect(Collectors.toList());
    }

    /**
     * Returns all living combatants on the opposing team.
     * Player → all living Enemies
     * Enemy  → all living Players
     */
    @Override
    public List<Combatant> getLivingOpponents() {
        return allCombatants.stream()
                .filter(c -> c.isAlive() && isOpposingTeam(actor, c))
                .collect(Collectors.toList());
    }

    /**
     * Returns all living combatants regardless of team.
     */
    @Override
    public List<Combatant> getLivingAll() {
        return allCombatants.stream()
                .filter(Combatant::isAlive)
                .collect(Collectors.toList());
    }

    // ── InteractionProvider implementation ───────────────────

    @Override public UserInterface getUI() { return ui; }

    // ── ExecutionState implementations ────────────────────────

    @Override public Item getSelectedItem() { return selectedItem; }
    @Override public void setSelectedItem(Item item) { this.selectedItem = item; }
    @Override public List<Combatant> getTargets() { return targets; }
    @Override public void setTargets(List<Combatant> targets) { this.targets = targets; }
    @Override public Combatant getCurTarget() { return curTarget; }
    @Override public void setCurTarget(Combatant target) { this.curTarget = target; }
    @Override public int getDamage() { return damage; }
    @Override public void addDamage(int dmg) { this.damage += dmg; }

    // ── Team checks ───────────────────────────────────────────

    /**
     * Returns true if both combatants are on the same team.
     */
    public static boolean isSameTeam(Combatant a, Combatant b) {
        return teamOf(a) == teamOf(b);
    }

    /**
     * Returns true if the two combatants are on opposing teams.
     */
    public static boolean isOpposingTeam(Combatant a, Combatant b) {
        return teamOf(a) != teamOf(b);
    }

    /**
     * Resolves the team of a combatant.
     */
    public static Team teamOf(Combatant c) {
        return c.getTeam();
    }

    // ── Team enum ─────────────────────────────────────────────

    public enum Team {
        PLAYER,
        ENEMY
    }
}