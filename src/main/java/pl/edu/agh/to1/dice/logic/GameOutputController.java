package pl.edu.agh.to1.dice.logic;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Tomek
 * Date: 14.04.13
 * Time: 07:20
 * To change this template use File | Settings | File Templates.
 */
public interface GameOutputController {
    public void writeDice(DiceSet diceSet);
    public void writeTables(Table[] tables);
    public void writePlayerPoints(Player player);
    public void writeWinners(Set<Player> winners);
    public void writeMessage(String message);
    public void writeMessageBegin();
    public void writeMessageEnd();
    public void writeMessageStop();
    public void writeMessageTurnStart(Player player);
    public void writeMessageTurnPoints(int points);
    public void writeHelp();
}
