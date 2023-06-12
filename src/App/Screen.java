package App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
            title = new JLabel("Szachy heksagonalne", SwingConstants.CENTER);
        } else {
            title = new JLabel(this.message, SwingConstants.CENTER);
        }
        title.setFont(new Font("Sans", Font.BOLD, 80));
        title.setForeground(Color.ORANGE);
        add(title);
        setLayout(new GridLayout(5, 1));




//        JButton starto;
//
//        if(this.type == ScreenType.Start) {
//            starto = new JButton("Start");
//        } else {
//            starto = new JButton("Play Again");
//        }
//        starto.setFont(new Font("Sans", Font.BOLD, 50));
//        starto.setBackground(Color.BLACK); starto.setForeground(Color.RED); starto.setBorder(null);starto.setFocusPainted(false);
//
//        starto.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                sup.changeLayout();
//            }
//        });
//
//        add(starto);
//
//        JButton quit = new JButton("Quit");
//        quit.setFont(new Font("Sans", Font.PLAIN, 40));
//        quit.setBackground(Color.BLACK); quit.setForeground(Color.GRAY);quit.setBorder(null);quit.setFocusPainted(false);
//
//        quit.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.exit(2);
//            }
//        });
//
//        add(quit);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image bg = new ImageIcon("src/Images/background.jpg").getImage();
        Image start = new ImageIcon("src/Images/start.png").getImage();
        Image quit = new ImageIcon("src/Images/quit.png").getImage();
        g.drawImage(bg, 0, 0, this.getWidth(), this.getHeight(), this);
        int width = this.getWidth() / 5;
        int height = (int) ((double) (start.getHeight(null) / (double) (start.getWidth(null))) * width);

        g.drawImage(start, this.getWidth()/3, this.getHeight()/3, width, height, null);
        g.drawImage(quit, this.getWidth()/3 + (int) (width * 0.75) + 10, this.getHeight()/3 + height / 2 + 10, width, height, null);
        for( MouseListener al : this.getMouseListeners() ) {
            this.removeMouseListener( al );
        }
        int w = this.getWidth() / 3; int h = this.getHeight() / 3;
        Polygon startPolygon = new Polygon();
        startPolygon.addPoint(w + 0,         h + height / 4);
        startPolygon.addPoint(w + width / 2, h + 0);
        startPolygon.addPoint(w + width,     h + height / 4);
        startPolygon.addPoint(w + width,     h + height * 3 / 4);
        startPolygon.addPoint(w + width / 2, h + height);
        startPolygon.addPoint(w + 0,         h + height * 3 / 4);

        w = this.getWidth()/3 + (int) (width * 0.75) + 10; h = this.getHeight()/3 + height / 2 + 10;
        Polygon quitPolygon = new Polygon();
        quitPolygon.addPoint(w + 0,         h + height / 4);
        quitPolygon.addPoint(w + width / 2, h + 0);
        quitPolygon.addPoint(w + width,     h + height / 4);
        quitPolygon.addPoint(w + width,     h + height * 3 / 4);
        quitPolygon.addPoint(w + width / 2, h + height);
        quitPolygon.addPoint(w + 0,         h + height * 3 / 4);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Point p = new Point(e.getX(), e.getY());
                if (startPolygon.contains(p)) {
                    sup.changeLayout();
                } else if (quitPolygon.contains(p)) {
                    System.exit(2);
                }
            }
        });
    }
}
