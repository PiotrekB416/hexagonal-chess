package Entity.Piece;

import Entity.Entity;

import java.util.ArrayList;

public class Pawn extends Piece {
    public boolean white;
    public Pawn(int rank, String file, boolean white){
        super(rank, file);
        this.white = white;
    }

    @Override
    public ArrayList<Integer> getPossibleMoves(){
        ArrayList<Integer> ret = new ArrayList();

        return ret;
    }

}
