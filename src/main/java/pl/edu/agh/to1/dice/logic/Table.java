package pl.edu.agh.to1.dice.logic;

import pl.edu.agh.to1.dice.logic.commands.CommandHandler;
import pl.edu.agh.to1.dice.logic.figures.Figure;

public interface Table extends CommandHandler {
    public int getSize();
    public Figure getAt(int index);
}
