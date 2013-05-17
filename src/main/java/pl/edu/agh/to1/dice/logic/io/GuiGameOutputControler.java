package pl.edu.agh.to1.dice.logic.io;

import java.util.Set;
import pl.edu.agh.to1.dice.logic.GameState;
import pl.edu.agh.to1.dice.logic.Player;
import pl.edu.agh.to1.dice.logic.commands.Command;

/**
 * Created with IntelliJ IDEA.
 * User: Johnny
 * Date: 17.05.13
 * Time: 08:49
 * To change this template use File | Settings | File Templates.
 */
public class GuiGameOutputControler implements GameOutputController {
    @Override
    public void init(Player player, GameState initialState) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void update(GameState newState) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void finish(Set<Player> winners) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void terminate() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void send(Command command) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
