package pl.edu.agh.to1.dice.logic;

import pl.edu.agh.to1.dice.logic.figures.*;
import pl.edu.agh.to1.dice.logic.commands.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StdTable extends Table {
    private final DiceGeneralCombination jokerChecker;

    private StdTable(List<DiceCombination> combinations, DiceGeneralCombination jokerChecker) {
        super(combinations);
        this.jokerChecker = jokerChecker;
    }

    public CommandResponse doExecute(Command command) {
        int jokerBonus = 100;

        DiceCombination combination = getDiceCombination(command);
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
        return super.doHandle(command);
    }

    public static Table getTable() {
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
        return new StdTable(collections, diceGeneralCombination);
    }
}
