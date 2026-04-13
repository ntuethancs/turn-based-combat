package boundary;

import java.util.List;

import control.Registry;
import control.mode.GameMode;
import entity.action.interfaces.Action;
import entity.combatant.Combatant;
import entity.combatant.player.Player;
import entity.interfaces.Describable;
import entity.interfaces.Named;
import entity.item.Item;
import entity.level.Difficulty;

public interface UserInterface {
    void displayWelcome();
    GameMode selectGameMode();
    void displayModeEnd(boolean playerWon, GameMode mode);
    <T extends Named & Describable> Class<? extends T> selectFromRegistry(Registry<T> registry, String title);
    <T extends Named & Describable> List<Class<? extends T>> selectMultipleFromRegistry(Registry<T> registry, String title, int count);
    int askReplay();
    void displayRoundStart(int round, List<Combatant> combatants);
    Combatant selectTarget(List<Combatant> combatants);
    Item selectItem(List<Item> items);
    void displayActionResult(String msg);
    void displayBattleEnd(boolean playerWon, Player player, int rounds);
    Action selectAction(List<Action> allActions, List<Action> readyActions, Combatant owner);
    Difficulty selectDifficulty();
}
