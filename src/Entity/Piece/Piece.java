package Entity.Piece;

import App.Board;
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
    public static HashMap<Integer, int[]> generateNeighboringMap(int file){
        HashMap<Integer, int[]> neighboringMap = null;
        switch (file){
            case 1, 2, 3, 4, 5 -> {
                neighboringMap = new HashMap<>(){
                    {
                        put(0, new int[]{1, 0}); put(1, new int[]{2, 1}); put(2, new int[]{1, 1});
                        put(3, new int[]{1, 2}); put(4, new int[]{0, 1}); put(5, new int[]{-1, 1});
                        put(6, new int[]{-1, 0}); put(7, new int[]{-2, -1}); put(8, new int[]{-1, -1});
                        put(9, new int[]{-1, -2}); put(10, new int[]{0, -1}); put(11, new int[]{1, -1});
                    }
                } ;
                if (file == 5){
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
                if (file == 7){
                    neighboringMap.replace(9, new int[]{0, -2});
                }
            }
        }
        return neighboringMap;
    }
    public static ArrayList<Integer> generateMovesFromArray(int[][] moveArray, int rank, String file, boolean rep, boolean validate){
        ArrayList<Integer> moves = new ArrayList<>();
        for (int[] i : moveArray){
            if(!validate){
                moves.add(-1);
            }
            int startpos = (11 - rank) * 11 + revdict.get(file) - 1;
            boolean repeat = rep;
            int[] testpos = new int[]{revdict.get(file), rank};
            HashMap<Integer, int[]> neighboringMap;
            do {
                int r = testpos[1];
                int f = testpos[0];
                for(int j : i){
                    if (j < 0){
                        break;
                    }
                    neighboringMap = generateNeighboringMap(testpos[0]);
                    r += neighboringMap.get(j)[0];
                    f += neighboringMap.get(j)[1];
                    testpos = new int[]{f, r};
                    if(r < 1 || r > 11 || f < 1 || f > 11){
                        break;
                    }
                }
                if(r < 1 || r > 11 || f < 1 || f > 11){
                    break;
                }
                testpos = new int[]{f, r};
                int pos = (11 - r) * 11 + f - 1;
                //System.out.println(pos);
                if(Board.board.get(pos) == null){
                    break;
                }
                if(validate) {
                    if (Board.board.get(pos).getPiece().isWhite() == Board.board.get(startpos).getPiece().isWhite()) {
                        break;
                    }

                    if(!Board.validateMove(startpos, pos)){
                        continue;
                    }
                    if (Board.board.get(startpos).getPiece().getClass() == Pawn.class) {
                        if (revdict.get(file) - f != 0) {
                            if (pos == Board.enPassant) {
                                moves.add(pos);
                                continue;
                            }
                            if (Board.board.get(pos).getPiece().getClass() != Empty.class & Board.board.get(pos).getPiece().isWhite() != Board.board.get(startpos).getPiece().isWhite()) {
                                moves.add(pos);
                                continue;
                            }
                            continue;
                        } else {
                            if (Board.board.get(pos).getPiece().getClass() != Empty.class) {
                                continue;
                            }
                        }
                    }
                    if (Board.board.get(pos).getPiece().getClass() == Empty.class) {
                        moves.add(pos);
                        continue;
                    }
                    moves.add(pos);
                    break;

                } else {
                    moves.add(pos);
                }

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

    public int isWhite() {
        return -1;
    }

}
