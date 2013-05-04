package pl.edu.agh.to1.dice.logic.figures;

import pl.edu.agh.to1.dice.logic.commands.Command;

public interface Figure {
    public String getName();
    public Command getCommand();
    public int getValue();
}
