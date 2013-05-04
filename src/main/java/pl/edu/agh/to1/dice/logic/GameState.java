package pl.edu.agh.to1.dice.logic;

import java.util.Dictionary;

public interface GameState {
    public Dictionary<Player,Table> getTables();
    public DiceSet getDiceSet();
}
