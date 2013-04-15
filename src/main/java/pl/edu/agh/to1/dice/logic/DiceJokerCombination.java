package pl.edu.agh.to1.dice.logic;

/**
 * Created with IntelliJ IDEA.
 * User: Tomek
 * Date: 14.04.13
 * Time: 21:59
 * To change this template use File | Settings | File Templates.
 */
public abstract class DiceJokerCombination extends DiceCombination  {
    protected DiceJokerCombination(String cmd_string) {
        super(cmd_string);
    }

    public abstract CommandResponse joker(DiceSet diceSet, int jokerBonus);
}
