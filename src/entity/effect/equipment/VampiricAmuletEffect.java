package entity.effect.equipment;

import boundary.GameUI;
import entity.combatant.CombatEvent;
import entity.combatant.Combatant;
import entity.combatant.helpers.StatField;
import entity.effect.base.PermanentEffect;

public class VampiricAmuletEffect extends PermanentEffect {
    public VampiricAmuletEffect() {
        super("Vampiric Amulet");
        addTrigger(CombatEvent.TURN_END, this::onTurnEnd);
    }

    @Override
    public void apply(Combatant target, GameUI ui) {
        // passive permanent effect; no immediate stat change
    }

    @Override
    public void remove(Combatant target, GameUI ui) {
        // never expires by default
    }

    private boolean onTurnEnd(Combatant target, GameUI ui) {
        int dealt = target.consumeDamageDealtThisTurn();
        int healAmount = dealt / 10;
        if (healAmount <= 0) return true;

        int before = target.getHp();
        int maxHp = target.stats().get(StatField.maxHp);
        target.setHp(Math.min(maxHp, before + healAmount));
        int healed = target.getHp() - before;
        if (healed > 0 && ui != null) {
            ui.displayActionResult(target.getName() + " absorbs life and heals " + healed + " HP!");
        }
        return true;
    }
}
