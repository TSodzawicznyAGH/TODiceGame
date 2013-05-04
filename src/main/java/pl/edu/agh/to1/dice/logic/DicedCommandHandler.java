package pl.edu.agh.to1.dice.logic;

public abstract class DicedCommandHandler implements CommandHandler {
    @Override
    public CommandResponse execute(String cmd_string, Object... args) {
        if (args.length == 1) {
            DiceSet diceSet;
            try {
                diceSet = (DiceSet) args[0];
            }
            catch (ClassCastException e) {
                throw new IllegalArgumentException("DiceSet expected", e);
            }
            return doExecute(cmd_string, diceSet);
        }
        return CommandResponse.CMD_UNKNOWN;
    }
    public abstract CommandResponse doExecute(String cmd_string, DiceSet diceSet);
}
