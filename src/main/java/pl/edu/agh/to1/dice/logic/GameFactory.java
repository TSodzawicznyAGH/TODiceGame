package pl.edu.agh.to1.dice.logic;

public class GameFactory {
    static Game getStdGame() {
        return new Game(new GameState(), 2, 3, 2);
    }
    static Game getTriGame() {
        return new Game(new TriGameState(), 2, 3, 2);
    }
}
