package pl.edu.agh.to1.dice.logic;

import pl.edu.agh.to1.dice.logic.commands.*;
import pl.edu.agh.to1.dice.logic.io.GameOutputController;
import pl.edu.agh.to1.dice.logic.io.IOController;

import java.util.*;

public class Game implements CommandHandler{
    private final GameState gameState;
    private final int maxPlayers;
    private final int minPlayers;
    private final int maxRerolls;

    private final List<Player> playerOrder = new LinkedList<Player>();
    private final Map<Player, IOController> ioControllerMap = new HashMap<Player, IOController>();
    private final Map<Player, GameOutputController> gameOutputControllerMap = new HashMap<Player, GameOutputController>();
    private boolean running = false;

    public Game(GameState gameState, int minPlayers, int maxPlayers, int maxRerolls) {
        this.gameState = gameState;
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
        this.maxRerolls = maxRerolls;
    }

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
        return maxPlayers;
    }
    public int getMinPlayers() {
        return minPlayers;
    }
    public int getPlayerCount() {
        return playerOrder.size();
    }

    private void initIOControllers() {
        List<Command> allCommands = gameState.getTable().getCommandList();
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

    int currentRerolls = 0;
    private void playerMove(Player player) {
        IOController ioController = ioControllerMap.get(player);
        Table table = gameState.getTables().get(player);
        DiceSet diceSet = gameState.getDiceSet();

        Set<Command> availableCommands = table.getAvailableCommands();
        availableCommands.addAll(handledCommands);

        boolean ready = false;
        currentRerolls = 0;
        diceSet.unlockAll();
        diceSet.roll();

        while (!ready) {
            updateGameControllers();
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
            if (this.canHandle(command)) {
                response = this.doHandle(command);
            }

            ioController.callback(response);

            if (!running) {
                /* terminate received */
                ready = true;
            }
        }
    }

    private Set<Player> play() {
        running = true;
        initIOControllers();
        for (int round = 0; running && (round < gameState.getTableSize()); ++round) {
            for (Player player : playerOrder) {
                playerMove(player);
                if (!running) break;
            }
        }
        Set<Player> winners = new HashSet<Player>();
        if (running) {
            updateGameControllers();
            int max = 0;
            for (Player player : playerOrder) {
                int score = gameState.getTables().get(player).getScore();
                if (score == max) {
                    winners.add(player);
                }
                if (score > max) {
                    max = score;
                    winners.clear();
                    winners.add(player);
                }
            }
        }
        return winners;
    }

    //returns null if cannot start game, empty set if game was terminated, set of winners otherwise
    public Set<Player> doPlay() {
        if (running || (getPlayerCount() < getMinPlayers())) {
            return null;
        }
        return play();
    }

    public int getMaxRerolls() {
        return maxRerolls;
    }



    List<Command> handledCommands = Arrays.asList(GameCommand.TERMINATE, GameCommand.REROLL,
            new ValueGameCommand<Integer>("l", -1));

    public boolean canHandle(Command command) {
        if ((command == GameCommand.TERMINATE) || (command == GameCommand.REROLL)
            || ((command.getCommandString().equals("l") && (command instanceof ValueGameCommand)))) {
            return true;
        }
        return false;
    }

    @Override
    public CommandResponse testHandle(Command command) {
        if (canHandle(command)) {
            return CommandResponses.COMMAND_OK;
        }
        return CommandResponses.COMMAND_UNKNOWN;
    }

    @Override
    public CommandResponse doHandle(Command command) {
        DiceSet diceSet = gameState.getDiceSet();

        if (command == GameCommand.TERMINATE) {
            running = false;
        }
        else if(command == GameCommand.REROLL) {
            if (currentRerolls < getMaxRerolls()) {
                diceSet.roll();
                ++currentRerolls;
            }
            else {
                return CommandResponses.COMMAND_FAILED;
            }
        }
        else if(command.getCommandString().equals("l") && (command instanceof ValueGameCommand)) {
            int i = ((ValueGameCommand<Integer>) command).getValue();
            if (0 <= i && i < diceSet.SIZE) {
                if (diceSet.isLocked(i)) {
                    diceSet.unlock(i);
                }
                else {
                    diceSet.lock(i);
                }
            }
            else
                return CommandResponses.COMMAND_FAILED;
        }

        return testHandle(command);
    }
}
