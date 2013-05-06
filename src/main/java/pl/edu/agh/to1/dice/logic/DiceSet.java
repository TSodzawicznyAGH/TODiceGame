package pl.edu.agh.to1.dice.logic;

public class DiceSet {
    public final int SIZE;
    private Die[] dice;

    public DiceSet(int size) {
        SIZE = size;
        dice = new Die[size];
        for (int i = 0; i < size; ++i) {
            dice[i] = getDie();
        }
    }

    public int getValue(int i) {
        return dice[i].getValue();
    }

    public void lock(int i) {
        dice[i].lock();
    }

    public void unlock(int i) {
        dice[i].unlock();
    }

    public boolean isLocked(int i) {
        return dice[i].isLocked();
    }
    public void unlockAll() {
        for (Die die : this.dice) {
            die.unlock();
        }
    }

    public void roll() {
        for (Die die : this.dice) {
            die.roll();
        }
    }

    public int getDiceMax() {
        return dice[0].getDieMax();
    }

    protected Die getDie() {
        return new Die();
    }
}
