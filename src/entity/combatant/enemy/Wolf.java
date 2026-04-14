package entity.combatant.enemy;

public class Wolf extends Enemy {
    private static int count = 0;

    public Wolf() {
        super(40, 45, 5, 35, 0, 0);
        tag = (char)('A' + count);
        count++;
    }

    @Override
    public String getName() {
        return super.getName() + "-" + tag;
    }

    @Override
    public String getDescription() { return "A ferocious predator with sharp fangs."; }
}