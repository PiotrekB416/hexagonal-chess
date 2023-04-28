
package App;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    private int color;
    private boolean ischeck;

    public App(){
        super();
        newApp(this, -1);

    }

    public App(int x){
        super();
        newApp(this, x);
    }

    public App(int x, int color, boolean ischeck) {
        this.color = color;
        this.ischeck = ischeck;

        newApp(this, x);
    }

    private void newApp(JFrame sup, int x){
        sup.setTitle("Szachy heksagonalne");
        sup.setIconImage(new ImageIcon("src/Images/brown.png").getImage());

        sup.setSize(1150, 1200);
        sup.setLocationRelativeTo(null);
        sup.setVisible(true);
        //super.add(new Board("b/qbk/n1b1n/r5r/ppppppppp/11/5P5/4P1P4/3P1B1P3/2P2B2P2/1PRNQBKNRP1", 1, -1, this));
        switch (x) {
            case -1 -> add(new StartScreen(this));
            case 0 -> sup.add(new Board("k/3/5/7/9/1Q9/10Q/Q10/11/11/10K", 1, -1, this));
            case 1 -> {
                String message = " wins: ";
                if (this.color == 1) {
                    message = "white" + message;
                } else {
                    message = "black" + message;
                }
                if (this.ischeck){
                    message += "checkmate";
                } else {
                    message += "stalemate";
                }

                add(new EndScreen(this, message));

            }
            default -> {}
        }

        //super.add(new Board("", 1, -1, this))
        sup.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}