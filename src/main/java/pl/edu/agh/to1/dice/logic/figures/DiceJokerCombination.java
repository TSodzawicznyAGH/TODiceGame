package pl.edu.agh.to1.dice.logic.figures;


import pl.edu.agh.to1.dice.logic.DiceSet;

public abstract class DiceJokerCombination extends DiceCombination {
    protected DiceJokerCombination(String cmd_string) {
        super(cmd_string);
    }

    public abstract void joker(DiceSet diceSet, int jokerBonus);
}
