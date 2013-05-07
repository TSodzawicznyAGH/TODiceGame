package pl.edu.agh.to1.dice.logic.figures;

import pl.edu.agh.to1.dice.logic.DiceSet;
import pl.edu.agh.to1.dice.logic.commands.*;

public abstract class DiceCombination implements CommandHandler {
    protected int points = -1;
    protected final String figureName;

    protected DiceSet parseCommand(Command command) {
        FigureCommand figureCommand = null;
        try {
            figureCommand = (FigureCommand) command;
        }
        catch (ClassCastException e) {
            return null;
        }
        if (figureCommand.getCommandString().equals(figureName)) {
            return  figureCommand.getDiceSet();
        }
        return null;
    }

    protected DiceCombination(String figureName) {
        this.figureName = figureName;
    }

    public boolean isSet() {
        return (points >= 0);
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public abstract int check(DiceSet diceSet);

    public String getName() {
        return figureName;
    }

    public boolean canHandle(Command command) {
        return (parseCommand(command) != null);
    }

    public CommandResponse testHandle(Command command) {
        DiceSet diceSet = parseCommand(command);
        if (diceSet == null) {
            return CommandResponses.COMMAND_UNKNOWN;
        }
        if (isSet()) {
            return new ValueCommandResponse<Integer>(-1);
        }
        return new ValueCommandResponse<Integer>(check(diceSet));
    }

    public CommandResponse doHandle(Command command) {
        CommandResponse response = testHandle(command);
        if (response instanceof ValueCommandResponse) {
            this.points = ((ValueCommandResponse<Integer>) response).getValue();
        }
        return response;
    }

    @Override
    public String toString() {
        return String.format("%-3s %3s", figureName, ((points < 0) ? "-" : Integer.toString(points)));
    }
}
