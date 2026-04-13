package entity.combatant.enemy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EnemyFactory {
    private static final EnemyRegistry registry = EnemyRegistry.getInstance();

    public static <T extends Enemy> T create(Class<T> type) {
        return registry.create(type);
    }

    public static List<Enemy> createGroup(Map<Class<? extends Enemy>, Integer> group) {
        List<Enemy> enemies = new ArrayList<>();
        for (Map.Entry<Class<? extends Enemy>, Integer> entry : group.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                enemies.add(create(entry.getKey()));
            }
        }
        return enemies;
    }
}
