
package App;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    private int type;
    private boolean isCheck;
    public void changeLayout() {
        changeLayout(0);
    }

    public void changeLayout(int x, int color, boolean isCheck) {
        this.type = color;
        this.isCheck = isCheck;
        changeLayout(x);
    }

    public void changeLayout(Board board) {
        this.type = board.checkDraw(board);
        if (this.type != 0) {
            changeLayout(2);
        }
    }
    private final String position;
    private void changeLayout(int x) {
        switch (x) {
            case 0 -> {
                this.setContentPane(new Board( this.position,1, -1, this));
                revalidate();
            }
            case 1 -> {
                String message = " wins: ";
                if (this.type == 1) {
                    message = "White" + message;
                } else {
                    message = "Black" + message;
                }
                if (this.isCheck){
                    message += "checkmate";
                } else {
                    message += "stalemate";
                }
                this.setContentPane(new Screen(this, ScreenType.End, message));
                revalidate();
                repaint();

            }
            case 2 -> {
                String message = "Draw: ";
                if (this.type == 1) {
                    message += "Insufficient Material";
                } else if (this.type == 2){
                    message += "Threefold Repetition";
                }
                this.setContentPane(new Screen(this, ScreenType.End, message));
                revalidate();
                repaint();
            }
            default -> {}
        }
    }
    public void showMenu(){
        this.setContentPane(new Screen(this, ScreenType.Start));
        revalidate();
        repaint();
    }
    public App(){
        super();
        setTitle("Szachy heksagonalne");
        setIconImage(new ImageIcon("src/Images/brown.png").getImage());

        this.position = "b/qbk/n1b1n/r5r/ppppppppp/11/5P5/4P1P4/3P1B1P3/2P2B2P2/1PRNQBKNRP1";
        //this.position = "k/3/5/7/9/11/5p5/4P6/11/11/10K";

        setPreferredSize(new Dimension(1400, 1200));
        setSize(new Dimension(1400, 1200));
        setLocationRelativeTo(null);
        setContentPane(new Screen(this, ScreenType.Start));
        revalidate();
        super.pack();
        super.setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}