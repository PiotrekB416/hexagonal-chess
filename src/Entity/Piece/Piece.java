package Entity.Piece;

import Entity.Entity;

import java.util.HashMap;

public class Piece extends Entity {

    private HashMap<Integer, String> pieces = new HashMap<Integer, String>(){
        {
            put(0, "none"); put(1, "pawn"); put(2, "knight"); put(3, "bishop"); put(4, "rook"); put(5, "queen"); put(6, "king");
        }
    };

    public Piece(int rank, String file, int type, String id){
        super(rank, file, id);
    }

    public void move(int rank, int file){

    }
}
