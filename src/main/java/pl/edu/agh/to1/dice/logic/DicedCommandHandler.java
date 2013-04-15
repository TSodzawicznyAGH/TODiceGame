package pl.edu.agh.to1.dice.logic;

/**
 * Created with IntelliJ IDEA.
 * User: Tomek
 * Date: 14.04.13
 * Time: 12:55
 * To change this template use File | Settings | File Templates.
 */
public interface DicedCommandHandler extends CommandHandler {
    public CommandResponse execute(String cmd_string, DiceSet diceSet);
}
