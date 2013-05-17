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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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

    Map<String, JButton> buttons = new HashMap<String, JButton>();

    private Canvas canvas;
    private int x_canvas = 420;
    private int y_canvas = 560;
    private JPanel panel;
    private ArrayList<String> nazwy = new ArrayList<String>();
    private Command myCommand = null;

    class figureClicked implements ActionListener {
        private Command command;
        public figureClicked(Command command){
            this.command = command;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            myCommand = command;
            //notify
        }
    }

    @Override
    public void init(Set<Command> availableCommands) {
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
        setLayout(new BorderLayout());
        GameState state = new GameState();

        panel = new JPanel();
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(13,1));
        JPanel rightPanel = new JPanel();
        rightPanel.add(new JTextField("PRAWA STRONA!"));
        canvas.setBounds(0, 0, x_canvas, y_canvas);
        canvas.setBackground(Color.CYAN);
        panel.setLayout(new BorderLayout());
        panel.add(canvas, BorderLayout.CENTER);
        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);
        this.add(panel);
        for(Command command: availableCommands){
            JButton button = new JButton(command.toString());
            button.addActionListener(new figureClicked(command));
            buttons.put(command.toString(), button);
            leftPanel.add(new JButton(command.toString()));
        }

        setResizable(false);
        pack();
        setVisible(true);
    }

    @Override
    public Command read(Set<Command> availableCommands) {
        for(String key: buttons.keySet()){
            boolean enabling = false;
            for(Command cmd: availableCommands){
                if(cmd.toString().equals(key)){
                    enabling = true;
                }
            }
            buttons.get(key).enable(enabling);
        }
        try{
            //wait
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        for(JButton button: buttons.values()){
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
                for(Player player: newState.getTables().keySet()){
                    g.drawString(player.getName(), 95 + 80*pls ,35);
                    pls++;
                }
                int j = 0;
                for (int i = 0; i < newState.getTableLines(); ++i) {
                    pls = 0;
                    for (Table table : newState.getTables().values()) {
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

            public void update(Graphics g){
                paint(g);
            }

        };
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

