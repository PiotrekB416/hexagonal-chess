package Entity.Piece;

import Entity.Entity;

import java.util.ArrayList;

public abstract class Piece extends Entity {
    public Piece(int rank, String file){
        super(rank, file);
    }

    public ArrayList<Integer> getPossibleMoves(){
        return new ArrayList<Integer>();
    }
}
