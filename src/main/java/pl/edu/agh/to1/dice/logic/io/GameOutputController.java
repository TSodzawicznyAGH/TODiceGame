package pl.edu.agh.to1.dice.logic.io;

import pl.edu.agh.to1.dice.logic.GameState;
import pl.edu.agh.to1.dice.logic.Player;
import pl.edu.agh.to1.dice.logic.commands.Command;

import java.util.Set;

public interface GameOutputController {
    //inicjalizacja, podany player, z ktorym zwiazany jest ten kontroler oraz stan gry
    public void init(Player player, GameState initialState);
    //nowy stan gry
    
    public void update(GameState newState);
    public void finish(Set<Player> winners);
    public void terminate();

    public void send(Command command);
}
