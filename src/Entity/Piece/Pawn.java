package Entity.Piece;

import App.Board;
import Entity.Entity;
import Interfaces.IMoves;

import javax.imageio.ImageIO;
import javax.imageio.ImageTranscoder;
import javax.imageio.ImageTypeSpecifier;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Pawn extends Piece {
    private int white;
    public Pawn(int rank, String file, int white){
        super(rank, file);
        this.white = white;
    }


    @Override
    public ArrayList<Integer> getPossibleMoves(Board board){
        if (this.white == 1){
            return this.getWhiteMoves(board);
        }
        return getBlackMoves(board);
    }

    private ArrayList<Integer> getWhiteMoves(Board board){
        int[] orgpos = new int[]{
                0, 0, 1, 2, 3, 4, 5, 4, 3, 2, 1
        };
        int file = super.revdict.get(this.file);

        int[][] moveArray = new int[][]{
                {0}, {2}, {10}
        };

        int test = generateMovesFromArray(board, new int[][]{{0}}, this.rank, this.file, false, true).size();
        if (orgpos[file] == this.rank && test != 0){
            //ret.add((this.rank + 2) * 11 + file + 0);
            moveArray = new int[][]{
                    {0, 0}, {0}, {2}, {10}
            };
        }
        return generateMovesFromArray(board, moveArray, this.rank, this.file, false, true);
    }
    private ArrayList<Integer> getBlackMoves(Board board){
        ArrayList<Integer> ret = new ArrayList();

        int file = this.revdict.get(this.file);

        int[][] moveArray = new int[][]{
                {6}, {4}, {8}
        };

        int test = generateMovesFromArray(board, new int[][]{{6}}, this.rank, this.file, false, true).size();
        if (this.rank == 7 && test != 0){
            //ret.add((this.rank + 2) * 11 + file + 0);
            moveArray = new int[][]{
                    {6, 6}, {6}, {4}, {8}
            };
        }

        return generateMovesFromArray(board, moveArray, this.rank, this.file, false, true);


    }
    @Override
    public Image getTexture() {
        String color = "black";
        if (this.white == 1){
            color = "white";
        }
        String file = "src/Images/" + color + "/pawn.png";

        Image image = new ImageIcon(file).getImage();

        return image;
    }
    @Override
    public int isWhite() {
        return white;
    }
}
