package pl.edu.agh.to1.dice.logic.commands;

public enum ValueGameCommand implements Command {
    EXAMPLE("ex");

    private String commandString = null;
    private ValueGameCommand(String commandString) {
        this.commandString = commandString;
    }
    @Override
    public String toString() {
        return commandString;
    }
}
