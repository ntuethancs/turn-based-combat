package entity.effect.equipment;

import boundary.GameUI;
import entity.action.interfaces.SpecialAttack;
import entity.combatant.CombatEvent;
import entity.combatant.Combatant;
import entity.effect.base.PermanentEffect;

public class ManaGemEffect extends PermanentEffect {
    public ManaGemEffect() {
        super("Mana Gem");
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
        if (!target.consumeUsedSpecialThisTurn()) return true;
        SpecialAttack special = target.actions.getSpecial();
        if (special == null || special.getCooldown() <= 0) return true;
        special.setCooldown(Math.max(0, special.getCooldown() - 1));
        if (ui != null) {
            ui.displayActionResult(target.getName() + "'s Mana Gem reduces special cooldown by 1!");
        }
        return true;
    }
}
