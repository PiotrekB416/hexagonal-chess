package Entity.Tile;

import Entity.Entity;
import Interfaces.IDrawable;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class Indicator extends Entity implements IDrawable {

    // 0 - possible move here, 1 - selected, 2 - checked
    private boolean[] type;

    public Indicator (int rank, String file) {
        super(rank, file);
        this.type = new boolean[]{false, false, false};
    }
    public boolean getIndicator(int index) {
        return this.type[index];
    }
    public void setIndicator(boolean indicator) {
        this.setIndicator(indicator, 0);
    }
    public void setIndicator(boolean indicator, int index) {
        this.type[index] = indicator;
    }

    public Color getTileTexture() {
         if (this.type[1]) {
            return Color.decode("#00ff00");
         } else if (this.type[2]) {
            return Color.decode("#ff0000");
         }
         return null;
    }
    @Override
    public void draw(Graphics g, double scale, ImageObserver observer) {
        double offset = 0;

        g.setColor(Color.decode("#00ff00"));

        double centerY = startheight + offset + (100 * (12 - this.getRank())) + this.offset.get(this.getFile()) * 50 + 35;
        double centerX = ((57.74 + 28.83)) * (double)(revdict.get(this.getFile())) + offset + 39;
        if (this.type[0]) {

            g.fillOval((int)centerX, (int)centerY, 30, 30);
        }


        //g.drawImage(this.getTexture(), (int) ((hoffset + 42.5) * scale), (int) ((startheight + (100 * (12 - this.getRank())) + offset + 35) * scale), (int) (30 * scale), (int) (30 * scale), observer);
    }
}
