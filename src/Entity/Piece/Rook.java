package Entity.Piece;

import App.Board;
import Images.Images;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Rook extends Piece {

    private int white;
    public Rook(int rank, String file, int white) {
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
                {0}, {2}, {4}, {6}, {8}, {10}
        };

        return generateMovesFromArray(board, moveArray, this.rank, this.file, true, true);
    }

    @Override
    public Image getTexture() {
        String color = "black";
        if (this.white == 1){
            color = "white";
        }
        String file = color + "/rook.png";

        Image image = new ImageIcon(Images.class.getResource(file)).getImage();

        return image;
    }
    @Override
    public int isWhite() {
        return white;
    }
}
