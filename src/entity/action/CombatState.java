package entity.action;

import java.util.List;
import entity.combatant.Combatant;

public interface CombatState {
    Combatant getActor();
    List<Combatant> getAllCombatants();
    List<Combatant> getLivingAllies();
    List<Combatant> getLivingOpponents();
    List<Combatant> getLivingAll();
}
