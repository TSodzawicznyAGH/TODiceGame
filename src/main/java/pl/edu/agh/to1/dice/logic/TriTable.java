package pl.edu.agh.to1.dice.logic;

import pl.edu.agh.to1.dice.logic.commands.*;
import pl.edu.agh.to1.dice.logic.figures.*;

import java.util.*;

public class TriTable extends Table {
    private List<Table> subTables = new LinkedList<Table>();

    public TriTable() {
        super(null);
        subTables.add(StdSubTable.getTable(1));
        subTables.add(StdSubTable.getTable(2));
        subTables.add(StdSubTable.getTable(3));
    }

    int getLines() {
        return subTables.get(0).getLines();
    }
    int getSize() {
        return subTables.get(0).getSize() * 3;
    }

    Set<Command> getAllCommands() {
        return subTables.get(0).getAllCommands();
    }

    Set<Command> getAvailableCommands() {
        Set<String> combinationNames = new HashSet<String>();
        for (Table table : subTables) {
            for (Command cmd : table.getAvailableCommands()) {
                combinationNames.add(cmd.getCommandString());
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
        for (Table table : subTables) {
            score += table.getScore();
        }
        return score;
    }

    public String getLine(int i) {
        return subTables.get(0).getLine(i) +' '+ subTables.get(1).getLine(i) +' '+ subTables.get(2).getLine(i);
    }

    protected DiceCombination getDiceCombination(Command command) {
        return null;
    }

    @Override
    public boolean canHandle(Command command) {
        return (subTables.get(0).canHandle(command) || subTables.get(1).canHandle(command)
                || subTables.get(2).canHandle(command));
    }

    @Override
    public CommandResponse testHandle(Command command) {
        for (int i = 0; i < 3; ++i) {
            Table table = subTables.get(i);
            if (table.canHandle(command)) {
                return table.testHandle(command);
            }
        }
        return CommandResponses.COMMAND_UNKNOWN;
    }

    @Override
    public CommandResponse doHandle(Command command) {
        for (int i = 0; i < 3; ++i) {
            Table table = subTables.get(i);
            if (table.canHandle(command)) {
                return table.doHandle(command);
            }
        }
        return CommandResponses.COMMAND_UNKNOWN;
    }
}
