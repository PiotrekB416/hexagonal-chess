package App;

import javax.swing.*;

public class App extends JFrame {

    public App(){

        super();
        double scale = 1;
        super.setSize((int) (1150 * scale), (int)(1200 * scale));
        super.setLocationRelativeTo(null);
        super.setVisible(true);
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Board board = new Board("b/qbk/n1b1n/r5r/ppppppppp/11/5P5/4P1P4/3P1B1P3/2P2B2P2/1PRNQBKNRP1", 1, -1, this);
        board.setScale(scale);
        super.add(board);
        //super.add(new Board("k/3/5/7/9/1Q9/10Q/Q10/11/11/10K", 1, -1));
        //super.add(new Board("k/3/5/7/9/2N8/3N2r1n2/10N/6Q4/11/6K4", 1, -1));
    }
}
