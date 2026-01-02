package Entity.Piece;

import Interfaces.IDrawable;

import java.awt.*;
import java.awt.image.ImageObserver;

public class Promotion implements IDrawable {
    private Piece piece;
    private int type;
    private Polygon polygon;
    public Polygon getPolygon() {
        return this.polygon;
    }
    public Promotion(int type, int color) {
        this.type = type;
        switch (type) {
            case 0 -> this.piece = new Queen(-1, "0", color);
            case 1-> this.piece = new Rook(-1, "0", color);
            case 2 -> this.piece = new Bishop(-1, "0", color);
            case 3-> this.piece = new Knight(-1, "0", color);
        }

    }
    @Override
    public void draw(Graphics g, double scale, ImageObserver observer) {
        int length = (int)(80 * scale);
        int[][] offsets = new int[][] {
                {(int) (((57.74 + 28.88) * 5 + 38 - 10) * scale), (int) ((450 + 47 - 10) * scale)},
                {(int) ((((57.74 + 28.88) * 5 - 5) + 43*2 + 80 + 10) * scale), (int) ((450 + 47 - 10) * scale)},
                {(int) (((57.74 + 28.88) * 5 + 38 - 10) * scale), (int) ((530 + 47*2 + 10) * scale)},
                {(int) ((((57.74 + 28.88) * 5 - 5) + 43*2 + 80 + 10) * scale), (int) ((530 + 47*2 + 10) * scale)}
        };
        this.polygon = new Polygon();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                this.polygon.addPoint(
                        (int)(offsets[this.type][0] - length/2 + 144 * i * scale + 10 * scale),
                        (int)(offsets[this.type][1] - length/2 + 150 * j * scale)
                );
            }
        }
        g.drawImage(this.piece.getTexture(), offsets[this.type][0], offsets[this.type][1], length, length, observer);
    }
}
