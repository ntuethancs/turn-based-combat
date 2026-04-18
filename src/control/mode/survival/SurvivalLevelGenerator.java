package control.mode.survival;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import control.mode.LevelGenerator;
import entity.combatant.enemy.Dragon;
import entity.combatant.enemy.Enemy;
import entity.combatant.enemy.EnemySpawner;
import entity.combatant.enemy.Goblin;
import entity.combatant.enemy.Wolf;
import entity.level.Level;
import entity.level.Spawner;

public class SurvivalLevelGenerator implements LevelGenerator {
    @Override
    public Iterator<Level> iterator() {
        return new Iterator<Level>() {
            private boolean delivered = false;

            @Override
            public boolean hasNext() {
                return !delivered;
            }

            @Override
            public Level next() {
                if (!hasNext()) throw new NoSuchElementException();
                delivered = true;
                
                return new Level(new Spawner() {
                    @Override
                    public Iterator<List<Enemy>> getWaves() {
                        return new Iterator<List<Enemy>>() {
                            private final Map<Class<? extends Enemy>, Integer> cumulativeCounts = new HashMap<>();
                            private int waveNumber = 1;

                            @Override
                            public boolean hasNext() {
                                return true; // Endless waves
                            }

                            @Override
                            public List<Enemy> next() {
                                Class<? extends Enemy> type;
                                if (waveNumber % 3 == 0) {
                                    type = Wolf.class;
                                } else if (waveNumber % 5 == 0) {
                                    type = Dragon.class;
                                } else {
                                    type = Goblin.class;
                                }
                                cumulativeCounts.put(type, cumulativeCounts.getOrDefault(type, 0) + 1);
                                waveNumber++;
                                
                                return EnemySpawner.createGroup(cumulativeCounts);
                            }
                        };
                    }
                });
            }
        };
    }
}
