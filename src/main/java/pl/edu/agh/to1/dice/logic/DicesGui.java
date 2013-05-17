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
import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: Johnny
 * Date: 16.05.13
 * Time: 22:28
 * To change this template use File | Settings | File Templates.
 */
public class DicesGui extends JFrame{

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
        canvas.setBounds(0,0, x_canvas, y_canvas);
        canvas.setBackground(Color.CYAN);
        canvas.addMouseListener(mouseListener);
        panel.setLayout(new BorderLayout());
        panel.add(canvas, BorderLayout.WEST);
        this.add(panel);

        setResizable(false);
        pack();
        setVisible(true);
    }

    private MouseListener mouseListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            int x1 = e.getX();
            int y1 = e.getY();
            checkingValue(x1, y1);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            //not important
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //not important
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //not important
        }

        @Override
        public void mouseExited(MouseEvent e) {
            //not important
        }
    };

    private void checkingValue(int x, int y){
        //player = players.get((x-10)/80);
        //result = results.get((y-10)/30);
        //if(player.equals(playing_player)){
        //  if(player.resluts.getresult(result) == NULL){
        //      aktualizacja
        //  }
        //}
    }

    public static void main(String[] args){
        new DicesGui();
    }


}

