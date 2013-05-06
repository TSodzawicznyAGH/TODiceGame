package pl.edu.agh.to1.dice.logic;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: Johnny
 * Date: 05.05.13
 * Time: 20:32
 * To change this template use File | Settings | File Templates.
 */
public class PlayerMap{

    //POLA
    private File folder;

    //KONSTRUKTOR
    public PlayerMap(){
        folder = new File("Players");
        if(!folder.exists()){//jezeli folder nie istnieje
            folder.mkdir();//to tworzymy folder na nasze pliki
        }
    }

    //CHECK
    private boolean check(String key){
        String adres = new String(folder + "/" + key);
        try{
            FileInputStream fos = new FileInputStream(adres);
        }catch(FileNotFoundException e){return false;} //nie znaleziono pliku
        return true; //plik istnieje
    }

    //GET
    public Player getPlayer(String key){
        Object result = null;
        if( check(key) == false ){}
        else{
            String adres = new String(folder + "/" + key);
            try{
                FileInputStream fis = new FileInputStream(adres); //pobieramy plik o danym  kluczu
                ObjectInputStream ois = new ObjectInputStream(fis);
                result = ois.readObject(); //odczytujemy obiekt
                ois.close();
                fis.close();
            }catch(IOException e){System.out.println("IOEX 1");}
            catch(ClassNotFoundException f){System.out.println("CNFE 1");}
        }
        return (Player)result; //i rzutujemy go na typ Player
    }

    //PUT
    public void setPlayer(Player player){
        System.out.println("tak");
        String name = player.getName();
        String nazwa_pliku = folder + "/" + name;
        File file = new File(nazwa_pliku);
        if( check(name) == true ){
            file.delete();//usuwamy stary plik
        }
        try{
            FileOutputStream fos = new FileOutputStream(nazwa_pliku); //zapisujemy plik do naszego folderu
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(player);
            oos.close();
            fos.close();
        }catch(IOException e){System.out.println("IOEX 2");}
    }

}
