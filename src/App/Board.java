package App;

import Entity.Piece.*;
import Entity.Tile.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

public class Board extends JPanel {
    public ArrayList<ArrayList<Tile>> board;
    public Piece[] pieces;
    private static HashMap<Integer, String> dict = new HashMap(){
        {
            put(1, "a"); put(2, "b"); put(3, "c"); put(4, "d"); put(5, "e"); put(6, "f");
            put(7, "g"); put(8, "h"); put(9, "i"); put(10, "k"); put(11, "l");
        }
    };
    private static HashMap<String, Integer> revdict = new HashMap(){
        {
            put("a", 1); put("b", 2); put("c", 3); put("d", 4); put("e", 5); put("f", 6);
            put("g", 7); put("h", 8); put("i", 9); put("k", 10); put("l", 11);
        }
    };
    private static HashMap<String, Integer> offset = new HashMap(){
        {
            put("a", 0); put("b", 1); put("c", 2); put("d", 3); put("e", 4); put("f", 5);
            put("l", 0); put("k", 1); put("i", 2); put("h", 3); put("g", 4);
        }
    };
    private static double scale;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
        int size = 11;
        board = new ArrayList();
        //pieces = new Empty[121];;
        int index = 0;
        int wait = 0;
        for (int i = size; i > 0; i--){
            int start = 1;
            int len = size;
            if (i > ((size + 1) / 2)){
                start = i - ((size - 1) / 2);
                len = (size - i) * 2 + 1;
            }
            ArrayList<Tile> line = new ArrayList();
            for (int j = start; j < len + start; j++){
                Tile tile = new Tile(i, dict.get(j), new Empty(i, dict.get(j)));

                if (wait > 0){
                    tile.setPiece(new Empty(i, dict.get(j)));
                    wait--;
                    line.add(tile);
                    continue;
                }

                switch (this.position.charAt(index)) {
                    case 'p', 'P' -> {
                        boolean color = (this.position.charAt(index) == Character.toUpperCase(this.position.charAt(index)));
                        tile.setPiece(new Pawn(i, dict.get(j), color));
                    }
                    case 'r', 'R' -> {
                        boolean color = (this.position.charAt(index) == Character.toUpperCase(this.position.charAt(index)));
                        tile.setPiece(new Rook(i, dict.get(j), color));
                    }
                    case '/' -> {
                        index++;
                        j--;
                        continue;
                    }
                    case '2', '3', '4', '5', '6', '7', '8', '9' -> {
                        wait = Integer.parseInt(this.position.charAt(index) + "") -1;
                    }
                    case '1' -> {
                        if (this.position.length() == (index +1)){
                            break;
                        }
                        if (this.position.charAt(index +1) != '0' && this.position.charAt(index +1) != '1'){
                            break;
                        }
                        wait = Integer.parseInt(this.position.charAt(index) + "" + this.position.charAt(index +1)) -1;
                        index++;
                    }
                }

                line.add(tile);
                index++;
            }
            board.add(line);
        }
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
    }

    private String position;

    public Board(String position){
        this.position = position;
        this.scale = 1;
        this.setPosition(position);

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int startheight = -300;
                for (ArrayList<Tile> line : board) {

                    for (Tile tile : line) {
                        int offset = Board.offset.get(tile.getFile()) * 50;
                        int hoffset = ((int) (57.74 + 28.88)) * revdict.get(tile.getFile());
                        int startx = (int) ((hoffset + 17.5) * Board.scale);
                        int starty = (int) ((startheight + (100 * (12 - tile.getRank())) + offset + 10) * Board.scale);
                        int length = (int) (80 * Board.scale);
                        if (e.getX() > startx && e.getX() < startx + length &&
                            e.getY() > starty && e.getY() < starty + length
                        ){
                            System.out.println(tile.getPiece());
                        }
                    }
                }
            }

        });

    }

    @Override
    protected void paintComponent(Graphics g) {
        setBackground(Color.BLACK);
        int startheight = -300;
        super.paintComponent(g);
        for (ArrayList<Tile> line : board) {

            for (Tile tile : line) {
                int offset = this.offset.get(tile.getFile()) * 50;
                int hoffset = ((int) (57.74 + 28.88)) * revdict.get(tile.getFile());


                g.drawImage(tile.getTexture(), (int) (hoffset * this.scale), (int) ((startheight + (100 * (12 - tile.getRank())) + offset) * this.scale), (int) (115 * this.scale), (int) (100 * this.scale), this);
                if (tile.getPiece() != null) {

                    g.drawImage(tile.getPiece().getTexture(), (int) ((hoffset + 17.5) * this.scale), (int) ((startheight + (100 * (12 - tile.getRank())) + offset + 10) * this.scale), (int) (80 * this.scale), (int) (80 * this.scale), this);
                }

                //g.drawImage(hex, 100, 100,   this);
            }
        }
    }
}
