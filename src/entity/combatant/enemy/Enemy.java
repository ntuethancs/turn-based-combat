package entity.combatant.enemy;

import entity.action.ActionContext;
import entity.action.enemy.EnemyBasicAttack;
import entity.action.interfaces.Action;
import entity.combatant.Combatant;

public class Enemy extends Combatant {
    protected char tag = 'A';

    public Enemy(int hp, int attack, int defense, int speed, int critRate, int critDamage) {
        super(hp, attack, defense, speed, critRate, critDamage);
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
