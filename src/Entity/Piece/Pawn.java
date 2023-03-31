package Entity.Piece;

import Entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;

public class Pawn extends Piece {
    public Pawn(int rank, String file, boolean white){
        super(rank, file, white);
    }


    @Override
    public ArrayList<Integer> getPossibleMoves(){
        if (this.white){
            return this.getWhiteMoves();
        }
        return getBlackMoves();
    }

    private ArrayList<Integer> getWhiteMoves(){
        ArrayList<Integer> ret = new ArrayList();
        int[] orgpos = new int[]{
                0, 0, 1, 2, 3, 4, 5, 5, 4, 3, 2
        };
        int file = super.revdict.get(this.file);

        int[][] moves = new int[][]{
                {1, 0}, {0, -1}, {0, 1}
        };
        if (orgpos[file] == this.rank){
            ret.add((this.rank + 2) * 11 + file + 0);
        }
        for(int[] move : moves){

            ret.add((this.rank + move[0]) * 11 + file + move[1]);
        }

        return ret;
    }
    private ArrayList<Integer> getBlackMoves(){
        ArrayList<Integer> ret = new ArrayList();

        int file = this.revdict.get(this.file);

        int[][] moves = new int[][]{
                {1, 0}, {1, -1}, {1, 1}
        };


        if (this.rank == 7){
            ret.add((this.rank - 2) * 11 + file + 0);
        }
        for(int[] move : moves){

            ret.add((this.rank - move[0]) * 11 + file + move[1]);
        }

        return ret;
    }

}
