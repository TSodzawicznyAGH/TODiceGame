package pl.edu.agh.to1.dice.logic;

import pl.edu.agh.to1.dice.logic.commands.*;
import pl.edu.agh.to1.dice.logic.figures.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StdSubTable extends Table {
    private final DiceGeneralCombination jokerChecker;
    private int multiplier;

    private StdSubTable(List<DiceCombination> combinations, DiceGeneralCombination jokerChecker, int multiplier) {
        super(combinations);
        this.jokerChecker = jokerChecker;
        this.multiplier = multiplier;
    }

    public CommandResponse doExecute(Command command) {
        int jokerBonus = 100;

        DiceCombination combination = getDiceCombination(command);
        if (combination == null) {
            return CommandResponses.COMMAND_UNKNOWN;
        }
        if (jokerChecker.isSet()
                && (((ValueCommandResponse<Integer>) (jokerChecker.testHandle(command))).getValue() > 0)) {
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

        return new ValueCommandResponse<Integer>(combination.getPoints());
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
        return new StdSubTable(collections, diceGeneralCombination, multiplier);
    }
}
