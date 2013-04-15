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
public class DiceFulCombination extends DiceJokerCombination {

    protected DiceFulCombination() {
        super("ful");
    }

    @Override
    public int check(DiceSet diceSet) {
        int points = 0;

        Map<Integer, Integer> vals = new HashMap<Integer, Integer>();
        for (int i = 0; i < diceSet.SIZE; ++i) {
            int key = diceSet.getValue(i);

            if (!vals.containsKey(key)) {
                vals.put(key, 1);
            }
            else {
                vals.put(key, vals.get(key) + 1);
            }
        }

        if (vals.keySet().size() == 2) {
            points = 25;
            for (Integer key: vals.keySet()) {
                if (vals.get(key) < 2) {
                    points = 0;
                }
            }
        }
        return points;
    }

    @Override
    public CommandResponse joker(DiceSet diceSet, int jokerBonus) {
        points = 25 + jokerBonus;
        return CommandResponse.CMD_OK;
    }
}
