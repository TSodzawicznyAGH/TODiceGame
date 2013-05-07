package pl.edu.agh.to1.dice.logic.commands;

public class ValueGameCommand<T> implements Command {
    private T value;
    private String commandString = null;

    public ValueGameCommand(String commandString, T value) {
        this.commandString = commandString;
        this.value = value;
    }

    public T getValue() {
        return value;
    }
    public void setValue(T value) {
        this.value = value;
    }

    public String getCommandString() {
        return commandString;
    }
    @Override
    public String toString() {
        return commandString;
    }
}
