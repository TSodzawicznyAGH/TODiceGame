package pl.edu.agh.to1.dice.logic.commands;

import pl.edu.agh.to1.dice.logic.DiceSet;

public class FigureCommand implements Command {
    private String figureName;
    private DiceSet diceSet;

    private FigureCommand(String figureShortName) {
        this.figureName = figureShortName;
    }
    public DiceSet getDiceSet() {
        return diceSet;
    }
    public void setDiceSet(DiceSet diceSet) {
        this.diceSet = diceSet;
    }
    public String getFigureName() {
        return figureName;
    }
}
