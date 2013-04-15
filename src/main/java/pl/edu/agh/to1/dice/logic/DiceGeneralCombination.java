package pl.edu.agh.to1.dice.logic;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Tomek
 * Date: 14.04.13
 * Time: 20:42
 * To change this template use File | Settings | File Templates.
 */
public class DiceGeneralCombination extends DiceCombination {

    protected DiceGeneralCombination() {
        super("g");
    }

    @Override
    public int check(DiceSet diceSet) {
        int points = 50;

        int first = diceSet.getValue(0);
        for (int i = 1; i < diceSet.SIZE; ++i) {
            if (first != diceSet.getValue(i)) {
                points = 0;
                break;
            }
        }

        return points;
    }
}
