package Entity.Piece;

import Entity.Entity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Piece extends Entity {
    public Piece(int rank, String file){
        super(rank, file);

    }

    protected static HashMap<String, Integer> revdict = new HashMap(){
        {
            put("a", 1); put("b", 2); put("c", 3); put("d", 4); put("e", 5); put("f", 6);
            put("g", 7); put("h", 8); put("i", 9); put("k", 10); put("l", 11);
        }
    };
    protected ArrayList<Integer> generateMovesFromArray(int[][] moveArray, int rank, String file){
        ArrayList<Integer> moves = new ArrayList<>();
        for (int[] i : moveArray){
            int pos = (rank + i[0]) * 11 + (revdict.get(file) + i[1]);
            int r = rank + i[0], f = revdict.get(file) + i[1];
            if (r > 6 && (f <= r - 6 || f + r >= 17)){
                continue;
            }
            moves.add(pos);
        }
        return moves;
    }
    public ArrayList<Integer> getPossibleMoves(){
        return new ArrayList();
    }

    public Image getTexture(){
        return new ImageIcon().getImage();
    }

}
