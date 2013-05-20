package pl.edu.agh.to1.dice.logic;

import pl.edu.agh.to1.dice.logic.commands.*;
import pl.edu.agh.to1.dice.logic.figures.*;

import java.util.*;

public class TriTable extends Table {
    private List<Table> subTables = new LinkedList<Table>();

    public TriTable() {
        super(new LinkedList<DiceCombination>());
        subTables.add(StdTable.getTable(1));
        subTables.add(StdTable.getTable(2));
        subTables.add(StdTable.getTable(3));
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
        Set<Command> commands = new HashSet<Command>();
        for (int i = 0; i < 3; ++i) {
            for (Command cmd : subTables.get(i).getAvailableCommands()) {
                String combinationName;
                combinationName = Integer.toString(i) + cmd.getCommandString();

                commands.add(new FigureCommand(combinationName));
            }
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

    protected FigureCommand getSubCommand(FigureCommand figureCommand) {
        for (int i = 0; i < 3; ++i) {
            if (figureCommand.getCommandString().startsWith(Integer.toString(i))) {
                FigureCommand figureSubCommand = new FigureCommand(figureCommand.getCommandString().substring(1));
                figureSubCommand.setDiceSet(figureCommand.getDiceSet());
                return figureSubCommand;
            }
        }
        return null;
    }

    private int getHandlerTable(FigureCommand figureCommand) {
        for (int i = 0; i < 3; ++i) {
            if (figureCommand.getCommandString().startsWith(Integer.toString(i))) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean canHandle(Command command) {
        try {
            FigureCommand figureCommand = (FigureCommand) command;

            int i = getHandlerTable(figureCommand);
            FigureCommand figureSubCommand = getSubCommand(figureCommand);
            if ( i >= 0 && figureSubCommand != null) {
                return subTables.get(i).canHandle(figureSubCommand);
            }
        }
        catch (ClassCastException e) {
             // bad command
        }
        return false;
    }

    @Override
    public CommandResponse testHandle(Command command) {
        try {
            FigureCommand figureCommand = (FigureCommand) command;

            int i = getHandlerTable(figureCommand);
            FigureCommand figureSubCommand = getSubCommand(figureCommand);
            if ( i >= 0 && figureSubCommand != null) {
                return subTables.get(i).testHandle(figureSubCommand);
            }
        }
        catch (ClassCastException e) {
             // bad command
        }
        return CommandResponses.COMMAND_UNKNOWN;
    }

    @Override
    public CommandResponse doHandle(Command command) {
        try {
            FigureCommand figureCommand = (FigureCommand) command;

            int i = getHandlerTable(figureCommand);
            FigureCommand figureSubCommand = getSubCommand(figureCommand);
            if ( i >= 0 && figureSubCommand != null) {
                return subTables.get(i).doHandle(figureSubCommand);
            }
        }
        catch (ClassCastException e) {
             // bad command
        }
        return CommandResponses.COMMAND_UNKNOWN;
    }
}
