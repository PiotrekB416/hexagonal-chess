package Entity.Tile;

import Entity.Entity;
import Entity.Piece.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.UUID;

public class Tile extends Entity {
    private HashMap<String, Integer> revdict = new HashMap(){
        {
            put("a", 1); put("b", 2); put("c", 3); put("d", 4); put("e", 5); put("f", 6);
            put("g", 7); put("h", 8); put("i", 9); put("k", 10); put("l", 11);
        }
    };
    public Piece piece;
    public Tile(int rank, String file){
        super(rank, file);
    }
    public Tile(int rank, String file, Piece piece) {
        super(rank, file);
        this.piece = piece;
    }
    public Image getTexture() {
        int color = this.rank % 3;
        if (this.revdict.get(this.file) % 3 == 1) {
            color += 2; color %= 3;
        } else if (this.revdict.get(this.file) % 3 == 2) {
            color++; color %= 3;
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
}
