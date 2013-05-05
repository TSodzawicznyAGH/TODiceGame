package pl.edu.agh.to1.dice.logic.figures;

import pl.edu.agh.to1.dice.logic.DiceSet;

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
    public void joker(DiceSet diceSet, int jokerBonus) {
        points = check(diceSet) + jokerBonus;
    }
}
