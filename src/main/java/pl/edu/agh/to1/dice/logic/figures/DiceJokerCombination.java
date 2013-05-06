package pl.edu.agh.to1.dice.logic.figures;


import pl.edu.agh.to1.dice.logic.DiceSet;
import pl.edu.agh.to1.dice.logic.commands.Command;

public abstract class DiceJokerCombination extends DiceCombination {
    protected DiceJokerCombination(String cmd_string) {
        super(cmd_string);
    }

    public abstract void joker(Command command, int jokerBonus);
}
