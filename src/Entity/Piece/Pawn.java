package Entity.Piece;

import Entity.Entity;

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
    private boolean white;
    public Pawn(int rank, String file, boolean white){
        super(rank, file);
        this.white = white;
    }


    @Override
    public ArrayList<Integer> getPossibleMoves(){
        if (this.white){
            return this.getWhiteMoves();
        }
        return getBlackMoves();
    }

    private ArrayList<Integer> getWhiteMoves(){
        ArrayList<Integer> ret = new ArrayList();
        int[] orgpos = new int[]{
                0, 0, 1, 2, 3, 4, 5, 4, 3, 2, 1
        };
        int file = super.revdict.get(this.file);

        int[][] moveArray = new int[][]{
                {0}, {2}, {10}
        };
        if (orgpos[file] == this.rank){
            //ret.add((this.rank + 2) * 11 + file + 0);
            moveArray = new int[][]{
                    {0, 0}, {0}, {2}, {10}
            };
        }
        return generateMovesFromArray(moveArray, this.rank, this.file, false, true);
    }
    private ArrayList<Integer> getBlackMoves(){
        ArrayList<Integer> ret = new ArrayList();

        int file = this.revdict.get(this.file);

        int[][] moveArray = new int[][]{
                {6}, {4}, {8}
        };
        if (this.rank == 7){
            //ret.add((this.rank + 2) * 11 + file + 0);
            moveArray = new int[][]{
                    {6, 6}, {6}, {4}, {8}
            };
        }
        return generateMovesFromArray(moveArray, this.rank, this.file, false, true);


    }
    @Override
    public Image getTexture() {
        String color = "black";
        if (this.white){
            color = "white";
        }
        String file = "src/Images/" + color + "/pawn.png";

        Image image = new ImageIcon(file).getImage();

        return image;
    }
    @Override
    public boolean isWhite() {
        return white;
    }
}
