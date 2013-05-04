package pl.edu.agh.to1.dice.logic.commands;

public interface CommandHandler {
    public boolean canHandle(Command command);
    public CommandResponse testHandle(Command command);
    public CommandResponse doHandle(Command command);
}
