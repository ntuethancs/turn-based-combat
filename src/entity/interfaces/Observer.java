package entity.interfaces;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import entity.action.ActionContext;
import entity.combatant.CombatEvent;

public abstract class Observer {
    protected final Map<CombatEvent, Predicate<ActionContext>> triggerMap = new HashMap<>();

    public boolean trigger(CombatEvent event, ActionContext ctx) {
        if (triggerMap.containsKey(event)) {
            return triggerMap.get(event).test(ctx);
        }
        return true;
    }

    protected void addTrigger(CombatEvent event, Predicate<ActionContext> trigger) { triggerMap.put(event, trigger); }
    protected void removeTrigger(CombatEvent event) { triggerMap.remove(event); }

}
