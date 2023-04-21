package Entity.Piece;


import Entity.Entity;
import Interfaces.IMoves;
import Interfaces.IValidate;
import javax.swing.*;
import java.awt.*;

public class Piece extends Entity implements IValidate, IMoves {
    public Piece(int rank, String file){
        super(rank, file);
    }

    public Image getTexture(){
        return new ImageIcon().getImage();
    }

    public int isWhite() {
        return -1;
    }

}
