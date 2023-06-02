package Entity.Tile;

import Entity.Entity;
import Interfaces.IDrawable;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class Indicator extends Entity implements IDrawable {

    // 0 - possible move here, 1 - selected, 2 - checked, 3 - moved
    private boolean[] type;

    public Indicator (int rank, String file) {
        super(rank, file);
        this.type = new boolean[]{false, false, false, false};
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
         } else if (this.type[3]) {
             return Color.decode("#BAEBAE");
         }
         return null;
    }
    @Override
    public void draw(Graphics g, double scale, ImageObserver observer) {
        g.setColor(Color.decode("#00ff00"));

        //double centerY = startheight + offset + (100 * (12 - this.getRank())) + this.offset.get(this.getFile()) * 50 + 35;
        //double centerX = ((57.74 + 28.83)) * (double)(revdict.get(this.getFile())) + offset + 39;
        double radius = 54;
        double offset = 54;
        double centerX = ((57.74 + 28.83)) * (double)(revdict.get(this.getFile())) + offset + ((6-revdict.get(this.getFile())) * 0.5);
        // calculate the difference of x coordinate of bottom left and bottom right points of the tile hexagon and offset it by radius of the indicator to make the circle drawn in the center
        double xPoint = centerX * scale + radius * Math.cos(2 * Math.PI / 6);
        double xOffset = (xPoint - (centerX * scale + radius * Math.cos(2*2 * Math.PI / 6))) / 2 + (30 * scale) / 2;
        centerX = xPoint - xOffset;
        double centerY = startheight + offset + (100 * (12 - this.getRank())) + this.offset.get(this.getFile()) * 50;
        // calculate the difference of y coordinate of top and bottom points of the tile hexagon and offset it by radius of the indicator to make the circle drawn in the center
        double yPoint = centerY * scale + radius * Math.sin(2 * Math.PI / 6);
        double yOffset = (yPoint - (centerY * scale + radius * Math.sin(4*2 * Math.PI / 6))) / 2 + (30 * scale) / 2;
        centerY = yPoint - yOffset;
        if (this.type[0]) {

            g.fillOval((int)centerX, (int)centerY, (int)(30 * scale), (int)(30 * scale));
        }
    }
}
