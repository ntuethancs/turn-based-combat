package entity.combatant.enemy;

import entity.action.ActionContext;
import entity.action.enemy.EnemyBasicAttack;
import entity.action.interfaces.Action;
import entity.combatant.Combatant;

public class Enemy extends Combatant {

    public Enemy(int hp, int attack, int defense, int speed) {
        super(hp, attack, defense, speed);
        actions.add(new EnemyBasicAttack());
    }

    @Override
    public ActionContext.Team getTeam() { return ActionContext.Team.ENEMY; }

    @Override
    public String getDescription() { return "A standard enemy."; }

    public Action chooseAction(ActionContext ctx) {
        return actions.ready(ctx).get(0);
    }
}
