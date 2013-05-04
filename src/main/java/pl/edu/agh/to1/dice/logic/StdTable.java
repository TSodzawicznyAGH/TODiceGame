package pl.edu.agh.to1.dice.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StdTable extends Table {
    private final DiceGeneralCombination jokerChecker;

    private StdTable(List<DiceCombination> combinations, DiceGeneralCombination jokerChecker) {
        super(combinations);
        this.jokerChecker = jokerChecker;
    }

    @Override
    public CommandResponse doExecute(String cmd_string, DiceSet diceSet) {
        CommandResponse response = CommandResponse.CMD_UNKNOWN;
        int jokerBonus = 100;

        if (jokerChecker.isSet() && jokerChecker.check(diceSet) > 0) {
            for (DiceCombination c: combinations) {
                if (c.execute(cmd_string) == CommandResponse.CMD_FAILED) {
                    if (c instanceof DiceJokerCombination) {
                        response = ((DiceJokerCombination) c).joker(diceSet, jokerBonus);
                        lastPoints = c.getPoints();
                    }
                    else {
                        response = c.execute(cmd_string, diceSet);
                        if (response == CommandResponse.CMD_OK) {
                            c.setPoints(jokerBonus + c.getPoints());
                            lastPoints = c.getPoints();
                        }
                    }
                }
            }
        }
        else {
            response = super.doExecute(cmd_string, diceSet);
        }
        return response;
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
