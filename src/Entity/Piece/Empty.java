package Entity.Piece;

import Entity.Entity;

import java.util.ArrayList;

public class Empty extends Piece {

    public Empty(int rank, String file){
        super(rank, file);
    }

    @Override
    public ArrayList<Integer> getPossibleMoves(){
        return super.getPossibleMoves();
    }

}
