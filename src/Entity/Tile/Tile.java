package Entity.Tile;

import Interfaces.IDrawable;
import Interfaces.IHashMaps;
import Entity.Entity;
import Entity.Piece.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class Tile extends Entity implements IHashMaps, IDrawable {
    public Piece getPiece() {
        return this.piece;
    }
    public Indicator getIndicator() {
        return this.indicator;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
    public void setCheck(boolean check) {
        this.getIndicator().setIndicator(check, 2);
    }
    private Piece piece;
    private Indicator indicator;
    public Tile(int rank, String file){
        super(rank, file);
        this.piece = null;
        this.indicator = new Indicator(rank, file);
    }
    public Tile(int rank, String file, Piece piece) {
        super(rank, file);
        this.piece = piece;
        this.indicator = new Indicator(rank, file);
    }
    public Image getTexture() {
        if (this.getIndicator().getIndicator(1) || this.getIndicator().getIndicator(2)) {
            return this.getIndicator().getTileTexture();
        }
        int color = this.rank % 3;
        if (this.revdict.get(this.file) % 3 == 1) {
            color += 2;
            if (this.revdict.get(this.file) < 6){
                color += 2;
            }
            color %= 3;
        } else if (this.revdict.get(this.file) % 3 == 2) {
            color++;
            if (this.revdict.get(this.file) < 6){
                color += 1;
            }
            color %= 3;
        }
        return switch (color) {
            case 0 -> new ImageIcon("src/Images/brown.png").getImage();
            case 1 -> new ImageIcon("src/Images/light.png").getImage();
            case 2 -> new ImageIcon("src/Images/dark.png").getImage();
            default -> throw new IllegalStateException("Unexpected value: " + color);
        };
    }
    @Override
    public void draw(Graphics g, double scale, ImageObserver observer) {
        int offset = this.offset.get(this.getFile()) * 50;
        int hoffset = ((int) (57.74 + 28.88)) * revdict.get(this.getFile());
        g.drawImage(this.getTexture(), (int) (hoffset * scale), (int) ((startheight + (100 * (12 - this.getRank())) + offset) * scale), (int) (115 * scale), (int) (100 * scale), observer);
    }
}
