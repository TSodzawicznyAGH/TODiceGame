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
import java.util.ArrayList;
import java.util.Set;
import javax.swing.*;
import pl.edu.agh.to1.dice.logic.commands.Command;
import pl.edu.agh.to1.dice.logic.commands.CommandResponse;
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

    private Canvas canvas;
    private int x_canvas = 420;
    private int y_canvas = 560;
    private JPanel panel;
    private ArrayList<String> nazwy = new ArrayList<String>();

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
        setLayout(new BorderLayout());
        canvas = new Canvas(){
            public void paint(Graphics g){
                Font font = new Font(Font.SERIF, 0, 18);
                g.setFont(font);
                for(int x=10; x <= x_canvas-10; x+=80){
                    g.drawLine(x, 10, x, y_canvas - 10);
                }
                for(int y=10, i = 0; y <= y_canvas-10; y+=30, i++){
                    g.drawLine(10, y, x_canvas-10, y);
                    if(i < 17)
                        g.drawString(nazwy.get(i), 15, 65+i*30);
                }


            }

            public void update(Graphics g){
                paint(g);
            }

        };
        panel = new JPanel();
        JPanel leftPanel = new JPanel();
        JButton jedynkiButton = new JButton("Jedynki");
        JButton dwojkiButton = new JButton("Dwójki");
        JButton trojkiButton = new JButton("Trójki");
        JButton czworkiButton = new JButton("Czwórki");
        JButton piatkiButton = new JButton("Piątki");
        JButton szostkiButton = new JButton("Szóstki");
        JButton trojkaButton = new JButton("Trójka");
        JButton czworkaButton = new JButton("Czwórka");
        JButton fulButton = new JButton("Ful");
        JButton malyButton = new JButton("Mały Strit");
        JButton duzyButton = new JButton("Duży Strit");
        JButton generalButton = new JButton("Generał");
        JButton szansaButton = new JButton("Szansa");
        leftPanel.setLayout(new GridLayout(13,1));
        leftPanel.add(jedynkiButton);
        leftPanel.add(dwojkiButton);
        leftPanel.add(trojkiButton);
        leftPanel.add(czworkiButton);
        leftPanel.add(piatkiButton);
        leftPanel.add(szostkiButton);
        leftPanel.add(trojkaButton);
        leftPanel.add(czworkaButton);
        leftPanel.add(fulButton);
        leftPanel.add(malyButton);
        leftPanel.add(duzyButton);
        leftPanel.add(generalButton);
        leftPanel.add(szansaButton);
        JPanel rightPanel = new JPanel();
        rightPanel.add(new JTextField("PRAWA STRONA!"));
        canvas.setBounds(0, 0, x_canvas, y_canvas);
        canvas.setBackground(Color.CYAN);
        panel.setLayout(new BorderLayout());
        panel.add(canvas, BorderLayout.CENTER);
        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);
        this.add(panel);

        setResizable(false);
        pack();
        setVisible(true);
    }

    @Override
    public void init(Set<Command> availableCommands) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Command read(Set<Command> availableCommands) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void callback(CommandResponse response) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void send(Command command) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public static void main(String[] args){
        new DicesGui();
    }

    @Override
    public void init(Player player, GameState initialState) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void update(GameState newState) {
        //To change body of implemented methods use File | Settings | File Templates.
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

