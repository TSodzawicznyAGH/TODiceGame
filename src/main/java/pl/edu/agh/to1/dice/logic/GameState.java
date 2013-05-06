package pl.edu.agh.to1.dice.logic;

import java.util.*;

public class GameState {
    private Map<Player,Table> tables = new HashMap<Player,Table>();
    private DiceSet diceSet = new DiceSet(getDiceCount());

    public void addPlayer(Player player) {
        tables.put(player, getTable());
    }
    public Map<Player,Table> getTables() {
        return tables;
    }
    public DiceSet getDiceSet() {
        return diceSet;
    }
    public int getTableSize() {
        return getTable().getSize();
    }

    protected int getDiceCount() {
        return 5;
    }
    protected Table getTable() {
        return StdTable.getTable();
    }
}
