package pl.edu.agh.to1.dice.logic;

import pl.edu.agh.to1.dice.logic.commands.Command;
import pl.edu.agh.to1.dice.logic.commands.CommandResponse;
import pl.edu.agh.to1.dice.logic.commands.CommandResponses;
import pl.edu.agh.to1.dice.logic.commands.FigureCommand;
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

    private void initIOControllers() {
        Set<Command> allCommands = gameState.getTable().getAllCommands();
        for (IOController ioController : ioControllerMap.values()) {
            ioController.init(allCommands);
        }
        for (Player player: gameOutputControllerMap.keySet()) {
            gameOutputControllerMap.get(player).init(player, gameState);
        }
    }

    private void updateGameControllers() {
        for (Player player: gameOutputControllerMap.keySet()) {
            gameOutputControllerMap.get(player).update(gameState);
        }
    }

    private void playerMove(Player player) {
        boolean ready = false;
        IOController ioController = ioControllerMap.get(player);
        Table table = gameState.getTables().get(player);
        DiceSet diceSet = gameState.getDiceSet();
        Set<Command> availableCommands = table.getAvailableCommands();
        int rerolls = 0;
        while (!ready) {
            // TODO - rerolls, game handler
            Command command = ioController.read(availableCommands);
            try {
                ((FigureCommand) command).setDiceSet(gameState.getDiceSet());
            }
            catch (ClassCastException e) { /* nothing happened */ }

            CommandResponse response = CommandResponses.COMMAND_UNKNOWN;
            if (table.canHandle(command)) {
                response = table.doHandle(command);
                ready = true;
            }
            ioController.callback(response);
        }
    }

    private Set<Player> play() {
        running = true;
        initIOControllers();
        for (int round = 0; running && (round < gameState.getTableSize()); ++round) {
            for (Player player : playerOrder) {
                playerMove(player);
                updateGameControllers();
            }
        }
        Set<Player> winners = new HashSet<Player>();
        winners.add(playerOrder.get(0));
        return winners;
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
    public int getMaxRerolls() {
        return 2;
    }
}
