package entity.combatant.enemy;

public class Goblin extends Enemy {
    private static int count = 0;

    public Goblin() {
        super(5, 35, 15, 25, 0, 0); 
        tag = (char)('A' + count);
        count++;
    }

    @Override
    public String getName() {
        return super.getName() + "-" + tag;
    }

    @Override
    public String getDescription() { return "A small but agile creature."; }
}