package pl.edu.agh.to1.dice.logic;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Tomek
 * Date: 14.04.13
 * Time: 05:11
 * To change this template use File | Settings | File Templates.
 */
public class Die {
    private static Random random = new Random();

    private boolean locked = false;
    private int value;
    private final int DICE_MAX;

    public Die() {
        DICE_MAX = getDieMax();
    }

    public int getValue() {
        return value;
    }

    public void lock() {
        locked = true;
    }

    public boolean isLocked() {
        return locked;
    }

    public void unlock() {
        locked = false;
    }

    public void roll() {
        if (!locked) {
            value =  random.nextInt(DICE_MAX) + 1;
        }
    }

    protected int getDieMax() {
        return 6;
    }
}
