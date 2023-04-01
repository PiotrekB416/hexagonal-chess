package Entity.Tile;

import Entity.Entity;
import Entity.Piece.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.UUID;

public class Tile extends Entity {
    private HashMap<String, Integer> revdict = new HashMap(){
        {
            put("a", 1); put("b", 2); put("c", 3); put("d", 4); put("e", 5); put("f", 6);
            put("g", 7); put("h", 8); put("i", 9); put("k", 10); put("l", 11);
        }
    };

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    private Piece piece;
    public Tile(int rank, String file){
        super(rank, file);
        this.piece = null;
    }
    public Tile(int rank, String file, Piece piece) {
        super(rank, file);
        this.piece = piece;
        this.moveIndicator = false;
    }
    public Image getTexture() {

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

        Image dark = new ImageIcon("src/Images/dark.png").getImage();
        Image medium = new ImageIcon("src/Images/brown.png").getImage();
        Image white = new ImageIcon("src/Images/light.png").getImage();
        switch (color) {
            case 0: return medium;
            case 1: return white;
            case 2 : return dark;
            default:
                throw new IllegalStateException("Unexpected value: " + color);
        }
    }

    public boolean isMoveIndicator() {
        return moveIndicator;
    }

    public void setMoveIndicator(boolean moveIndicator) {
        this.moveIndicator = moveIndicator;
    }

    private boolean moveIndicator;

    public Image getMoveIndicatorTexture(){
        if (this.moveIndicator){
            return new ImageIcon("src/Images/dot.png").getImage();
        }
        return new ImageIcon("src/Images/Empty.png").getImage();
    }
}
