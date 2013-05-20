package pl.edu.agh.to1.dice.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import javax.swing.*;

import pl.edu.agh.to1.dice.logic.io.GameOutputController;
import pl.edu.agh.to1.dice.logic.io.IOController;
import pl.edu.agh.to1.dice.logic.io.StdGameOutputController;
import pl.edu.agh.to1.dice.logic.io.StdIOController;

public class GameStarter {

    int players = 0;
    StdGameOutputController gameOutputController = new StdGameOutputController();
    IOController ioController = new StdIOController();
    Set<Player> game_players = new HashSet<Player>();
    Game game;
    final DicesGui[] contr = new DicesGui[4];

    public void play(){
        PlayerMap map = new PlayerMap();
        BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
        boolean useGUI = true;

        System.out.println("Dzień dobry!");
        System.out.print("Chcesz zagrac w [z]wykle czy po[t]rojne kosci?");
        String kosci = new String("z");
        try {
            kosci = bReader.readLine();
        } catch (IOException e) {return;}
        if(kosci.equals("t")){
            //throw new UnsupportedOperationException();
            game = GameFactory.getTriGame(); //cos nie dziala
            useGUI = false;
        }
        else{
            game = GameFactory.getStdGame();
        }

        do{
            System.out.print("Podaj liczbę graczy: ");
            String s = new String("0");
            try {
                s = bReader.readLine();
            } catch (IOException e) {return;}
            players = Integer.parseInt(s);
            if(players > game.getMaxPlayers() || players < game.getMinPlayers()){
                System.out.println("Liczba graczy musi byc z przedzialu od " + game.getMinPlayers() + " do " + game.getMaxPlayers());
            }
        }while(players > game.getMaxPlayers() || players < game.getMinPlayers());

        for(int i = 1; i <= players; i++){
            System.out.print("Podaj nazwę gracza: ");
            String name = new String("Gracz" + i);
            try {
                name = bReader.readLine();
            } catch (IOException e) {return;}

            Player player = new Player(name);
            game_players.add(player);

            System.out.print("Czy chcesz aby ten gracz był botem?[y/n]");
            String ifbot = new String("y");
            try {
                ifbot = bReader.readLine();
            } catch (IOException e) {return;}
            if(ifbot.equals("y")){
                Bot bot = new Bot();
                game.addPlayer(player, bot, bot);
            }
            else{
                if (useGUI) {
                    DicesGui diceGui = new DicesGui(player.getName());
                    game.addPlayer(player, diceGui, diceGui);
                    SwingUtilities.invokeLater(new RunGui(diceGui));
                }
                else {
                    game.addPlayer(player, ioController, gameOutputController);
                }

            }
        }

        for (Player player : game_players) {
            Player tmp = map.getPlayer(player.getName());
            if(tmp != null){
                player = tmp;
            }
            System.out.format("Gracz %s: %d wygranych, %d remisow, %d przegranych\n",
                    player.getName(), player.getWins(), player.getDraws(), player.getLosses());
            map.setPlayer(player);
        }

        System.out.println();
        System.out.println("Wcisnij <Enter>, zeby rozpaczac gre");
        try {
            bReader.readLine();
        } catch (IOException e) {return;}

        System.out.println();

        Set<Player> winners = game.doPlay();

        System.out.println();

        if(winners == null){
            System.out.println("Wystapil blad - nie mozna uruchomic gry");
            return;
        }
        else if(winners.isEmpty()){
            System.out.println("Gra przerwana.");
            return;
        }
        else if(winners.size() > 1){
            System.out.println("Zremisowali:");

            for(Player p : game_players){
                if(winners.contains(p)){
                    System.out.println(p.getName());

                    Player tmp = map.getPlayer(p.getName());
                    if(tmp == null){
                        tmp = p;
                    }
                    tmp.addDraw();
                    map.setPlayer(tmp);
                }
                else{
                    Player tmp = map.getPlayer(p.getName());
                    if(tmp == null){
                        tmp = p;
                    }
                    tmp.addLoss();
                    map.setPlayer(tmp);
                }
            }
        }
        else{
            System.out.println("Wygral:");
            for(Player p : game_players){
                if(winners.contains(p)){
                    System.out.println(p.getName());

                    Player tmp = map.getPlayer(p.getName());
                    if(tmp == null){
                        tmp = p;
                    }
                    tmp.addWin();
                    map.setPlayer(tmp);
                }
                else{
                    Player tmp = map.getPlayer(p.getName());
                    if(tmp == null){
                        tmp = p;
                    }
                    tmp.addLoss();
                    map.setPlayer(tmp);
                }
            }

        }
    }

    private static class RunGui extends Thread{

        DicesGui gui;

        public RunGui(DicesGui gui){
            this.gui = gui;
        }

        public void run(){
            gui.showGui();
        }
    }
}