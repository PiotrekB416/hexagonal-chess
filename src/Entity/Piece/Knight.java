package Entity.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Knight extends Piece{
    private int white;
    public Knight(int rank, String file, int white){
        super(rank, file);
        this.white = white;
    }
    public ArrayList<Integer> getPossibleMoves(){
        return getMoves();
    }
    private ArrayList<Integer> getMoves(){
        // {rank, file}
        int[][] moveArray = {{1, 0}, {1, 2}, {3, 2}, {3, 4}, {5, 4}, {5, 6}, {7, 6}, {7, 8}, {9, 8}, {9, 10}, {11, 10}, {11, 0}};
        return generateMovesFromArray(moveArray, this.rank, this.file, false, true);
    }

    @Override
    public Image getTexture() {
        String color = "black";
        if (this.white == 1){
            color = "white";
        }
        String file = "src/Images/" + color + "/knight.png";

        Image image = new ImageIcon(file).getImage();

        return image;
    }
    @Override
    public int isWhite() {
        return white;
    }

}