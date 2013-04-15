package pl.edu.agh.to1.dice.logic;

import java.util.HashSet;
import java.util.Set;

public class Game {
    private final int minPlayers;
    private final int maxPlayers;

    private GameOutputController gameOutputController;
    private Player[] players;
    private int playersCount = 0;
    private DiceSet diceSet;
    boolean running = false;


    public Game(GameOutputController gameOutputController) {
        this.gameOutputController = gameOutputController;
        minPlayers = getMinPlayers();
        maxPlayers = getMaxPlayers();
        players = new Player[maxPlayers];
        diceSet = new DiceSet(getDiceSetSize(), getMaxRerolls());
    }

    public boolean isRunning() {
        return running;
    }

    public boolean registerPlayer(Player player) {
        boolean ret = false;
        if (playersCount < maxPlayers) {
            ret = player.register(this, gameOutputController, diceSet, getTable());
            if (ret) {
                players[playersCount] = player;
                ++playersCount;
            }
        }
        return ret;
    }
    public boolean unregisterPlayer(Player player) {
        boolean ret = true;
        for (int i = 0; i < playersCount; ++i) {
            if (players[i] == player) {
                ret = unregisterPlayer(i);
            }
        }
        return ret;
    }
    private boolean unregisterPlayer(int i) {
        if (!running) {
            players[i].unregister();
            players[i] = null;

            for (int iter = i + 1; iter < playersCount; ++iter) {
                players[iter - 1] = players[iter];
            }
            players[playersCount - 1] = null;

            --playersCount;
        }
        return (!running);
    }
    private boolean unregisterAllPlayers() {
        boolean ret = true;
        for (int i = playersCount - 1; i >= 0; --i) {
            ret &= unregisterPlayer(i);
        }
        return ret;
    }

    private void printTables() {
        Table[] tables = new Table[playersCount];
        for (int i = 0; i < playersCount; ++i) {
            tables[i] = players[i].getTable();
        }
        gameOutputController.writeTables(tables);
    }

    private  void printEnd() {
        gameOutputController.writeMessageEnd();

        for (int i = 0; i < playersCount; ++i) {
            gameOutputController.writePlayerPoints(players[i]);
        }

        Set<Player> winners = new HashSet<Player>();
        winners.add(players[0]);
        int winnerScore = players[0].getScore();

        for (int i = 1; i < playersCount; ++i) {
            int newScore = players[i].getScore();

            if (newScore > winnerScore) {
                winners.clear();
                winners.add(players[i]);
                winnerScore = newScore;
            }
            else if (newScore == winnerScore) {
                winners.add(players[i]);
            }
        }

        gameOutputController.writeWinners(winners);
        printTables();
    }

    private void doPlay() {
        int rounds = getTable().getSize();

        gameOutputController.writeMessageBegin();

        for (int r = 0; (r < rounds) && running; ++r) {
            for (int p = 0; (p < playersCount) && running; ++p) {
                printTables();
                players[p].play();
            }
        }
        if (running) {
            printEnd();
        }
    }

    public int play() {
        int ret = 0;
        if (!running) {
            if (playersCount >= minPlayers) {
                running = true;
                doPlay();
                if (running) {
                    ret = 1;
                }
                else {
                    ret = -1;
                    running = true;
                }
            }
        }
        return ret;
    }
    public boolean stop() {
        boolean ret = running;
        if (running) {
            running = false;

            gameOutputController.writeMessageStop();

            unregisterAllPlayers();
        }
        return ret;
    }

    protected Table getTable() {
        return StdTable.getTable();
    }
    public int getMinPlayers() {
        return 2;
    }
    public int getMaxPlayers() {
        return 2;
    }
    protected int getDiceSetSize() {
        return 5;
    }
    protected int getMaxRerolls() {
        return 2;
    }
}
