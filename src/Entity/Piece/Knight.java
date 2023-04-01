package Entity.Piece;

import java.util.ArrayList;

public class Knight extends Piece{
    private boolean white;
    public Knight(int rank, String file, boolean white){
        super(rank, file);
        this.white = white;
    }
    private ArrayList<Integer> getMoves(){
        // {rank, file}
        int[][] moveArray = {{-1, -3}, {-2,-3}, {-1 , 3}, {-2, 3},
                {-3, -2}, {-3, -1}, {-3, 1}, {-3, 2},
                {1, -2}, {1, 2}, {2, -1}, {2, 1}};
        return generateMovesFromArray(moveArray, this.rank, this.file);
    }

}