package pl.edu.agh.to1.dice.logic.io;

import pl.edu.agh.to1.dice.logic.DiceSet;
import pl.edu.agh.to1.dice.logic.GameState;
import pl.edu.agh.to1.dice.logic.Player;
import pl.edu.agh.to1.dice.logic.Table;
import pl.edu.agh.to1.dice.logic.commands.Command;
import pl.edu.agh.to1.dice.logic.figures.DiceCombination;

import java.util.Set;

public class StdGameOutputController implements GameOutputController {
    @Override
    public void init(Player player, GameState initialState) {
        System.out.format("Hello %s!\n", player.getName());
        update(initialState);
    }

    @Override
    public void update(GameState newState) {
        for (Player player : newState.getTables().keySet()) {
            System.out.print(player.getName());
            System.out.print("\t");
        }
        System.out.println();
        for (int i = 0; i < newState.getTableSize(); ++i) {
            for (Table table : newState.getTables().values()) {
                System.out.print(table.getLine(i));
                System.out.print("\t");
            }
            System.out.println();
        }

        DiceSet diceSet = newState.getDiceSet();
        for (int i = 0; i < diceSet.SIZE; ++i) {
            if (!diceSet.isLocked(i)) {
                System.out.format("%d ", diceSet.getValue(i));
            }
            else {
                System.out.format("l(%d) ", diceSet.getValue(i));
            }
        }
        System.out.println();
    }

    @Override
    public void finish(Set<Player> winners) {

    }

    @Override
    public void terminate() {
    }

    @Override
    public void send(Command command) {
    }
}
