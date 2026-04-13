package boundary;

import java.util.List;
import control.mode.GameMode;
import entity.action.interfaces.Action;
import entity.combatant.Combatant;
import entity.combatant.player.Player;
import entity.equipment.Equipment;
import entity.item.Item;
import entity.level.Difficulty;

public interface UserInterface {
    void displayWelcome();
    GameMode selectGameMode();
    void displayModeEnd(boolean playerWon, GameMode mode);
    int selectPlayerType();
    int selectEnemyType();
    List<Item> selectItems();
    Equipment selectWeapon();
    Equipment selectArtifact();
    int askReplay();
    void displayRoundStart(int round, List<Combatant> combatants);
    Combatant selectTarget(List<Combatant> combatants);
    Item selectItem(List<Item> items);
    void displayActionResult(String msg);
    void displayBattleEnd(boolean playerWon, Player player, int rounds);
    Action selectAction(List<Action> allActions, List<Action> readyActions, Combatant owner);
    Difficulty selectDifficulty();
}
