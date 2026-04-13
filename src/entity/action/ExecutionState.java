package entity.action;

import java.util.List;
import entity.combatant.Combatant;
import entity.item.Item;

public interface ExecutionState {
    Item getSelectedItem();
    void setSelectedItem(Item item);
    List<Combatant> getTargets();
    void setTargets(List<Combatant> targets);
    Combatant getCurTarget();
    void setCurTarget(Combatant target);
    int getDamage();
    void addDamage(int dmg);
}
