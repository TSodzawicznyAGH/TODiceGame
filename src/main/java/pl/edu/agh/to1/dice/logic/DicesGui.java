package pl.edu.agh.to1.dice.logic;

/**
 * Created with IntelliJ IDEA.
 * User: Johnny
 * Date: 17.05.13
 * Time: 08:17
 * To change this template use File | Settings | File Templates.
 */
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.text.AttributedString;
import java.util.*;
import java.util.List;
import javax.swing.*;

import pl.edu.agh.to1.dice.logic.commands.*;
import pl.edu.agh.to1.dice.logic.figures.DiceCombination;
import pl.edu.agh.to1.dice.logic.io.GameOutputController;
import pl.edu.agh.to1.dice.logic.io.IOController;

/**
 * Created with IntelliJ IDEA.
 * User: Johnny
 * Date: 16.05.13
 * Time: 22:28
 * To change this template use File | Settings | File Templates.
 */
public class DicesGui extends JFrame implements IOController, GameOutputController{

    Map<String, JButton> buttons = new LinkedHashMap<String, JButton>();

    private Canvas canvas = null;
    private int x_canvas = 420;
    private int y_canvas = 560;
    private JPanel panel;
    private ArrayList<String> nazwy = new ArrayList<String>();
    private Command myCommand = null;
    JPanel leftPanel = new JPanel();
    JPanel rightPanel = new JPanel();
    private GameState nowState = null;
    private String name;
    JButton buttonDices[];
    int nrOfDices = 5;
    int currentRerolls = 0;
    Game game;
    JButton throwDices;
    Font ttfBase = null;
    Font ttfReal = null;

    private void updateDices(){
        final String[] glyphs = {"\u2680", "\u2681", "\u2682", "\u2683", "\u2684", "\u2685"};
        for(int i=0; i<nrOfDices; i++){
            if (ttfReal != null) {
                buttonDices[i].setFont(ttfReal);
                buttonDices[i].setText( glyphs[nowState.getDiceSet().getValue(i)-1] );
            }
            else {
                buttonDices[i].setText( Integer.toString(nowState.getDiceSet().getValue(i))  );
            }

            if( nowState.getDiceSet().isLocked(i)  ){
                buttonDices[i].setBackground(Color.LIGHT_GRAY);
            }
            else{
                buttonDices[i].setBackground(Color.WHITE);
            }
        }
    }



    class figureClicked implements ActionListener {
        private Command command;
        public figureClicked(Command command){
            this.command = command;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            myCommand = command;
            //this.notify();
        }
    }
    class diceClicked implements ActionListener {
        private int number;

        public diceClicked(int number){
            this.number = number;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            myCommand = new ValueGameCommand<Integer>("l",number) ;
     /*       if(e.getSource() instanceof JButton){
                JButton  button;
                button = (JButton)e.getSource();
                if(button.getBackground() == Color.WHITE){
                    //dice.lock
                    if(nowState != null){
                        nowState.getDiceSet().lock(number);
                    }
                }
                else{
                    //dice.unlock
                    if(nowState != null){
                        nowState.getDiceSet().unlock(number);
                    }

                }
            }  */
        }
    }

    class throwClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        /*     if(currentRerolls >= 2)
                return;
            currentRerolls++;
            if(nowState != null){
                nowState.getDiceSet().roll();
                updateDices();
            }
        */
            throwDices.setEnabled(false);
            myCommand = GameCommand.REROLL;

        }
    }

    public void resetPanel(boolean toRead, Set<Command> availableCommands, final GameState newState){
        if(toRead){
            for(String button : buttons.keySet()){
                for(Command cmd : availableCommands){
                    if(button.equals(cmd.toString())){
                        buttons.get(button).setEnabled(true);
                    }
                }
            }
        }
        else{
            canvas.repaint();
        }

    }

    public DicesGui(String name){
        nazwy.add("jedynki");
        nazwy.add("dwójki");
        nazwy.add("trójki");
        nazwy.add("czwórki");
        nazwy.add("piątki");
        nazwy.add("szóstki");
        nazwy.add("PREMIA");
        nazwy.add("SUMA");
        nazwy.add("trójka");
        nazwy.add("czwórka");
        nazwy.add("ful");
        nazwy.add("mały strit");
        nazwy.add("duży strit");
        nazwy.add("generał");
        nazwy.add("szansa");
        nazwy.add("SUMA");
        nazwy.add("WYNIK");
        this.name = name;
        try {
            InputStream myStream = new BufferedInputStream(new FileInputStream("CODE2000.TTF"));
            ttfBase = Font.createFont(Font.TRUETYPE_FONT, myStream);
            ttfReal = ttfBase.deriveFont(Font.PLAIN, 72);
        } catch (Exception ex) {
            System.err.println("CODE2000 not loaded.");
        }
    }

    public void showGui(){

    }

    @Override
    public void init(List<Command> availableCommands, Game game) {

        this.game = game;

        setLayout(new BorderLayout());
        this.setTitle(name);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(13,1));
        buttons = new LinkedHashMap<String, JButton>();
        for(Command command: availableCommands){
            JButton button = new JButton(command.toString());
            button.setEnabled(false);
            button.addActionListener(new figureClicked(command));
            buttons.put(command.toString(), button);
            leftPanel.add(button);
        }

        canvas = new Canvas(){
            public void paint(Graphics g){
                Font font = new Font(Font.SERIF, 0, 18);
                g.setFont(font);
                for(int x=10; x <= x_canvas-10; x+=80){
                    g.drawLine(x, 10, x, y_canvas - 10);
                }
                for(int y=10; y <= y_canvas-10; y+=30){
                    g.drawLine(10, y, x_canvas-10, y);
                }
                for(int i=0; i < 17; i++){
                    g.drawString(nazwy.get(i), 15, 65+i*30);
                }
                int pls = 0;
                if(nowState != null){
                    for(Player player: nowState.getTables().keySet()){
                        g.drawString(player.getName(), 95 + 80*pls ,35);
                        pls++;
                    }
                    /*int j = 0;
                    for (int i = 0; i < nowState.getTableLines(); ++i) {
                        pls = 0;
                        for (Table table : nowState.getTables().values()) {
                            g.drawString(table.getLine(i), 95 + 80*pls, 65 + j*30);//trzeba zmienic toString() w Table
                            pls++;
                        }
                        if(j == 5){
                            //wpisz PREMIE I SUME
                            j+=2;
                        }
                        j++;
                    }
                    //wpisz SUME i WYNIK
                    */
                    pls = 0;
                    for (Table table : nowState.getTables().values()) {
                        StdTable stdTable = (StdTable) table;
                        int j = 0;
                        for (int i = 0; i < nowState.getTableLines(); ++i) {
                            g.drawString(table.getLine(i), 95 + 80*pls, 65 + j*30);//trzeba zmienic toString() w Table

                            if(j == 5){
                                //wpisz PREMIE I SUME
                                j++;
                                g.drawString(Integer.toString(stdTable.getBonus()), 95 + 80*pls, 65 + j*30);
                                j++;
                                g.drawString(Integer.toString(stdTable.getTotalLow()), 95 + 80*pls, 65 + j*30);
                            }
                            j++;
                        }
                        //wpisz SUME i WYNIK
                        g.drawString(Integer.toString(stdTable.getTotalHigh()), 95 + 80*pls, 65 + j*30);
                        j++;
                        g.drawString(Integer.toString(stdTable.getTotal()), 95 + 80*pls, 65 + j*30);
                        j++;

                        ++pls;
                    }
                }
            }

            public void update(Graphics g){
                g.clearRect (0, 0, getWidth(), getHeight());
                paint(g);
            }

        };
        canvas.setBounds(0, 0, x_canvas, y_canvas);
        canvas.setBackground(Color.CYAN);

        rightPanel = new JPanel();
        //rightPanel.add(new JTextField("PRAWA STRONA!"));

        rightPanel.setLayout(new GridLayout(nrOfDices + 1,1));
        buttonDices = new JButton[nrOfDices];
        for(int i=0; i<nrOfDices; i++){
            buttonDices[i] = new JButton("");
            buttonDices[i].addActionListener(new diceClicked(i));
            if(nowState != null){
                   buttonDices[i].setText("T");
            }
            rightPanel.add(buttonDices[i]);
        }

        throwDices = new JButton("Rzut kośćmi");
        throwDices.addActionListener(new throwClicked());
        rightPanel.add(throwDices);
        throwDices.setEnabled(false);

        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(canvas, BorderLayout.CENTER);
        panel.add(rightPanel, BorderLayout.EAST);
        this.add(panel);

        setResizable(false);
        pack();
        setVisible(true);

        //read(availableCommands);
    }

    @Override
    public Command read(Set<Command> availableCommands) {
        if(game.getLeftRerolls() > 0){
            throwDices.setEnabled(true);
        }
        myCommand = null;
        resetPanel(true, availableCommands, null);
        /*try{
            this.wait();
        }catch (InterruptedException e){
            e.printStackTrace();
        }*/
        while(myCommand == null){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        }
        for(JButton button : buttons.values()){
            button.setEnabled(false);
        }
        throwDices.setEnabled(false);
        return myCommand;
    }


    @Override
    public void callback(CommandResponse response) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void send(Command command) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void init(Player player, GameState initialState) {
        update(initialState);
    }

    @Override
    public void update(final GameState newState) {
        nowState = newState;
        resetPanel(false, null, nowState);
        currentRerolls=0;
        updateDices();
    }

    @Override
    public void finish(Set<Player> winners) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void terminate() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}

