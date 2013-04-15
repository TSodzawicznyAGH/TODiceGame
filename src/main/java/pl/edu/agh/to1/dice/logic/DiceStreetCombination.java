package pl.edu.agh.to1.dice.logic;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Tomek
 * Date: 14.04.13
 * Time: 20:54
 * To change this template use File | Settings | File Templates.
 */
public class DiceStreetCombination extends DiceJokerCombination {
    private final int n;
    private final int reward;
    protected DiceStreetCombination(String cmd_string, int n, int reward) {
        super(cmd_string);
        this.n = n;
        this.reward = reward;
    }

    @Override
    public int check(DiceSet diceSet) {
        int points = 0;

        Set<Integer> vals = new HashSet<Integer>();
        for (int i = 0; i < diceSet.SIZE; ++i) {
            vals.add(diceSet.getValue(i));
        }

        for (int i = 1; i <= diceSet.getDiceMax() - n + 1; ++i) {
            points = reward;

            inner_loop:
            for (int j = i; j < i + n; ++j) {
                if (!vals.contains(j)) {
                    System.out.format("%d %d %d - %s\n", i, j, n, "false");
                    points = 0;
                    break inner_loop;
                }
            }
            if (points == reward) {
                break;
            }
        }
        return points;
    }

    @Override
    public CommandResponse joker(DiceSet diceSet, int jokerBonus) {
        points = reward + jokerBonus;
        return CommandResponse.CMD_OK;
    }
}
