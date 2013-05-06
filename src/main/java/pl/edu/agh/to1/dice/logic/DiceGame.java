package pl.edu.agh.to1.dice.logic;

import pl.edu.agh.to1.dice.logic.io.IOController;
import pl.edu.agh.to1.dice.logic.io.StdGameOutputController;
import pl.edu.agh.to1.dice.logic.io.StdIOController;

public class DiceGame {
    public void play() {
        System.out.println("Playing Dice");
        Player player1 = new Player("p1");
        Player player2 = new Player("p2");
        StdGameOutputController gameOutputController = new StdGameOutputController();
        IOController ioController = new StdIOController();
        Bot bot  = new Bot();
        Game game = new Game();
        game.addPlayer(player1, ioController, gameOutputController);
        game.addPlayer(player2, bot, bot);
        game.doPlay();
    }
}