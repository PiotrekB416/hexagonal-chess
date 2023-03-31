package App;

import javax.swing.*;

public class App extends JFrame {
    public double scale = 1;
    public App(double scale){
        super();
        this.scale = scale;
        super.setSize((int)(1150 * scale), (int)(1200*scale));
        super.setLocationRelativeTo(null);
        super.setVisible(true);
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.add(new Board(scale));
    }

    public App(){

        super();
        super.setSize(1150, 1200);
        super.setLocationRelativeTo(null);
        super.setVisible(true);
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.add(new Board(1.0));
    }
}
