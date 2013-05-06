package pl.edu.agh.to1.dice.logic.figures;

import pl.edu.agh.to1.dice.logic.DiceSet;
import pl.edu.agh.to1.dice.logic.commands.Command;

public class DiceSZCombination extends DiceJokerCombination {
    public DiceSZCombination() {
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
    public void joker(Command command, int jokerBonus) {
        points = jokerBonus;
        DiceSet diceSet = parseCommand(command);

        if (diceSet != null) {
            points += check(diceSet);
        }
    }
}
