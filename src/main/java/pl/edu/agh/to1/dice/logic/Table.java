package pl.edu.agh.to1.dice.logic;

import pl.edu.agh.to1.dice.logic.commands.CommandHandler;
import pl.edu.agh.to1.dice.logic.figures.DiceCombination;

public interface Table extends CommandHandler {
    public int getSize();
    public DiceCombination getAt(int index);
}
