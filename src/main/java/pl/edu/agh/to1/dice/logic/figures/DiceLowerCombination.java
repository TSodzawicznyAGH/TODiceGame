package pl.edu.agh.to1.dice.logic.figures;

import pl.edu.agh.to1.dice.logic.DiceSet;

public class DiceLowerCombination extends DiceCombination {
    private final int type;

    public DiceLowerCombination(int type) {
        super(Integer.toString(type));
        this.type = type;
    }

    @Override
    public int check(DiceSet diceSet) {
        int points = 0;
        for (int i = 0; i < diceSet.SIZE; ++i) {
            if (diceSet.getValue(i) == type) {
                points += type;
            }
        }
        return points;
    }
}
