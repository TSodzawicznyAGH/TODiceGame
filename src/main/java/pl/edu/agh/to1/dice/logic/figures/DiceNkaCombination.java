package pl.edu.agh.to1.dice.logic.figures;

import pl.edu.agh.to1.dice.logic.DiceSet;
import pl.edu.agh.to1.dice.logic.commands.Command;

import java.util.HashMap;
import java.util.Map;

public class DiceNkaCombination extends DiceJokerCombination {
    private final int n;
    public DiceNkaCombination(int n) {
        super(Integer.toString(n) + "ka");
        this.n = n;
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

        for (Integer key: vals.keySet()) {
            if (vals.get(key) >= n) {
                for (int i = 0; i < diceSet.SIZE; ++i) {
                    points += diceSet.getValue(i);
                }
                break;
            }
        }
        return points;
    }

    @Override
    public void joker(Command command, int jokerBonus) {
        DiceSet diceSet = parseCommand(command);
        points = 0;
        if (diceSet != null) {
            for (int i = 0; i < diceSet.SIZE; ++i) {
                points += diceSet.getValue(i);
            }
        }
        points += jokerBonus;
    }
}
