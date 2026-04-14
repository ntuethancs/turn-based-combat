package boundary.output;

import boundary.output.colours.ColourPalette;
import entity.action.ActionContext;
import entity.combatant.Combatant;
import entity.combatant.helpers.StatField;

public class CombatantRenderer {
    private final OutputBuilder builder;
    private static final int BAR_LENGTH = 20;
    private static final int CARD_WIDTH = 50;
    private static final int ART_WIDTH = 12;

    public CombatantRenderer(OutputBuilder builder) {
        this.builder = builder;
    }

    private ColourPalette palette() {
        return builder.getPalette();
    }

    public String iconFor(Combatant c) {
        return "";
    }

    public String colourFor(Combatant c) {
        String type = typeOf(c);
        if ("dragon".equals(type) || "boss".equals(type)) {
            return palette().boss();
        }
        return (c.getTeam() == ActionContext.Team.PLAYER)
                ? palette().player()
                : palette().enemy();
    }

    public String hpcolour(int hp, int maxHp) {
        double ratio = hp * 1.0 / Math.max(maxHp, 1);
        if (ratio > 0.60) return palette().high();
        if (ratio > 0.30) return palette().medium();
        return palette().low();
    }

    private String barColour(int hp, int maxHp) {
        double ratio = hp * 1.0 / Math.max(maxHp, 1);
        if (ratio > 0.60) return palette().success();
        if (ratio > 0.30) return palette().warning();
        return palette().danger();
    }

    public String safeStatusText(Combatant c) {
        if (c.status == null) return "NORMAL";
        return c.status.toString().toUpperCase();
    }

    public String statusColour(String status) {
        String s = status.toLowerCase();
        if (s.contains("stun")) return palette().warning();
        if (s.contains("dead") || s.contains("defeat")) return palette().danger();
        if (s.contains("buff")) return palette().success();
        return palette().neutral();
    }

    private String typeOf(Combatant c) {
        String name = c.getName().toLowerCase();

        if (name.contains("warrior")) return "warrior";
        if (name.contains("mage") || name.contains("wizard")) return "mage";
        if (name.contains("healer")) return "healer";
        if (name.contains("archer")) return "archer";

        if (name.contains("goblin")) return "goblin";
        if (name.contains("wolf")) return "wolf";
        if (name.contains("dragon")) return "dragon";
        if (name.contains("boss")) return "boss";

        if (c.getTeam() == ActionContext.Team.PLAYER) return "player";
        return "enemy";
    }

    private String[] artFor(Combatant c) {
        switch (typeOf(c)) {
            case "warrior":
                return new String[]{
                        "    O_/      ",
                        "   /|        ",
                        "   / \\       ",
                        "  /___\\      "
                };
            case "mage":
                return new String[]{
                        "    /\\       ",
                        "   (  )      ",
                        "   /||\\  *   ",
                        "   /  \\      "
                };
            case "healer":
                return new String[]{
                        "    O        ",
                        "   /|\\  +    ",
                        "   / \\       ",
                        "             "
                };
            case "archer":
                return new String[]{
                        "    O  }-->  ",
                        "   /|\\       ",
                        "   / \\       ",
                        "             "
                };
            case "goblin":
                return new String[]{
                        "   ,,,       ",
                        "  (o o)      ",
                        "  /|_|\\      ",
                        "   / \\       "
                };
            case "wolf":
                return new String[]{
                        "  /\\__/\\     ",
                        " (  o o )    ",
                        "  \\_  _/     ",
                        "    \\/       "
                };
            case "dragon":
                return new String[]{
                        "   /\\_/\\     ",
                        "  ( o.o )    ",
                        "  /> ^ <\\    ",
                        "  \\/___\\/    "
                };
            case "boss":
                return new String[]{
                        "   .---.     ",
                        "  / o o\\     ",
                        "  |  ^  |    ",
                        "  | '_' |    "
                };
            default:
                return new String[]{
                        "    O        ",
                        "   /|\\       ",
                        "   / \\       ",
                        "             "
                };
        }
    }

    private String fit(String s, int width) {
        if (s.length() > width) {
            return s.substring(0, width - 3) + "...";
        }
        return String.format("%-" + width + "s", s);
    }

    private void appendHealthBar(int hp, int maxHp) {
        maxHp = Math.max(maxHp, 1);
        int filled = (int) Math.round((hp * 1.0 / maxHp) * BAR_LENGTH);
        filled = Math.max(0, Math.min(BAR_LENGTH, filled));

        builder.append("[");
        builder.repeat("=", filled, barColour(hp, maxHp));
        builder.repeat("-", BAR_LENGTH - filled, palette().softDivider());
        builder.append("]");
    }

    private void border() {
        builder.append("    +");
        builder.repeat("-", CARD_WIDTH - 2);
        builder.appendLine("+");
    }

    private void line(String text, String colour) {
        builder.append("    |");
        builder.append(fit(text, CARD_WIDTH - 2), colour);
        builder.appendLine("|");
    }

    public void printCombatantCard(Combatant c, int index, boolean showIndex) {
        int maxHp = c.stats().get(StatField.maxHp);
        String status = safeStatusText(c);
        String team = (c.getTeam() == ActionContext.Team.PLAYER) ? "ALLY" : "ENEMY";

        border();

        String name = (showIndex ? index + ". " : "") + c.getName() + " [" + team + "]";
        line(name, colourFor(c));
        line("STATUS: " + status, statusColour(status));
        line("", palette().neutral());

        String[] art = artFor(c);
        for (String a : art) {
            builder.append("    |");
            builder.append(fit(a, ART_WIDTH), colourFor(c));
            builder.append(fit("", CARD_WIDTH - 2 - ART_WIDTH));
            builder.appendLine("|");
        }

        line("", palette().neutral());

        builder.append("    |");
        builder.append(fit("HP " + c.getHp() + "/" + maxHp, 12), hpcolour(c.getHp(), maxHp));
        appendHealthBar(c.getHp(), maxHp);

        int used = 12 + 2 + BAR_LENGTH;
        int remain = (CARD_WIDTH - 2) - used;
        if (remain > 0) builder.append(fit("", remain));

        builder.appendLine("|");

        border();
    }
}