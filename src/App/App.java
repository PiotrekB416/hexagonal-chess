
package App;

import javax.swing.*;

public class App extends JFrame {

    private int type;
    private boolean isCheck;

    public App(){
        super();
        newApp(this, -1);
    }

    public App(int x){
        super();
        newApp(this, x);
    }

    public App(Board board) {
        this.type = board.checkDraw(board);

        newApp(this, 2);
    }

    public App(int x, int color, boolean isCheck) {
        this.type = color;
        this.isCheck = isCheck;

        newApp(this, x);
    }

    private void newApp(JFrame sup, int x){
        sup.setTitle("Szachy heksagonalne");
        sup.setIconImage(new ImageIcon("src/Images/brown.png").getImage());

        sup.setSize(1150, 1200);
        sup.setLocationRelativeTo(null);
        sup.setVisible(true);
        //super.add(new Board("b/qbk/n1b1n/r5r/ppppppppp/11/5P5/4P1P4/3P1B1P3/2P2B2P2/1PRNQBKNRP1", 1, -1, this));
        //"k/3/5/7/9/11/5p5/4P6/11/11/10K"


//        pane.add(new StartScreen(this));
//        pane.add(new Board( "b/qbk/n1b1n/r5r/ppppppppp/11/5P5/4P1P4/3P1B1P3/2P2B2P2/1PRNQBKNRP1",1, -1, this));
//


        switch (x) {
            case -1 -> {sup.repaint(); add(new StartScreen(this));}
            case 0 -> {sup.repaint(); add(new Board( "b/qbk/n1b1n/r5r/ppppppppp/11/5P5/4P1P4/3P1B1P3/2P2B2P2/1PRNQBKNRP1",1, -1, this)); sup.repaint(); }
            case 1 -> {
                String message = " wins: ";
                if (this.type == 1) {
                    message = "White" + message;
                } else {
                    message = "Black" + message;
                }
                if (this.isCheck){
                    message += "Checkmate";
                } else {
                    message += "Stalemate";
                }
                add(new EndScreen(this, message));
            }
            case 2 -> {
                String message = "Draw: ";
                if (this.type == 1) {
                    message += "Insufficient Material";
                } else if (this.type == 2){
                    message += "Threefold Repetition";
                }
                add(new EndScreen(this, message));
            }
            default -> {}
        }

        //super.add(new Board("", 1, -1, this))
        sup.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}