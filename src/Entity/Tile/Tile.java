package Entity.Tile;

import Interfaces.IHashMaps;
import Entity.Entity;
import Entity.Piece.Piece;

import javax.swing.*;
import java.awt.*;

public class Tile extends Entity implements IHashMaps {
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
        this.moveIndicator = new boolean[]{false, false};
    }
    public Image getTexture() {
        if(moveIndicator[1]){
            return new ImageIcon("src/Images/selected.png").getImage();
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
        return moveIndicator[0];
    }
    public void setMoveIndicator(boolean moveIndicator) {
        this.moveIndicator[0] = moveIndicator;
    }
    public void setMoveIndicator(boolean moveIndicator, int index) {
        this.moveIndicator[index] = moveIndicator;
    }

    private boolean[] moveIndicator;

    public Image getMoveIndicatorTexture(){
        if (this.moveIndicator[0]){
            return new ImageIcon("src/Images/dot.png").getImage();
        }
        return new ImageIcon("src/Images/Empty.png").getImage();
    }
}
