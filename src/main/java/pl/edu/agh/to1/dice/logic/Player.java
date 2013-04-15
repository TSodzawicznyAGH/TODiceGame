package pl.edu.agh.to1.dice.logic;


import com.sun.org.apache.xerces.internal.impl.xpath.regex.RegularExpression;

import java.util.regex.PatternSyntaxException;

/**
 * Created with IntelliJ IDEA.
 * User: Tomek
 * Date: 14.04.13
 * Time: 04:34
 * To change this template use File | Settings | File Templates.
 */
public class Player implements CommandHandler{
    private Game game;
    private GameOutputController gameOutputController;
    private Table table;
    private DiceSet diceSet;
    private PlayerIOController playerIOController;
    private String name;

    private boolean busy = false;

    int reRolls;

    public Player(String name, PlayerIOController playerIOController) {
        this.name = name;
        this.playerIOController = playerIOController;
    }

    public boolean isBusy() {
        return busy;
    }

    public boolean register(Game game,
                            GameOutputController gameOutputController,
                            DiceSet diceSet,
                            Table table) {
        boolean ret = !busy;
        if (ret) {
            this.game = game;
            this.gameOutputController = gameOutputController;
            this.diceSet = diceSet;
            this.table = table;
            busy = true;
        }
        return ret;
    }
    public void unregister() {
        if (busy) {
            busy = false;
            if (game.isRunning()) {
                game.stop();
            }
            game = null;
            gameOutputController = null;
            diceSet = null;
            table = null;
        }
    }

    public int getScore() {
        return table.getScore();
    }

    Table getTable() {
        return table;
    }

    private boolean reroll() {
        boolean ret = (reRolls < diceSet.MAX_REROLLS);
        if (ret) {
            ++reRolls;
            diceSet.roll();
            gameOutputController.writeDice(diceSet);
        }
        return ret;
    }

    private void changeLock(int i) {
        if (diceSet.isLocked(i)) {
            diceSet.unlock(i);
        } else {
            diceSet.lock(i);
        }
        gameOutputController.writeDice(diceSet);
    }

    boolean terminate = false;
    public void play() {
        int points = -1;

        terminate = false;
        reRolls = 0;
        diceSet.unlockAll();
        diceSet.roll();

        gameOutputController.writeMessageTurnStart(this);
        gameOutputController.writeDice(diceSet);

        CommandResponse response = CommandResponse.CMD_UNKNOWN;
        while (!terminate && (points == -1)) {
            String cmd_string = playerIOController.getCommand();

            response = this.execute(cmd_string);
            if (response == CommandResponse.CMD_UNKNOWN) {
                response = table.execute(cmd_string, diceSet);
                if (response == CommandResponse.CMD_OK) {
                    points = table.getLastPoints();
                }
            }

            playerIOController.sendResponse(response);
        }
        if (!terminate) {
            gameOutputController.writeMessageTurnPoints(points);
        }
    }

    @Override
    public CommandResponse execute(String cmd_string) {
        CommandResponse response = CommandResponse.CMD_OK;
        if (cmd_string.equals("x")) {
            terminate = true;
            game.stop();
        }
        else if (cmd_string.equals("h")) {
            gameOutputController.writeHelp();
        }
        else if (cmd_string.startsWith("l")) {
            boolean success = true;
            int i = -1;
            try {
                i = Integer.parseInt(cmd_string.split("\\s", 2)[1]);
            } catch (PatternSyntaxException e) { // regex error
                success = false;
            } catch (NumberFormatException e) { // wrong command argument - not a number
                success = false;
            } catch (ArrayIndexOutOfBoundsException e) { // no command argument
                success = false;
            }
            if (success && (i > 0) && (i <= diceSet.SIZE)) {
                changeLock(i-1);
            } else {
                response = CommandResponse.CMD_FAILED;
            }
        }
        else if (cmd_string.equals("r")) {
            if (!reroll()) {
                response = CommandResponse.CMD_FAILED;
            }
        }
        else {
            response = CommandResponse.CMD_UNKNOWN;
        }
        return response;
    }

    @Override
    public String toString() {
        return new String(name);
    }
}
