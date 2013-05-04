package pl.edu.agh.to1.dice.logic;

import java.util.Set;

public interface Game {
    public int getMaxPlayers();
    public int getMinPlayers();

    public Set<Player> doPlay();
}
