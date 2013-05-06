package pl.edu.agh.to1.dice.logic;

import pl.edu.agh.to1.dice.logic.io.IOController;
import pl.edu.agh.to1.dice.logic.io.StdGameOutputController;
import pl.edu.agh.to1.dice.logic.io.StdIOController;

public class DiceGame {
    public void play() {
        new GameStarter().play();
    }
}