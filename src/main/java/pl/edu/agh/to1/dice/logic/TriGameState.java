package pl.edu.agh.to1.dice.logic;


public class TriGameState extends GameState {
    protected int getDiceCount() {
        return 5;
    }
    protected Table getTable() {
        return new TriTable();
    }
}
