package Entity.Piece;

import Entity.Entity;

public class Empty extends Entity {

//    protected HashMap<Integer, String> pieces = new HashMap<Integer, String>(){
//        {
//            put(0, "none"); put(1, "pawn"); put(2, "knight"); put(3, "bishop"); put(4, "rook"); put(5, "queen"); put(6, "king");
//        }
//    };

    public Empty(int rank, String file){
        super(rank, file);
    }

}
