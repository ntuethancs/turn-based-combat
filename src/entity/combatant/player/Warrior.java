package entity.combatant.player;

import entity.action.player.warrior.ShieldBash;
import entity.action.player.warrior.WarriorBasicAttack;

public class Warrior extends Player {
    public Warrior() {
        super(260, 40, 20, 30, 10, 20);
        actions.add(new WarriorBasicAttack());
        actions.add(new ShieldBash());
    }

    @Override
    public String getDescription() { return "High HP and defense, but low speed."; }
}
