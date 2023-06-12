package App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

enum ScreenType {
    Start,
    End
}

public class Screen extends JPanel {
    private final App sup;
    private final ScreenType type;
    private String message;

    public Screen(App sup, ScreenType type) {
        this.sup = sup;
        this.type = type;
        this.new_screen();
    }
    public Screen(App sup, ScreenType type, String message) {
        this.sup = sup;
        this.type = type;
        this.message = message;
        this.new_screen();
    }
    private void new_screen() {
        setBackground(Color.BLACK);
        JLabel title;
        if (this.type == ScreenType.Start) {
            title = new JLabel("Szachy hexagonalne", SwingConstants.CENTER);
        } else {
            title = new JLabel(this.message, SwingConstants.CENTER);
        }
        title.setFont(new Font("Sans", Font.BOLD, 80));
        title.setForeground(Color.ORANGE);
        add(title);
        setLayout(new GridLayout(5, 1));

        JButton starto;

        if(this.type == ScreenType.Start) {
            starto = new JButton("Start");
        } else {
            starto = new JButton("Play Again");
        }
        starto.setFont(new Font("Sans", Font.BOLD, 50));
        starto.setBackground(Color.BLACK); starto.setForeground(Color.RED); starto.setBorder(null);starto.setFocusPainted(false);

        starto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sup.changeLayout();
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
