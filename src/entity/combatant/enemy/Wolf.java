package entity.combatant.enemy;

public class Wolf extends Enemy {
    private static int count = 0;

    public Wolf() {
        super(40, 45, 5, 35);
    }

    @Override
    public String getName() {
        return super.getName() + "-" + (char)('A' + count++);
    }

    @Override
    public String getDescription() { return "A ferocious predator with sharp fangs."; }
}