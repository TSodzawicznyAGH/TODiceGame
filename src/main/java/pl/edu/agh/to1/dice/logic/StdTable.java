package pl.edu.agh.to1.dice.logic;

import pl.edu.agh.to1.dice.logic.figures.*;
import pl.edu.agh.to1.dice.logic.commands.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StdTable extends Table {
    private final DiceGeneralCombination jokerChecker;
    private int bonus = 0;
    private int totalLow = 0;
    private int totalHigh = 0;
    private int multiplier = 1;

    private StdTable(List<DiceCombination> combinations, DiceGeneralCombination jokerChecker) {
        super(combinations);
        this.jokerChecker = jokerChecker;
    }

    private StdTable(List<DiceCombination> combinations, DiceGeneralCombination jokerChecker, int multiplier) {
        super(combinations);
        this.jokerChecker = jokerChecker;
        this.multiplier = multiplier;
    }

    private DiceCombination handle(Command command) {
        int jokerBonus = 100;

        DiceCombination combination = getDiceCombination(command);
        if (combination != null) {
            DiceSet diceSet = ((FigureCommand) command).getDiceSet();

            if (!combination.isSet()) {
                if (jokerChecker.isSet()
                        && (jokerChecker.check(diceSet) > 0)) {
                    // if joker
                    if ((combination != null) && (combination instanceof DiceJokerCombination)) {
                        ((DiceJokerCombination) combination).joker(command, jokerBonus);
                    }
                    else {
                        combination.doHandle(command);
                        combination.setPoints(combination.getPoints() + jokerBonus);
                    }
                }
                else {
                    combination.doHandle(command);
                }

                combination.setPoints(combination.getPoints() * multiplier);
            }
        }
        return combination;
    }

    public CommandResponse testHandle(Command command) {
        DiceCombination combination = handle(command);

        if (combination != null) {
            int points = combination.getPoints();
            combination.setPoints(-1);

            return new ValueCommandResponse<Integer>(points);
        }
        // else
        return CommandResponses.COMMAND_UNKNOWN;
    }

    public CommandResponse doHandle(Command command) {
        DiceCombination combination = handle(command);

        if (combination != null) {
            int points = combination.getPoints();
            if (combination instanceof DiceLowerCombination) {
                totalLow += points;
                if (totalLow >= 63 && bonus == 0) {
                    bonus += 35;
                }
            }
            else {
                totalHigh += points;
            }

            return new ValueCommandResponse<Integer>(points);
        }
        // else
        return CommandResponses.COMMAND_UNKNOWN;
    }

    public int getTotalHigh() {
        return totalHigh;
    }

    public int getTotalLow() {
        return totalLow;
    }

    public int getBonus() {
        return bonus;
    }

    public int getTotal() {
        return totalHigh + totalLow + bonus;
    }

    @Override
    public int getScore() {
        return getTotal();
    }

    public static Table getTable() {
        return getTable(1);
    }

    public static Table getTable(int multiplier) {
        List<DiceCombination> list = new ArrayList<DiceCombination>();

        for (int i = 1; i <=6; ++i) {
            list.add(new DiceLowerCombination(i));
        }
        list.add(new DiceNkaCombination(3));
        list.add(new DiceNkaCombination(4));
        list.add(new DiceFulCombination());
        list.add(new DiceStreetCombination("ms", 4, 30));
        list.add(new DiceStreetCombination("ds", 5, 40));
        DiceGeneralCombination diceGeneralCombination = new DiceGeneralCombination();
        list.add(diceGeneralCombination);
        list.add(new DiceSZCombination());

        List<DiceCombination> collections = Collections.unmodifiableList(list);
        return new StdTable(collections, diceGeneralCombination, multiplier);
    }
}
