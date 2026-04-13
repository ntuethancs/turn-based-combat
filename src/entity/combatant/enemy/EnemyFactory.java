package entity.combatant.enemy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import entity.interfaces.Factory;

public class EnemyFactory extends Factory {
    public static List<Enemy> createGroup(Map<Class<? extends Enemy>, Integer> group) {
        List<Enemy> enemies = new ArrayList<>();
        for (Map.Entry<Class<? extends Enemy>, Integer> entry : group.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                enemies.add(instantiate(entry.getKey()));
            }
        }
        return enemies;
    }
}
