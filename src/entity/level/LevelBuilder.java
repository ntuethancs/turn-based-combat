package entity.level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import entity.combatant.enemy.Enemy;
import entity.combatant.enemy.EnemySpawner;

public class LevelBuilder implements Spawner {
    private final List<Map<Class<? extends Enemy>, Integer>> waves = new ArrayList<>();
    private Map<Class<? extends Enemy>, Integer> currentWave = new HashMap<>();

    public LevelBuilder addEnemyToCurrentWave(Class<? extends Enemy> type) {
        currentWave.put(type, currentWave.getOrDefault(type, 0) + 1);
        return this;
    }

    public LevelBuilder nextWave() {
        if (!currentWave.isEmpty()) {
            waves.add(new HashMap<>(currentWave));
            currentWave.clear();
        }
        return this;
    }

    @Override
    public Iterator<List<Enemy>> getWaves() {
        if (!currentWave.isEmpty()) {
            waves.add(new HashMap<>(currentWave));
            currentWave.clear();
        }
        
        return new Iterator<List<Enemy>>() {
            private final Iterator<Map<Class<? extends Enemy>, Integer>> waveIterator = waves.iterator();

            @Override
            public boolean hasNext() {
                return waveIterator.hasNext();
            }

            @Override
            public List<Enemy> next() {
                Map<Class<? extends Enemy>, Integer> group = waveIterator.next();
                return EnemySpawner.createGroup(group);
            }
        };
    }

    public Level build() {
        return new Level(this);
    }
}
