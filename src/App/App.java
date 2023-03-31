package App;

import javax.swing.*;

public class App extends JFrame {

    public App(){

        super();
        super.setSize(1150, 1200);
        super.setLocationRelativeTo(null);
        super.setVisible(true);
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.add(new Board());
    }
}
