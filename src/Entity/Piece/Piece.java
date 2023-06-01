package Entity.Piece;


import App.Board;
import Entity.Entity;
import Interfaces.IDrawable;
import Interfaces.IMoves;
import Interfaces.IValidate;
import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public abstract class Piece extends Entity implements IValidate, IMoves, IDrawable {
    public Piece(int rank, String file){
        super(rank, file);
    }

    public Image getTexture(){
        return new ImageIcon().getImage();
    }

    public int isWhite() {
        return -1;
    }

    @Override
    public void draw(Graphics g, double scale, ImageObserver observer) {
        int offset = this.offset.get(this.getFile()) * 50;
        int hoffset = ((int) (57.74 + 28.88)) * revdict.get(this.getFile());

        g.drawImage(this.getTexture(), (int) ((hoffset + 17.5) * scale), (int) ((startheight + (100 * (12 - this.rank)) + offset + 10) * scale), (int) (80 * scale), (int) (80 * scale), observer);
    }
}
