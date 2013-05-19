package pl.edu.agh.to1.dice.logic.io;

import pl.edu.agh.to1.dice.logic.Game;
import pl.edu.agh.to1.dice.logic.commands.Command;
import pl.edu.agh.to1.dice.logic.commands.CommandResponse;

import java.util.*;

public interface IOController {
    // inicjalizacja, podany zbior wszystkich uzywanych komend
    public void init(List<Command> availableCommands, Game game);

    // pobranie komendy, podany zbior dostepnych w danym momencie komend
    public Command read(Set<Command> availableCommands);
    // wywolany po przetworzeniu pobranej read() komendy
    public void callback(CommandResponse response);

    public void send(Command command);
}
