package control.mode;

import control.Registry;
import control.mode.challenge.ChallengeMode;
import control.mode.story.StoryMode;
import control.mode.survival.SurvivalMode;
import control.mode.timed.TimedMode;

public class GameModeRegistry extends Registry<GameMode> {
    private static final GameModeRegistry instance = new GameModeRegistry();

    private GameModeRegistry() {
        register(StoryMode.class);
        register(SurvivalMode.class);
        register(TimedMode.class);
        register(ChallengeMode.class);
    }

    public static GameModeRegistry getInstance() {
        return instance;
    }

    public GameMode create(int index) {
        try {
            return getType(index).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create GameMode at index " + index, e);
        }
    }
}
