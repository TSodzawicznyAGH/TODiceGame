package pl.edu.agh.to1.dice.logic;

import pl.edu.agh.to1.dice.logic.commands.*;
import pl.edu.agh.to1.dice.logic.figures.DiceCombination;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Table implements CommandHandler {
    protected final List<DiceCombination> combinations;

    public Table(List<DiceCombination> combinations) {
        this.combinations = combinations;
    }

    int getSize() {
        return combinations.size();
    }

    Set<Command> getAllCommands() {
        Set<String> combinationNames = new HashSet<String>();
        for (DiceCombination combination : combinations) {
            combinationNames.add(combination.getName());
        }

        Set<Command> commands = new HashSet<Command>();
        for (String combinationName : combinationNames) {
            commands.add(new FigureCommand(combinationName));
        }
        return commands;
    }

    Set<Command> getAvailableCommands() {
        Set<String> combinationNames = new HashSet<String>();
        for (DiceCombination combination : combinations) {
            if (!combination.isSet()) {
                combinationNames.add(combination.getName());
            }
        }

        Set<Command> commands = new HashSet<Command>();
        for (String combinationName : combinationNames) {
            commands.add(new FigureCommand(combinationName));
        }
        return commands;
    }

    int getScore() {
        int score = 0;
        for (DiceCombination c: combinations) {
            if (c.isSet()) {
                score += c.getPoints();
            }
        }
        return score;
    }

    public String getLine(int i) {
        return combinations.get(i).toString();
    }

    protected DiceCombination getDiceCombination(Command command) {
        DiceCombination handler = null;
        for (DiceCombination diceCombination : combinations) {
            if (diceCombination.canHandle(command)) {
                handler = diceCombination;
                break;
            }
        }
        return handler;
    }

    @Override
    public boolean canHandle(Command command) {

        return (getDiceCombination(command) != null);
    }

    @Override
    public CommandResponse testHandle(Command command) {
        CommandHandler handler = getDiceCombination(command);
        if (handler != null) {
            return handler.testHandle(command);
        }
        return CommandResponses.COMMAND_UNKNOWN;
    }

    @Override
    public CommandResponse doHandle(Command command) {
        CommandHandler handler = getDiceCombination(command);
        if (handler != null) {
            return handler.doHandle(command);
        }
        return CommandResponses.COMMAND_UNKNOWN;
    }
}
