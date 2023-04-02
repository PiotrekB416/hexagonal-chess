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
    protected static final HashMap<Integer, String> dict = new HashMap<Integer, String>(){
        {
            put(1, "a"); put(2, "b"); put(3, "c"); put(4, "d"); put(5, "e"); put(6, "f");
            put(7, "g"); put(8, "h"); put(9, "i"); put(10, "k"); put(11, "l");
        }
    };
    protected static final HashMap<String, Integer> revdict = new HashMap<String, Integer>(){
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

//                int mult = 1;
//                int offset = 0;
//                int offset2 = 0;
//                String test_file = file;
//
//                do{
//                    int f = revdict.get(file) + i[1] * mult;
//                    test_file = dict.get(f);
//                    if(test_file == null){
//                        break;
//                    }
//                    switch (test_file){
//                        case "a", "b", "c", "d", "e" -> {
//                            offset = (i[1] > 0) ? 1 : 0;
//                            if (revdict.get(file) >= 6){
//                                offset2 = 6 - revdict.get(file);
//                            }
//                        }
//                        case "f" -> {
//                            offset = 0;
//                        }
//                        case "g", "h", "i", "k", "l" -> {
//                            offset = (i[1] < 0) ? 1 : 0;
//                            if (revdict.get(file) <= 6){
//                                offset2 = 6 - revdict.get(file);
//                            }
//                        }
//
//                    }
//                    int r = rank + (i[0] + offset) * mult;
//                    int pos = ((11 - rank) - (i[0] + offset) * mult + offset2)  * 11 + (revdict.get(file) + (i[1] * mult)) -1;
//                    if ((r > 11 || r < 1 || f > 11 || f < 1)
//                    ) {
//                        break;
//                    }
//                    moves.add(pos);
//                    mult++;
//                    test_file = dict.get(f);
//                } while (rep);

                boolean repeat = rep;
                int[] testpos = new int[]{revdict.get(file), rank};
                HashMap<Integer, int[]> neighboringMap = null;
                do {
                    int r = testpos[1];
                    int f = testpos[0];
                    for(int j : i){
                        if (j < 0){
                            break;
                        }
                        switch (testpos[0]){
                            case 1, 2, 3, 4, 5 -> {
                                neighboringMap = new HashMap<>(){
                                    {
                                        put(0, new int[]{1, 0}); put(1, new int[]{2, 1}); put(2, new int[]{1, 1});
                                        put(3, new int[]{1, 2}); put(4, new int[]{0, 1}); put(5, new int[]{-1, 1});
                                        put(6, new int[]{-1, 0}); put(7, new int[]{-2, -1}); put(8, new int[]{-1, -1});
                                        put(9, new int[]{-1, -2}); put(10, new int[]{0, -1}); put(11, new int[]{1, -1});
                                    }
                                } ;
                                if (testpos[0] == 5){
                                    neighboringMap.replace(3, new int[]{0, 2});
                                }
                            }
                            case 6 -> {
                                neighboringMap = new HashMap<>(){
                                    {
                                        put(0, new int[]{1, 0}); put(1, new int[]{1, 1}); put(2, new int[]{0, 1});
                                        put(3, new int[]{-1, 2}); put(4, new int[]{-1, 1}); put(5, new int[]{-2, 1});
                                        put(6, new int[]{-1, 0}); put(7, new int[]{-2, -1}); put(8, new int[]{-1, -1});
                                        put(9, new int[]{-1, -2}); put(10, new int[]{0, -1}); put(11, new int[]{1, -1});
                                    }
                                };
                            }
                            case 7, 8, 9, 10, 11 -> {
                                neighboringMap = new HashMap<>(){
                                    {
                                        put(0, new int[]{1, 0}); put(1, new int[]{1, 1}); put(2, new int[]{0, 1});
                                        put(3, new int[]{-1, 2}); put(4, new int[]{-1, 1}); put(5, new int[]{-2, 1});
                                        put(6, new int[]{-1, 0}); put(7, new int[]{-1, -1}); put(8, new int[]{0, -1});
                                        put(9, new int[]{1, -2}); put(10, new int[]{1, -1}); put(11, new int[]{2, -1});
                                    }
                                };
                                if (testpos[0] == 7){
                                    neighboringMap.replace(9, new int[]{0, -2});
                                }
                            }
                        }
                        r += neighboringMap.get(j)[0];
                        f += neighboringMap.get(j)[1];
                        testpos = new int[]{f, r};
                    }
                    if(r < 1 || r > 11 || f < 1 || f > 11){
                        break;
                    }
                    testpos = new int[]{f, r};
                    int pos = (11 - r) * 11 + f - 1;
                    moves.add(pos);

                } while (repeat);

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
