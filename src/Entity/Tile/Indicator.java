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

    public Image getTexture() {

        if (this.type[0]) {
            return new ImageIcon("src/Images/dot.png").getImage();
        }
        return new ImageIcon("src/Images/Empty.png").getImage();
    }
    public Image getTileTexture() {
         if (this.type[1]) {
            return new ImageIcon("src/Images/selected.png").getImage();
         } else if (this.type[2]) {
            return new ImageIcon("src/Images/checked.png").getImage();
         }
         return null;
    }
    @Override
    public void draw(Graphics g, double scale, ImageObserver observer) {
        int offset = this.offset.get(this.getFile()) * 50;
        int hoffset = ((int) (57.74 + 28.88)) * revdict.get(this.getFile());
        g.drawImage(this.getTexture(), (int) ((hoffset + 42.5) * scale), (int) ((startheight + (100 * (12 - this.getRank())) + offset + 35) * scale), (int) (30 * scale), (int) (30 * scale), observer);
    }
}
