package entity.combatant.enemy;

import control.Registry;

public class EnemyRegistry extends Registry<Enemy> {
    private static final EnemyRegistry instance = new EnemyRegistry();

    private EnemyRegistry() {
        Class<?>[] classes = {
            Goblin.class,
            Wolf.class,
            Dragon.class
        };
        for (Class<?> cls : classes) {
            @SuppressWarnings("unchecked")
            Class<? extends Enemy> enemyClass = (Class<? extends Enemy>) cls;
            register(enemyClass);
        }
    }

    public static EnemyRegistry getInstance() {
        return instance;
    }
}
