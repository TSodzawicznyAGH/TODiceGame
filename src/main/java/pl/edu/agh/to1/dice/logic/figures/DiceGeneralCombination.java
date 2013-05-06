package pl.edu.agh.to1.dice.logic.figures;

import pl.edu.agh.to1.dice.logic.DiceSet;


public class DiceGeneralCombination extends DiceCombination {

    public DiceGeneralCombination() {
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
