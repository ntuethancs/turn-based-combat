package entity.combatant.player;

import entity.action.player.wizard.ArcaneBlast;
import entity.action.player.wizard.WizardBasicAttack;

public class Wizard extends Player {
    public Wizard() {
        super(200, 50, 10, 20, 10, 20);
        actions.add(new WizardBasicAttack());
        actions.add(new ArcaneBlast());
    }

    @Override
    public String getDescription() { return "Low HP and defense, but high attack."; }
}
