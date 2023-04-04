package App;

import javax.swing.*;

public class App extends JFrame {

    public App(){

        super();
        super.setSize(1150, 1200);
        super.setLocationRelativeTo(null);
        super.setVisible(true);
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.add(new Board("b/qbk/n1b1n/r5r/ppppppppp/11/5P5/4P1P4/3P1B1P3/2P2B2P2/1PRNQBKNRP1", true, -1));
        //super.add(new Board("1/3/5/7/9/2N8/3N4n2/10N/11/11/11"));
    }
}
