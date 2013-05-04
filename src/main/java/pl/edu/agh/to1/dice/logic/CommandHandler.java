package pl.edu.agh.to1.dice.logic;

public interface CommandHandler {
    public CommandResponse execute(String cmd_string, Object... args);
}
