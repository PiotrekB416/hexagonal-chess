package Entity.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class King extends Piece {
    private boolean white;
    public King(int rank, String file, boolean white) {
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
                {0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9}, {10}, {11}
        };

        return generateMovesFromArray(moveArray, this.rank, this.file, false);
    }

    @Override
    public Image getTexture() {
        String color = "black";
        if (this.white){
            color = "white";
        }
        String file = "src/Images/" + color + "/king.png";

        Image image = new ImageIcon(file).getImage();

        return image;
    }
    @Override
    public boolean isWhite() {
        return white;
    }
}