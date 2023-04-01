package Entity.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Rook extends Piece {
    private boolean white;
    public Rook(int rank, String file, boolean white) {
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
                {1, 0}, {-1, 0}, {0, 1}, {0, -1}, {-1, -1}, {-1, 1}
        };

//        int[] distance = new int[]{
//                11 - this.rank, this.rank - 1, 11 - this.revdict.get(this.file), this.revdict.get(this.file) - 1
//        };
//
//        for(int index = 0; index < distance.length; index++){
//            for(int i = 0; i < distance[index]; i++){
//                ret.add((this.rank + (moves[index][0] * i)) * 11 + file + (moves[index][1] * i));
//            }
//        }
//
//        for(int i = 0; i < 2; i++){
//            boolean repeat = true;
//            int index = 5 + i;
//            while(repeat){
//                ret.add((this.rank + (moves[index][0] * i)) * 11 + file + (moves[index][1] * i));
//                int afterrank = (this.rank + (moves[index][0] * i)) * 11;
//                int afterfile =  file + (moves[index][1] * i);
//                if (afterrank == 1 || (afterfile == 1 || afterfile == 11)){
//                    repeat = false;
//                }
//            }
//        }
//
//        return ret;
        return generateMovesFromArray(moveArray, this.rank, this.file);
    }

    @Override
    public Image getTexture() {
        String color = "black";
        if (this.white){
            color = "white";
        }
        String file = "src/Images/" + color + "/rook.png";

        Image image = new ImageIcon(file).getImage();

        return image;
    }
}