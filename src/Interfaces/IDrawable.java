package Interfaces;

import java.awt.*;
import java.awt.image.ImageObserver;

public interface IDrawable extends IHashMaps {
    int startheight = -300;
    void draw(Graphics g, double scale, ImageObserver observer);
}