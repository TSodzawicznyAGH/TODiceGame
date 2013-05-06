package pl.edu.agh.to1.dice.logic;

import pl.edu.agh.to1.dice.logic.io.GameOutputController;
import pl.edu.agh.to1.dice.logic.io.IOController;

import java.util.*;

public class Game {
    private final GameState gameState = getGameState();
    private final List<Player> playerOrder = new LinkedList<Player>();
    private final Map<Player, IOController> ioControllerMap = new HashMap<Player, IOController>();
    private final Map<Player, GameOutputController> gameOutputControllerMap = new HashMap<Player, GameOutputController>();
    private boolean running = false;

    public boolean addPlayer(Player player, IOController ioController, GameOutputController gameOutputController) {
        if ((!running) && (getPlayerCount() < getMaxPlayers())) {
            gameState.addPlayer(player);
            playerOrder.add(player);
            ioControllerMap.put(player, ioController);
            gameOutputControllerMap.put(player, gameOutputController);
            return true;
        }
        return false;
    }
    public int getMaxPlayers() {
        return 3;
    }
    public int getMinPlayers() {
        return 2;
    }
    public int getPlayerCount() {
        return playerOrder.size();
    }

    private void playerMove(Player player) {
        boolean ready = false;
        IOController ioController = ioControllerMap.get(player);
        Table table = gameState.getTables().get(player);
        DiceSet diceSet = gameState.getDiceSet();
        while (!ready) {
            //TODO
        }
    }
    private Set<Player> play() {
        running = true;
        for (int round = 0; round < gameState.getTableSize(); ++round) {
            for (Player player : playerOrder) {
                playerMove(player);
            }
        }
        return null; //TODO
    }

    //returns null if cannot start game, empty set if game was terminated, set of winners otherwise
    public Set<Player> doPlay() {
        if (running || (getPlayerCount() < getMinPlayers())) {
            return null;
        }
        return play();
    }

    protected GameState getGameState() {
        return new GameState();
    }
}
