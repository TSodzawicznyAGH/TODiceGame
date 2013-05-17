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

    JButton jedynkiButton;
    JButton dwojkiButton;
    JButton trojkiButton;
    JButton czworkiButton;
    JButton piatkiButton;
    JButton szostkiButton;
    JButton trojkaButton;
    JButton czworkaButton;
    JButton fulButton;
    JButton malyButton;
    JButton duzyButton;
    JButton generalButton;
    JButton szansaButton;
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
        GameState state = new GameState();
        state.addPlayer(new Player("Tom"));
        state.addPlayer(new Player("John"));
        state.addPlayer(new Player("ccc"));
        update(state);
        panel = new JPanel();
        JPanel leftPanel = new JPanel();
        jedynkiButton = new JButton("Jedynki");
        dwojkiButton = new JButton("Dwójki");
        trojkiButton = new JButton("Trójki");
        czworkiButton = new JButton("Czwórki");
        piatkiButton = new JButton("Piątki");
        szostkiButton = new JButton("Szóstki");
        trojkaButton = new JButton("Trójka");
        czworkaButton = new JButton("Czwórka");
        fulButton = new JButton("Ful");
        malyButton = new JButton("Mały Strit");
        duzyButton = new JButton("Duży Strit");
        generalButton = new JButton("Generał");
        szansaButton = new JButton("Szansa");
        jedynkiButton.addActionListener(new jedynkiClicked());
        dwojkiButton.addActionListener(new dwojkiClicked());
        trojkiButton.addActionListener(new trojkiClicked());
        czworkiButton.addActionListener(new czworkiClicked());
        piatkiButton.addActionListener(new piatkiClicked());
        szostkiButton.addActionListener(new szostkiClicked());
        trojkaButton.addActionListener(new trojkaClicked());
        czworkaButton.addActionListener(new czworkaClicked());
        fulButton.addActionListener(new fulClicked());
        malyButton.addActionListener(new malyClicked());
        duzyButton.addActionListener(new duzyClicked());
        generalButton.addActionListener(new generalClicked());
        szansaButton.addActionListener(new szansaClicked());

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

    @Override
    public void init(Player player, GameState initialState) {
        //To change body of implemented methods use File | Settings | File Templates.
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

    class jedynkiClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            jedynkiButton.setEnabled(false);
            //dla kazdego z klawiszy robimy jeszcze
            //dodaj do odpowiedniego wyniku
            //daj zagrac nastepnemu graczowi
        }
    }

    class dwojkiClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dwojkiButton.setEnabled(false);
        }
    }

    class trojkiClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            trojkiButton.setEnabled(false);
        }
    }

    class czworkiClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            czworkiButton.setEnabled(false);
        }
    }

    class piatkiClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            piatkiButton.setEnabled(false);
        }
    }

    class szostkiClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            szostkiButton.setEnabled(false);
        }
    }

    class trojkaClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            trojkaButton.setEnabled(false);
        }
    }

    class czworkaClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            czworkaButton.setEnabled(false);
        }
    }

    class fulClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            fulButton.setEnabled(false);
        }
    }

    class malyClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            malyButton.setEnabled(false);
        }
    }

    class duzyClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            duzyButton.setEnabled(false);
        }
    }

    class generalClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            generalButton.setEnabled(false);
        }
    }

    class szansaClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            szansaButton.setEnabled(false);
        }
    }

    public static void main(String[] args){
        new DicesGui();
    }

}

