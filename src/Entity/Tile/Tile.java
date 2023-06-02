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
    private final Indicator indicator;
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
    private Polygon polygon;

    public Polygon getPolygon() {
        return polygon;
    }

    private final Color[] colors = new Color[]{Color.decode("#a05a2c"), Color.decode("#ff9955"), Color.decode("#803300")};
    @Override
    public void draw(Graphics g, double scale, ImageObserver observer) {
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
//        int offset = this.offset.get(this.getFile()) * 50;
//        double hoffset = ((57.74 + 28.88)) * (double)revdict.get(this.getFile());
        double offset = 54;
        double centerY = startheight + offset + (100 * (12 - this.getRank())) + this.offset.get(this.getFile()) * 50;
        double centerX = ((57.74 + 28.83)) * (double)(revdict.get(this.getFile())) + offset + ((6-revdict.get(this.getFile())) * 0.5);

        this.polygon = new Polygon();

        for (int i = 0; i < 6; i++) {
            double radius = 54;
            this.polygon.addPoint((int) (centerX * scale + radius * Math.cos(i * 2 * Math.PI / 6) * scale),
                    (int) (centerY * scale + radius * Math.sin(i * 2 * Math.PI / 6) * scale));
        }
        g.setColor(colors[color]);
        if (this.getIndicator().getIndicator(1) || this.getIndicator().getIndicator(2) || this.getIndicator().getIndicator(3)) {
            g.setColor(this.getIndicator().getTileTexture());
        }

        g.fillPolygon(this.polygon);

        //g.drawImage(this.getTexture(), (int) (hoffset * scale), (int) ((startheight + (100 * (12 - this.getRank())) + offset) * scale), (int) (115 * scale), (int) (100 * scale), observer);
    }
}
