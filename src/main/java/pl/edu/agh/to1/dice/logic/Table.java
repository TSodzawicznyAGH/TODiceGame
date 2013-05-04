package pl.edu.agh.to1.dice.logic;

import java.util.List;

public class Table extends DicedCommandHandler {
    protected final List<DiceCombination> combinations;
    protected int lastPoints = -1;

    public Table(List<DiceCombination> combinations) {
        this.combinations = combinations;
    }

    int getSize() {
        return combinations.size();
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

    public CommandResponse execute(String cmd_string) {

        CommandResponse response = CommandResponse.CMD_UNKNOWN;
        for (DiceCombination c: combinations) {
            response = c.execute(cmd_string);
            if (response != CommandResponse.CMD_UNKNOWN) {
                break;
            }
        }
        return response;
    }

    @Override
    public CommandResponse doExecute(String cmd_string, DiceSet diceSet) {
        CommandResponse response = CommandResponse.CMD_UNKNOWN;
        for (DiceCombination c: combinations) {
            response = c.execute(cmd_string, diceSet);
            if (response != CommandResponse.CMD_UNKNOWN) {
                lastPoints = c.getPoints();
                break;
            }
        }
        return response;
    }

    public int getLastPoints() {
        return lastPoints;
    }
}
