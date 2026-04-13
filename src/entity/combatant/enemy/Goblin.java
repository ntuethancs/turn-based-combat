package entity.combatant.enemy;

public class Goblin extends Enemy {
    private static int count = 0;

    public Goblin() {
        super(5, 35, 15, 25); 
    }

    @Override
    public String getName() {
        return super.getName() + "-" + (char)('A' + count++);
    }

    @Override
    public String getDescription() { return "A small but agile creature."; }
}