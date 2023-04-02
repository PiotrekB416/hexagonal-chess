package App;

import Entity.Piece.*;
import Entity.Tile.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class Board extends JPanel {
    public static ArrayList<Tile> board;
    public static boolean whiteTurn;
    public static int enPassant;
    private static final HashMap<Integer, String> dict = new HashMap<Integer, String>(){
        {
            put(1, "a"); put(2, "b"); put(3, "c"); put(4, "d"); put(5, "e"); put(6, "f");
            put(7, "g"); put(8, "h"); put(9, "i"); put(10, "k"); put(11, "l");
        }
    };
    private static final HashMap<String, Integer> revdict = new HashMap<String, Integer>(){
        {
            put("a", 1); put("b", 2); put("c", 3); put("d", 4); put("e", 5); put("f", 6);
            put("g", 7); put("h", 8); put("i", 9); put("k", 10); put("l", 11);
        }
    };
    private static final HashMap<String, Integer> offset = new HashMap<String, Integer>(){
        {
            put("a", 0); put("b", 1); put("c", 2); put("d", 3); put("e", 4); put("f", 5);
            put("l", 0); put("k", 1); put("i", 2); put("h", 3); put("g", 4);
        }
    };
    private static double scale;


    public String getPosition() {
        return position;
    }

    public void setPosition(String position, boolean whiteTurn, int enPassant) {
        this.whiteTurn = whiteTurn;
        this.enPassant = enPassant;
        this.position = position;
        int size = 11;
        board = new ArrayList<>();
        int index = 0;
        int wait = 0;
        for (int i = size; i > 0; i--){
            int start = 1;
            int len = size;
            if (i > ((size + 1) / 2)){
                start = i - ((size - 1) / 2);
                len = (size - i) * 2 + 1;
            }
            for (int j = 1; j < start; j++){
                    board.add(null);
            }
            for (int j = start; j < len + start; j++){
                Tile tile = new Tile(i, dict.get(j), new Empty(i, dict.get(j)));

                if (wait > 0){
                    tile.setPiece(new Empty(i, dict.get(j)));
                    wait--;
                    board.add(tile);
                    continue;
                }
                boolean color = (this.position.charAt(index) == Character.toUpperCase(this.position.charAt(index)));
                switch (this.position.charAt(index)) {
                    case 'p', 'P' -> {
                        tile.setPiece(new Pawn(i, dict.get(j), color));
                    }
                    case 'n', 'N' -> {
                        tile.setPiece(new Knight(i, dict.get(j), color));
                    }
                    case 'b', 'B' -> {
                        tile.setPiece(new Bishop(i, dict.get(j), color));
                    }
                    case 'r', 'R' -> {
                        tile.setPiece(new Rook(i, dict.get(j), color));
                    }

                    case '/' -> {
                        index++;
                        j--;
                        continue;
                    }
                    case '2', '3', '4', '5', '6', '7', '8', '9' -> {
                        wait = Integer.parseInt(String.valueOf(this.position.charAt(index))) -1;
                    }
                    case '1' -> {
                        if (this.position.length() == (index +1)){
                            break;
                        }
                        if (this.position.charAt(index +1) != '0' && this.position.charAt(index +1) != '1'){
                            break;
                        }
                        wait = Integer.parseInt(String.valueOf(this.position.charAt(index)) + this.position.charAt(index +1)) -1;
                        index++;
                    }
                }

                board.add(tile);
                index++;
            }

            for (int j = len + start; j <= size; j++){
                board.add(null);
            }
        }
    }

    private String position;

    public Board(String position, boolean whiteTurn, int enPassant){
        this.position = position;
        Board.scale = 1;
        this.setPosition(position, whiteTurn, enPassant);
        System.out.println(board);
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int startheight = -300;
                int clicked_index = -1;
                for (int i = 0; i < 121; i++) {
                    Tile tile = board.get(i);
                    if(tile == null){
                        continue;
                    }
                    int offset = Board.offset.get(tile.getFile()) * 50;
                    int hoffset = ((int) (57.74 + 28.88)) * revdict.get(tile.getFile());
                    int startx = (int) ((hoffset + 17.5) * Board.scale);
                    int starty = (int) ((startheight + (100 * (12 - tile.getRank())) + offset + 10) * Board.scale);
                    int length = (int) (80 * Board.scale);
                    if (e.getX() > startx && e.getX() < startx + length &&
                        e.getY() > starty && e.getY() < starty + length
                    ){
                        clicked_index = i;
                        break;

                    }
                }
                if(clicked_index < 0){
                    return;
                }
                for (int i = 0; i < 121; i++) {
                    Tile tile = board.get(i);
                    if(tile == null){
                        continue;
                    }
                    tile.setMoveIndicator(false);
                }
                for(int index: board.get(clicked_index).getPiece().getPossibleMoves()){
                    Tile tile = board.get(index);

                    if(tile != null){ tile.setMoveIndicator(true);}
                }
                repaint();

            }

        });

    }

    @Override
    protected void paintComponent(Graphics g) {
        setBackground(Color.BLACK);
        int startheight = -300;
        super.paintComponent(g);


        for (Tile tile : board) {
            if(tile == null){
                continue;
            }
            int offset = Board.offset.get(tile.getFile()) * 50;
            int hoffset = ((int) (57.74 + 28.88)) * revdict.get(tile.getFile());


            g.drawImage(tile.getTexture(), (int) (hoffset * Board.scale), (int) ((startheight + (100 * (12 - tile.getRank())) + offset) * Board.scale), (int) (115 * Board.scale), (int) (100 * Board.scale), this);
            if (tile.getPiece() != null) {
                g.drawImage(tile.getPiece().getTexture(), (int) ((hoffset + 17.5) * this.scale), (int) ((startheight + (100 * (12 - tile.getRank())) + offset + 10) * Board.scale), (int) (80 * Board.scale), (int) (80 * Board.scale), this);
            }
            g.drawImage(tile.getMoveIndicatorTexture(), (int) ((hoffset + 42.5) * Board.scale), (int) ((startheight + (100 * (12 - tile.getRank())) + offset + 35) * Board.scale), (int) (30 * Board.scale), (int) (30 * Board.scale), this);


            //g.drawImage(hex, 100, 100,   this);
        }

    }
}
