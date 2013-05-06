package pl.edu.agh.to1.dice.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import pl.edu.agh.to1.dice.logic.io.IOController;
import pl.edu.agh.to1.dice.logic.io.StdGameOutputController;
import pl.edu.agh.to1.dice.logic.io.StdIOController;

/**
 * Created with IntelliJ IDEA.
 * User: Johnny
 * Date: 07.05.13
 * Time: 00:03
 * To change this template use File | Settings | File Templates.
 */
public class GameStarter {

    int players = 0;
    StdGameOutputController gameOutputController = new StdGameOutputController();
    IOController ioController = new StdIOController();
    Set<Player> game_players = new HashSet<Player>();

    public void play(){
               System.out.println("Dzień dobry!");
               System.out.print("Chcesz zagrac w zwykle[z] czy potrojne[t] kosci?");
                String kosci = new String("z");
                try {
                    BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
                    kosci = bReader.readLine();
                } catch (IOException e) {return;}
                if(kosci.equals("t")){
                    Game game = new Game();//potrojne kosci
                }
                else{
                    Game game = new Game();//zwykle kosci
                }

               do{
                   System.out.print("Podaj liczbę graczy: ");
                   String s = new String("0");
                   try {
                       BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
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
                        BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
                        name = bReader.readLine();
                    } catch (IOException e) {return;}
                    Player player = new Player(name);
                   game_players.add(player);
                    System.out.print("Czy chcesz aby ten gracz był botem?[y/n]");
                    String ifbot = new String("y");
                    try {
                        BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
                        ifbot = bReader.readLine();
                    } catch (IOException e) {return;}
                    if(ifbot.equals("y")){
                        Bot bot = new Bot();
                        game.addPlayer(player, bot, bot);
                    }
                    else{
                        game.addPlayer(player, ioController, gameOutputController);
                    }
               }

               Set<Player> winners = game.doPlay();
               if(winners == null){
                   return;
               }
               else if(winners.isEmpty()){
                   return;
               }
               else if(winners.size() > 1){
                   System.out.println("tu");
                    PlayerMap map = new PlayerMap();
                    for(Player p : game_players){
                        if(winners.contains(p)){
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
                   System.out.println("tam");
                   PlayerMap map = new PlayerMap();
                   for(Player p : game_players){
                       if(winners.contains(p)){
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
}