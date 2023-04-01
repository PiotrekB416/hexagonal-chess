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

    protected static HashMap<String, Integer> revdict = new HashMap<String, Integer>(){
        {
            put("a", 1); put("b", 2); put("c", 3); put("d", 4); put("e", 5); put("f", 6);
            put("g", 7); put("h", 8); put("i", 9); put("k", 10); put("l", 11);
        }
    };
    protected ArrayList<Integer> generateMovesFromArray(int[][] moveArray, int rank, String file, boolean rep){
        ArrayList<Integer> moves = new ArrayList<>();
            for (int[] i : moveArray){
//                int mult = 1;
//                int offset_mult = 0;
//                int offset = 0;
//                if (revdict.get(file) < 6){
//                    offset_mult = 1;
//                } else if (revdict.get(file) > 6) {
//                    offset_mult = -1;
//                }
//                offset = (offset_mult * i[1] > 0) ? 1 : 0;
//                if (rep){
//                    offset = 0;
//                }
//
//                do {
//                    int pos = ((11 - rank) - (i[0] + offset) * mult) * 11 + (revdict.get(file) + (i[1]) * mult) -1;
//                    int r = rank + i[0] * mult;
//                    int f = revdict.get(file) + i[1] * mult;
//
//                    if ((r > 11 || r < 1 || f > 11 || f < 1)
//                    ) {
//                        break;
//                    }
//                    moves.add(pos);
//                    mult++;
//                } while (rep);

                switch (file){
                    case "f" -> {
                        int mult = 1;
                        do{
                            int f = revdict.get(file) + i[1] * mult;
                            int r = rank + i[0] * mult;
                            int pos = ((11 - rank) - (i[0]) * mult)  * 11 + (revdict.get(file) + (i[1] * mult)) -1;
                            if ((r > 11 || r < 1 || f > 11 || f < 1)
                            ) {
                                break;
                            }
                            moves.add(pos);
                            mult++;
                        } while (rep);
                    }
                }



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
