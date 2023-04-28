package App;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreen extends JPanel {
    public StartScreen(JFrame sup){
        setBackground(Color.BLACK);
        JLabel title = new JLabel("Szachy hexagonalne", SwingConstants.CENTER);
        title.setFont(new Font("Sans", Font.BOLD, 80));
        title.setForeground(Color.ORANGE);
        add(title);
        setLayout(new GridLayout(5, 1));

        JButton starto = new JButton("START");
        starto.setFont(new Font("Sans", Font.BOLD, 50));
        starto.setBackground(Color.BLACK); starto.setForeground(Color.RED); starto.setBorder(null);starto.setFocusPainted(false);

        starto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sup.dispose();
                new App(0);
            }
        });

        add(starto);

        JButton quit = new JButton("Quit");
        quit.setFont(new Font("Sans", Font.PLAIN, 40));
        quit.setBackground(Color.BLACK); quit.setForeground(Color.GRAY);quit.setBorder(null);quit.setFocusPainted(false);

        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(2);
            }
        });

        add(quit);
        repaint();
    }
}

