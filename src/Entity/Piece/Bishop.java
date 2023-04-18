package Entity.Piece;

import App.Board;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Bishop extends Piece {
    private int white;
    public Bishop(int rank, String file, int white) {
        super(rank, file);
        this.white = white;
    }

    @Override
    public ArrayList<Integer> getPossibleMoves(Board board){
        return getMoves(board);
    }

    private ArrayList<Integer> getMoves(Board board){

        ArrayList<Integer> ret = new ArrayList();
        //int file = this.revdict.get(this.file);
        int[][] moveArray = new int[][]{
                {1}, {3}, {5}, {7}, {9}, {11}
        };

        return generateMovesFromArray(board, moveArray, this.rank, this.file, true, true);
    }

    @Override
    public Image getTexture() {
        String color = "black";
        if (this.white == 1){
            color = "white";
        }
        String file = "src/Images/" + color + "/bishop.png";

        Image image = new ImageIcon(file).getImage();

        return image;
    }
    @Override
    public int isWhite() {
        return white;
    }
}