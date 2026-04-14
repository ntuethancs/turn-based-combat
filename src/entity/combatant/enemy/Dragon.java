package entity.combatant.enemy;

import java.util.List;

import entity.action.ActionContext;
import entity.action.enemy.EnemyBasicAttack;
import entity.action.enemy.FireBreath;
import entity.action.enemy.TailWhip;
import entity.action.interfaces.Action;
import entity.action.interfaces.SpecialAttack;

public class Dragon extends Enemy {
    private static int count = 0;

    public Dragon() {
        super(150, 50, 20, 15, 0, 0);
        actions.add(new EnemyBasicAttack());
        actions.add(new FireBreath());
        actions.add(new TailWhip());
        tag = (char)('A' + count);
        count++;
    }

    @Override
    public String getName() {
        return super.getName() + "-" + tag;
    }

    @Override
    public String getDescription() { return "A powerful fire-breathing dragon."; }

    @Override
    public Action chooseAction(ActionContext ctx) {
        List<Action> ready = actions.ready(ctx);
        for (Action a : ready) {
            if (a instanceof SpecialAttack) {
                return a;
            }
        }
        return ready.get(0);
    }
}