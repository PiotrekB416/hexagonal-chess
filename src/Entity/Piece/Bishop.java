package Entity.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Bishop extends Piece {
    private boolean white;
    public Bishop(int rank, String file, boolean white) {
        super(rank, file);
        this.white = white;
    }

    @Override
    public ArrayList<Integer> getPossibleMoves(){
        return getMoves();
    }

    private ArrayList<Integer> getMoves(){

        ArrayList<Integer> ret = new ArrayList();
        //int file = this.revdict.get(this.file);
        int[][] moveArray = new int[][]{
                {1, -1}, {3, -1}, {5, -1}, {7, -1}, {9, -1}, {11, -1}
        };

        return generateMovesFromArray(moveArray, this.rank, this.file, true);
    }

    @Override
    public Image getTexture() {
        String color = "black";
        if (this.white){
            color = "white";
        }
        String file = "src/Images/" + color + "/bishop.png";

        Image image = new ImageIcon(file).getImage();

        return image;
    }
}