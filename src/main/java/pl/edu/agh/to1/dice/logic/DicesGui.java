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
import java.io.IOException;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.*;
import java.util.List;
import javax.swing.*;
import pl.edu.agh.to1.dice.logic.commands.Command;
import pl.edu.agh.to1.dice.logic.commands.CommandResponse;
import pl.edu.agh.to1.dice.logic.commands.ValueGameCommand;
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

    public DicesGui(){
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
    }

    public void showGui(){

    }

    @Override
    public void init(List<Command> availableCommands) {

        setLayout(new BorderLayout());

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
                    int j = 0;
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
                }
            }

            public void update(Graphics g){
                paint(g);
            }

        };
        canvas.setBounds(0, 0, x_canvas, y_canvas);
        canvas.setBackground(Color.CYAN);

        rightPanel = new JPanel();
        rightPanel.add(new JTextField("PRAWA STRONA!"));

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

