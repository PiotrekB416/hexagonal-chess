package Entity.Piece;

import Entity.Entity;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
