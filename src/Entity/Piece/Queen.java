package Entity.Piece;

import App.Board;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Queen extends Piece {
    private int white;
    public Queen(int rank, String file, int white) {
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
                {0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9}, {10}, {11}
        };

        return generateMovesFromArray(board, moveArray, this.rank, this.file, true, true);
    }

    @Override
    public Image getTexture() {
        String color = "black";
        if (this.white == 1){
            color = "white";
        }
        String file = "src/Images/" + color + "/queen.png";

        Image image = new ImageIcon(file).getImage();

        return image;
    }
    @Override
    public int isWhite() {
        return white;
    }
}