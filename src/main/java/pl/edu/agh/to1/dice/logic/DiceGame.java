package pl.edu.agh.to1.dice.logic;

import com.sun.corba.se.impl.logging.ORBUtilSystemException;

public class DiceGame {
    public void play() {
        System.out.println("Playing Dice:");

        GameOutputController gameOutputController = new StdGameOutputController();
        PlayerIOController playerIOController = new StdPlayerIOController();

        Player terr = new Player("Terrorists", playerIOController);
        Player ct   = new Player("Counter terrorists", playerIOController);

        Game game = new Game(gameOutputController);

        game.registerPlayer(terr);
        game.registerPlayer(ct);
        int status = game.play();
    }
}