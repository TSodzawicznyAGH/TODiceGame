package pl.edu.agh.to1.dice.logic.figures;

import pl.edu.agh.to1.dice.logic.DiceSet;
import pl.edu.agh.to1.dice.logic.commands.Command;

import java.util.HashSet;
import java.util.Set;

public class DiceStreetCombination extends DiceJokerCombination {
    private final int n;
    private final int reward;
    public DiceStreetCombination(String cmd_string, int n, int reward) {
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

            for (int j = i; j < i + n; ++j) {
                if (!vals.contains(j)) {
                    System.out.format("%d %d %d - %s\n", i, j, n, "false");
                    points = 0;
                    break;
                }
            }
            if (points == reward) {
                break;
            }
        }
        return points;
    }

    @Override
    public void joker(Command command, int jokerBonus) {
        points = reward + jokerBonus;
    }
}
