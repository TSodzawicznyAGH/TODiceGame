package pl.edu.agh.to1.dice.logic;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Tomek
 * Date: 14.04.13
 * Time: 08:29
 * To change this template use File | Settings | File Templates.
 */
public class StdGameOutputController implements GameOutputController {
    private static String beginMessage = new String("Game commencing.");
    private static String endMessage = new String("End of game.");
    private static String stopMessage = new String("Game terminated.");
    private static String helpMessage = String.format(
            "Help:\n"
            + "  %-8s - %s\n"
            + "  %-8s - %s\n"
            + "  %-8s - %s\n"
            + "  %-8s   %s\n"
            + "  %-8s - %s\n"
            + "  %-8s - %s",
            "l <n>", "lock/unlock n-th die",
            "r", "roll again. Number of rolls is limited.",
            "<cmd>", "choose option <cmd>. All options are listed in result table.",
            "", "You can only use each once.",
            "h", "display this help.",
            "x", "terminate the game."
    );


    @Override
    public void writeDice(DiceSet diceSet) {
        System.out.println("Dice:");
        for (int i = 0; i < diceSet.SIZE; ++i) {
            if (!diceSet.isLocked(i)) {
                System.out.format("%d ", diceSet.getValue(i));
            }
            else {
                System.out.format("l(%d) ", diceSet.getValue(i));
            }
        }
        System.out.format("\n");
    }

    @Override
    public void writeTables(Table[] tables) {
        for (int i = 0; i < tables[0].getSize(); ++i) {
            for (Table t: tables) {
                System.out.format("| %s ", t.getLine(i));
            }
            System.out.print("|\n");
        }
    }

    @Override
    public void writePlayerPoints(Player player) {
        System.out.format("%s: %d points.\n", player, player.getScore());
    }

    @Override
    public void writeWinners(Set<Player> winners) {
        for (Player p: winners) {
            System.out.format("%s win!\n", p.toString());
        }
    }

    @Override
    public void writeMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void writeMessageBegin() {
        System.out.println(beginMessage);
    }

    @Override
    public void writeHelp() {
        System.out.println(helpMessage);
    }

    @Override
    public void writeMessageEnd() {
        System.out.println(endMessage);
    }

    @Override
    public void writeMessageStop() {
        System.out.println(stopMessage);
    }

    @Override
    public void writeMessageTurnStart(Player player) {
        System.out.format("%s - your turn.\n", player.toString());
    }

    @Override
    public void writeMessageTurnPoints(int points) {
        System.out.format("You got %d points. End of turn.\n", points);
    }
}
