package pl.edu.agh.to1.dice.logic;


import java.io.Serializable;

public class Player implements Serializable{

    private String name;
    private int wins;
    private int losses;
    private int draws;

    public Player(String name){
        this.name = name;
        this.wins = 0;
        this.losses = 0;
        this.draws = 0;
    }

    public void addWin(){
        this.wins++;
    }

    public void addLoss(){
        this.losses++;
    }

    public void addDraw(){
        this.draws++;
    }

    public String getName(){
        return this.name;
    }

    public int getWins(){
        return this.wins;
    }

    public int getDraws(){
        return this.draws;
    }

    public int getLosses(){
        return this.losses;
    }

    public String toString(){
        int games = wins + losses + draws;
        String games_s = Integer.toString(games);
        String ret = "Name: " + name
                + "\nWins: " + Integer.toString(wins) + "/" + games_s
                + "\nDraws: " + Integer.toString(draws) + "/" + games_s
                + "\nLosses: " + Integer.toString(losses) + "/" + games_s + "\n";
        return ret;
    }

}
