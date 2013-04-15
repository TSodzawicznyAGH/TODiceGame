package pl.edu.agh.to1.dice.logic;

/**
 * Created with IntelliJ IDEA.
 * User: Tomek
 * Date: 14.04.13
 * Time: 21:15
 * To change this template use File | Settings | File Templates.
 */
public class DiceSZCombination extends DiceJokerCombination {
    protected DiceSZCombination() {
        super("sz");
    }

    @Override
    public int check(DiceSet diceSet) {
        int points = 0;
        for (int i = 0 ; i < diceSet.SIZE; ++i) {
            points += diceSet.getValue(i);
        }
        return points;
    }

    @Override
    public CommandResponse joker(DiceSet diceSet, int jokerBonus) {
        points = check(diceSet) + jokerBonus;
        return CommandResponse.CMD_OK;
    }
}
