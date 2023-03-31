package App;

import Entity.Piece.*;
import Entity.Tile.Tile;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Board extends JPanel {
    public ArrayList<ArrayList<Tile>> board;
    public Piece[] pieces;
    private HashMap<Integer, String> dict = new HashMap<Integer, String>(){
        {
            put(1, "a"); put(2, "b"); put(3, "c"); put(4, "d"); put(5, "e"); put(6, "f");
            put(7, "g"); put(8, "h"); put(9, "i"); put(10, "k"); put(11, "l");
        }
    };
    private HashMap<String, Integer> revdict = new HashMap(){
        {
            put("a", 1); put("b", 2); put("c", 3); put("d", 4); put("e", 5); put("f", 6);
            put("g", 7); put("h", 8); put("i", 9); put("k", 10); put("l", 11);
        }
    };
    private HashMap<String, Integer> offset = new HashMap<String, Integer>(){
        {
            put("a", 0); put("b", 1); put("c", 2); put("d", 3); put("e", 4); put("f", 5);
            put("l", 0); put("k", 1); put("i", 2); put("h", 3); put("g", 4);
        }
    };
    private double scale;
    public Board(){
        this.scale = 1;
        int size = 11;
        board = new ArrayList();
        pieces = new Empty[121];;
        for (int i = size; i > 0; i--){
            int start = 1;
            int len = size;
            if (i > ((size + 1) / 2)){
                start = i - ((size - 1) / 2);
                len = (size - i) * 2 + 1;
            }
            ArrayList<Tile> line = new ArrayList();
            for (int j = start; j < len + start; j++){
                Tile tile = new Tile(i, dict.get(j));

                line.add(tile);
            }
            board.add(line);
        }
        for (int i = 0; i < 11; i++){
            for (int j = 0; j < 11; j++){
                pieces[i * 11 + j] = new Empty(i, dict.get(j));
            }

        }
        for (int i = ((size - 1) / 2); i > 0; i--){
            int offset = 11 * (((size - 1) / 2) - i);
            for (int j = 0; j < i; j++){
//                pieces[offset + j] = new Piece(i + ((size - 1) / 2), (String)dict.get(j), -1, "none");
//                pieces[offset + (size - 1 - j)] = new Piece(i + ((size - 1) / 2), (String)dict.get(size - 1 - j), -1, "none");
                pieces[offset + j] = null;
                pieces[offset + (size - 1 - j)] = null;

            }

        }

    }

    @Override
    protected void paintComponent(Graphics g){
        setBackground(Color.BLACK);
        int height = -230;
        super.paintComponent(g);
        for (ArrayList<Tile> line: board){

            for (Tile tile: line){
                int offset = this.offset.get(tile.getFile()) * 50;
                int hoffset = ((int)(57.74 + 28.88)) * revdict.get(tile.getFile());


                g.drawImage(tile.getTexture(), (int)(hoffset * this.scale),(int)((height + offset) * this.scale), (int)(115 * this.scale), (int)(100 * this.scale),this);
                //g.drawImage(hex, 100, 100,   this);
            }
            height += 100;
        }
    }
}

//package App;
//
//import Entity.Piece.Empty;
//import Entity.Piece.Piece;
//import Entity.Tile.Tile;
//
//import javax.swing.*;
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public class Board extends JPanel {
//    public ArrayList<Tile> board = new ArrayList<Tile>();
//    public Piece[] pieces;
//    private HashMap<Integer, String> dict = new HashMap<Integer, String>(){
//        {
//            put(1, "a"); put(2, "b"); put(3, "c"); put(4, "d"); put(5, "e"); put(6, "f");
//            put(7, "g"); put(8, "h"); put(9, "i"); put(10, "k"); put(11, "l");
//        }
//    };
//    private HashMap<String, Integer> revdict = new HashMap(){
//        {
//            put("a", 1); put("b", 2); put("c", 3); put("d", 4); put("e", 5); put("f", 6);
//            put("g", 7); put("h", 8); put("i", 9); put("k", 10); put("l", 11);
//        }
//    };
//    private HashMap<String, Integer> offset = new HashMap<String, Integer>(){
//        {
//            put("a", 0); put("b", 1); put("c", 2); put("d", 3); put("e", 4); put("f", 5);
//            put("l", 0); put("k", 1); put("i", 2); put("h", 3); put("g", 4);
//        }
//    };
//    public Board(){
//
//        int size = 11;
//
//        pieces = new Piece[121];
////        for (int i = size; i > 0; i--){
////            int start = 1;
////            int len = size;
////            if (i > ((size + 1) / 2)){
////                start = i - ((size - 1) / 2);
////                len = (size - i) * 2 + 1;
////            }
////            ArrayList<ArrayList<Object>> line = new ArrayList();
////            for (int j = start; j < len + start; j++){
////                ArrayList<Object> pos = new ArrayList();
////                pos.add(i);
////                pos.add(dict.get(j));
////                line.add(pos);
////            }
////            board.add(line);
////        }
//        for (int i = 0; i < 11; i++){
//            for (String file : revdict.keySet()){
//                if (i > 6) {
//                    int redundant = i - 6;
//                    if (revdict.get(file) < redundant || revdict.get(file) >  11 - redundant) {
//                        this.board.add(null);
//                        continue;
//                    }
//                }
//                this.board.add(new Tile(i + 1, file));
//                System.out.println(file + i);
//            }
//        }
//        for (int i = 0; i < 11; i++){
//            for (int j = 0; j < 11; j++){
//                pieces[i * 11 + j] = new Empty(i, dict.get(j));
//            }
//
//        }
//        for (int i = ((size - 1) / 2); i > 0; i--){
//            int offset = 11 * (((size - 1) / 2) - i);
//            for (int j = 0; j < i; j++){
////                pieces[offset + j] = new Piece(i + ((size - 1) / 2), (String)dict.get(j), -1, "none");
////                pieces[offset + (size - 1 - j)] = new Piece(i + ((size - 1) / 2), (String)dict.get(size - 1 - j), -1, "none");
//                pieces[offset + j] = null;
//                pieces[offset + (size - 1 - j)] = null;
//
//            }
//
//        }
//
//    }
//
//    @Override
//    protected void paintComponent(Graphics g){
//        super.paintComponent(g);
//        setBackground(Color.BLACK);
//        int windowoffset = -200;
//        //int height = -200;
//        int i = 0;
//        while (i < this.board.size()){
//            if (this.board.get(i) != null){
//                Tile x = board.get(i);
////                int width = (int)(x.getTexture().getWidth(this) * 0.25);
////                int height = (int)(x.getTexture().getHeight(this) * 0.25);
//                int height = 100;
//                int width = 115;
//                //int offset = this.offset.get(pos.get(1)) * 50;
//                //int hoffset = ((int)(57.74 + 28.88)) * revdict.get(pos.get(1));
//                //g.drawImage(hex, (int)(hoffset * this.scale),(int)((height + offset) * this.scale), (int)(115 * this.scale), (int)(100 * this.scale),this);
//                //g.drawImage(x.getTexture(), width / 2 * revdict.get(x.getFile()) ,this.offset.get(x.getFile()) * 50, width, height,this);
//                g.drawImage(x.getTexture(), (int)(57.74 + 28.88) * revdict.get(x.getFile()) ,windowoffset + (this.offset.get(x.getFile()) * 50), width, height,this);
//            }
//            i++;
//            windowoffset += 100;
//        }
//
//
////        for (ArrayList<ArrayList<Object>> line: board){
////
////            for (ArrayList<Object> pos: line){
////                int offset = this.offset.get(pos.get(1)) * 50;
////                int hoffset = ((int)(57.74 + 28.88)) * revdict.get(pos.get(1));
////                Image hex = new ImageIcon("src/Images/brown.png").getImage();
////                switch ((height + offset) % 150){
////                    case 0:{
////                        hex = new ImageIcon("src/Images/light.png").getImage();
////                    }; break;
////                    case 50: {
////                        hex = new ImageIcon("src/Images/dark.png").getImage();
////                    }; break;
////                }
////
////                g.drawImage(hex, hoffset,height + offset, 115, 100,this);
////                //g.drawImage(hex, 100, 100,   this);
////            }
////            height += 100;
////        }
////    }
//    }
//}
//
