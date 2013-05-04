package pl.edu.agh.to1.dice.logic.commands;

public enum FigureCommand implements Command {
    EXAMPLE("ex");

    private String commandString = null;
    private FigureCommand(String commandString) {
        this.commandString = commandString;
    }
    @Override
    public String toString() {
        return commandString;
    }
}
