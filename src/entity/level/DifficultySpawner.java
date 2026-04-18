package entity.level;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import entity.combatant.enemy.Dragon;
import entity.combatant.enemy.Enemy;
import entity.combatant.enemy.EnemySpawner;
import entity.combatant.enemy.Goblin;
import entity.combatant.enemy.Wolf;

public class DifficultySpawner implements Spawner {
    private final Difficulty difficulty;

    public DifficultySpawner(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public Iterator<List<Enemy>> getWaves() {
        return new Iterator<List<Enemy>>() {
            private int currentWave = 0;
            private final int maxWaves = getMaxWaves();

            private int getMaxWaves() {
                switch (difficulty) {
                    case MEDIUM: return 2;
                    case HARD:   return 2;
                    default:     return 1;
                }
            }

            @Override
            public boolean hasNext() {
                return currentWave < maxWaves;
            }

            @Override
            public List<Enemy> next() {
                if (!hasNext()) throw new NoSuchElementException();
                
                List<Enemy> waveEnemies = new ArrayList<>();
                if (currentWave == 0) {
                    waveEnemies.addAll(EnemySpawner.createGroup(getInitialGroup()));
                } else if (currentWave == 1) {
                    waveEnemies.addAll(EnemySpawner.createGroup(getBackupGroup()));
                }
                
                currentWave++;
                return waveEnemies;
            }

            private Map<Class<? extends Enemy>, Integer> getInitialGroup() {
                switch (difficulty) {
                    case EASY:   return Map.of(Goblin.class, 3);
                    case MEDIUM: return Map.of(Goblin.class, 1, Wolf.class, 1);
                    case HARD:   return Map.of(Goblin.class, 2);
                    case BOSS:   return Map.of(Dragon.class, 2);
                    default:     return Map.of();
                }
            }

            private Map<Class<? extends Enemy>, Integer> getBackupGroup() {
                switch (difficulty) {
                    case MEDIUM: return Map.of(Wolf.class, 2);
                    case HARD:   return Map.of(Goblin.class, 1, Wolf.class, 2);
                    default:     return Map.of();
                }
            }
        };
    }
}
