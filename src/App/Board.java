package App;

import Entity.Piece.*;
import Entity.Tile.Tile;
import Interfaces.IHashMaps;
import Interfaces.IMoves;
import Interfaces.IValidate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class Board extends JPanel implements IMoves {
    private ArrayList<Tile> board;
    public ArrayList<Tile> getBoard() {
        return this.board;
    }
    private int promotion;
    public int whiteTurn;
    public int enPassant;
    private double scale;
    private final JFrame window;
    public void setScale(double scale){
        this.scale = scale;
    }
    private Board getSelf(){
        return this;
    }
    private int clickedIndex = -1;
    private ArrayList<String> moveHistory = new ArrayList<>();
    public ArrayList<String> getMoveHistory() {
        return moveHistory;
    }

    private static ArrayList<Integer> moves = new ArrayList<>();

    public void setPosition(String position, int whiteTurn, int enPassant) {
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
                int color = (this.position.charAt(index) == Character.toUpperCase(this.position.charAt(index)) ? 1 : 0);
                switch (this.position.charAt(index)) {
                    case 'p', 'P' -> tile.setPiece(new Pawn(i, dict.get(j), color));
                    case 'n', 'N' -> tile.setPiece(new Knight(i, dict.get(j), color));
                    case 'b', 'B' -> tile.setPiece(new Bishop(i, dict.get(j), color));
                    case 'r', 'R' -> tile.setPiece(new Rook(i, dict.get(j), color));
                    case 'q', 'Q' -> tile.setPiece(new Queen(i, dict.get(j), color));
                    case 'k', 'K' -> tile.setPiece(new King(i, dict.get(j), color));
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
                        if (this.position.charAt(index + 1) != '0' && this.position.charAt(index +1) != '1'){
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

    public Board(String position, int whiteTurn, int enPassant, JFrame window){
        this.position = position;
        this.scale = 1;
        this.promotion = -1;
        this.window = window;
        this.setPosition(position, whiteTurn, enPassant);
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int startheight = -300;
                int clickedTile = -1;
                if (promotion != -1){
                    int[][] offsets = new int[][] {
                            {(int) (((57.74 + 28.88) * 5 - 5) * scale) + 43, (int) ((450) * scale) + 47}, {(int) (((57.74 + 28.88) * 5 - 5) * scale) + 43*2 + 80, (int) ((450) * scale) + 47},
                            {(int) (((57.74 + 28.88) * 5 - 5) * scale) + 43, (int) ((450) * scale) + 47*2 + 80}, {(int) (((57.74 + 28.88) * 5 - 5) * scale) + 43*2 + 80, (int) ((450) * scale) + 47*2 + 80}
                    };
                    int length = (int) (80 * scale);
                    for(int i = 0; i < 4; i++){
                        int[] offset = offsets[i];
                        if (e.getX() > offset[0] && e.getX() < (offset[0] + length) && e.getY() > offset[1] && e.getY() < offset[1] + length){
                            Piece piececlone = board.get(promotion).getPiece();
                            switch (i) {
                                case 0 -> board.get(promotion).setPiece(new Queen(piececlone.getRank(), piececlone.getFile(), piececlone.isWhite()));
                                case 1 -> board.get(promotion).setPiece(new Rook(piececlone.getRank(), piececlone.getFile(), piececlone.isWhite()));
                                case 2 -> board.get(promotion).setPiece(new Bishop(piececlone.getRank(), piececlone.getFile(), piececlone.isWhite()));
                                case 3 -> board.get(promotion).setPiece(new Knight(piececlone.getRank(), piececlone.getFile(), piececlone.isWhite()));
                            }
                            break;
                        }
                    }

                    promotion = -1;
                    repaint();
                    return;
                }
                for (int i = 0; i < 121; i++) {
                    Tile tile = board.get(i);
                    if(tile == null){
                        continue;
                    }
                    int offset = Board.offset.get(tile.getFile()) * 50;
                    int hoffset = ((int) (57.74 + 28.88)) * revdict.get(tile.getFile());
                    int startx = (int) ((hoffset + 17.5) * scale);
                    int starty = (int) ((startheight + (100 * (12 - tile.getRank())) + offset + 10) * scale);
                    int length = (int) (80 * scale);

                    if (!(e.getX() > startx && e.getX() < startx + length &&
                        e.getY() > starty && e.getY() < starty + length
                    )){continue;}

                    if (clickedIndex == i){
                        moves = new ArrayList<>();
                        for (int j = 0; j < 121; j++) {
                            Tile tile2 = board.get(j);
                            if(tile2 == null){
                                continue;
                            }
                            tile2.setMoveIndicator(false);
                            tile2.setMoveIndicator(false, 1);
                        }
                        clickedIndex = -1;
                        repaint();
                        break;
                    }

                    if(clickedIndex > 0 & board.get(i).getPiece().isWhite() != getSelf().whiteTurn){
                        clickedTile = clickedIndex;
                    }
                    clickedIndex = i;

                    break;

                }
                if(clickedIndex < 0){
                    return;
                }
                if (board.get(clickedIndex).getPiece().isWhite() != getSelf().whiteTurn){
                    if(clickedTile > 0) {
                        if (board.get(clickedIndex).isMoveIndicator()) {
                            movePiece(clickedTile, clickedIndex);
                            //Board.whiteTurn = !Board.whiteTurn;

                        }
                        for (int j = 0; j < 121; j++) {
                            Tile tile2 = board.get(j);
                            if (tile2 == null) {
                                continue;
                            }
                            tile2.setMoveIndicator(false);
                            tile2.setMoveIndicator(false, 1);
                        }
                        clickedIndex = -1;
                        //Board.whiteTurn = !Board.whiteTurn;
                        repaint();
                        return;
                    }
                    clickedIndex = -1;
                    moves = new ArrayList<>();
                    for (int i = 0; i < 121; i++) {
                        Tile tile = board.get(i);
                        if (tile == null) {
                            continue;
                        }
                        tile.setMoveIndicator(false);
                        tile.setMoveIndicator(false, 1);
                    }
                    repaint();
                    return;
                }

                for (int i = 0; i < 121; i++) {
                    Tile tile = board.get(i);
                    if(tile == null){
                        continue;
                    }
                    tile.setMoveIndicator(false);
                    tile.setMoveIndicator(false, 1);
                }
                //System.out.println(board.get(Board.clickedIndex));

                moves = board.get(clickedIndex).getPiece().getPossibleMoves(getSelf());
                if (Board.moves.size() == 0 && board.get(clickedIndex).getPiece().getClass() == King.class){
                    if(findChecks(board, getSelf()).size() != 0) {
                        System.out.println("mate");
                    } else {
                        System.out.println("stalemate");
                    }
                }
                board.get(clickedIndex).setMoveIndicator(true, 1);
                for(int index: moves){
                    Tile tile = board.get(index);

                    if(tile != null){ tile.setMoveIndicator(true);}
                }
                repaint();
                //System.out.println(Board.whiteTurn);

            }

        });

    }

    @Override
    protected void paintComponent(Graphics g) {

        if ((this.window.getSize().getWidth() / this.window.getSize().getHeight()) < (1150.0 / 1200.0)){
            this.scale = this.window.getSize().getWidth() / 1150.0;
        } else {
            this.scale = this.window.getSize().getHeight() / 1200.0;
        }

        setBackground(Color.BLACK);
        int startheight = -300;
        super.paintComponent(g);


        for (Tile tile : board) {
            if (tile == null) {
                continue;
            }
            int offset = this.offset.get(tile.getFile()) * 50;
            int hoffset = ((int) (57.74 + 28.88)) * revdict.get(tile.getFile());


            g.drawImage(tile.getTexture(), (int) (hoffset * this.scale), (int) ((startheight + (100 * (12 - tile.getRank())) + offset) * this.scale), (int) (115 * this.scale), (int) (100 * this.scale), this);if (tile.getPiece() != null) {
                g.drawImage(tile.getPiece().getTexture(), (int) ((hoffset + 17.5) * this.scale), (int) ((startheight + (100 * (12 - tile.getRank())) + offset + 10) * this.scale), (int) (80 * this.scale), (int) (80 * this.scale), this);
            }
            g.drawImage(tile.getMoveIndicatorTexture(), (int) ((hoffset + 42.5) * this.scale), (int) ((startheight + (100 * (12 - tile.getRank())) + offset + 35) * this.scale), (int) (30 * this.scale), (int) (30 * this.scale), this);


            //g.drawImage(hex, 100, 100,   this);
        }
        if(this.promotion != -1){
            promotePiece(g, this);
        }
        ArrayList<Integer> moves = getMovesByPlayer(this, whiteTurn);
        if (moves.size() == 0) {
//            JLabel wintext = new JLabel("Ktoś wygrał!");
//            wintext.setFont(new Font("Sans", Font.PLAIN, 40));
            window.dispose();
            new App(1, 1 - whiteTurn, findChecks(board, this).size() > 0);
        }
    }

    private void movePiece(int origin, int destination){

        Piece dest = this.board.get(destination).getPiece();
        int destRank = dest.getRank();
        String destFile = dest.getFile();
        Piece orig = this.board.get(origin).getPiece();
        int origRank = orig.getRank();
        String origFile = orig.getFile();

        if(destination == this.enPassant && orig.getClass() == Pawn.class){
            this.board.get(destination + ((orig.isWhite() == 1) ? 11 : -11)).setPiece(new Empty(destRank + ((orig.isWhite() == 1) ? 1 : -1), destFile));
        }
        if(orig.getClass() == Pawn.class && ((dest.getRank() == 1 && orig.isWhite() == 0) || ((
                dest.getRank() == 6 && (dest.getFile().equals("a") || dest.getFile().equals("l")) ||
                dest.getRank() == 7 && (dest.getFile().equals("b") || dest.getFile().equals("k")) ||
                dest.getRank() == 8 && (dest.getFile().equals("c") || dest.getFile().equals("i")) ||
                dest.getRank() == 9 && (dest.getFile().equals("d") || dest.getFile().equals("h")) ||
                dest.getRank() == 10 && (dest.getFile().equals("e") || dest.getFile().equals("g")) ||
                dest.getRank() == 11 && dest.getFile().equals("f")) && orig.isWhite() == 1))){
            this.promotion = destination;
        }

        this.board.get(origin).setPiece(new Empty(origRank, origFile));
        orig.setFile(destFile);
        orig.setRank(destRank);
        this.board.get(destination).setPiece(orig);
        if(orig.getClass() == Pawn.class && Math.abs(origin - destination) == 22){
            //System.out.println(Math.abs(origin - destination));
            this.enPassant = origin + 11;
            if (orig.isWhite() == 1){
                this.enPassant = origin - 11;
            }
        } else {
            this.enPassant = -1;
        }

        this.moveHistory.add(getBoardString());

        this.whiteTurn = 1 - this.whiteTurn;
    }

    private String getBoardString(){
        StringBuilder ret = new StringBuilder();
        for (Tile tile : this.board) {
            if (tile == null) {
                ret.append("null ");
                continue;
            }
            Piece piece = tile.getPiece();
            if (piece.getClass() == Empty.class) {
                ret.append("Empty ");
                continue;
            }
            if (piece.isWhite() == 1) {
                ret.append("W");
            } else {
                ret.append("B");
            }
            if (piece.getClass() == Pawn.class) {
                ret.append("Pawn ");
            } else if (piece.getClass() == Knight.class) {
                ret.append("Knight ");
            } else if (piece.getClass() == Bishop.class) {
                ret.append("Bishop ");
            } else if (piece.getClass() == Rook.class) {
                ret.append("Rook ");
            } else if (piece.getClass() == Queen.class) {
                ret.append("Queen ");
            } else {
                ret.append("King ");
            }
        }

        return ret.toString();
    }

    private void promotePiece(Graphics g, Board b){
        //int offset = this.offset.get("e") * 50;
        g.setColor(Color.RED);
        g.drawRect((int) (((57.74 + 28.88) * 5 - 5) * this.scale) - 1, (int) ((450) * this.scale) - 1, 290, 302);
        g.setColor(Color.GRAY);
        g.fillRect((int) (((57.74 + 28.88) * 5 - 5) * this.scale), (int) ((450) * this.scale), 288, 300);
        g.setColor(Color.GRAY);
        int color = this.promotion > 70 ? 0 : 1;


        Piece[] pieces = new Piece[]{new Queen(-1, "0", color), new Rook(-1, "0", color), new Bishop(-1, "0", color), new Knight(-1, "0", color)};

        Image[] images = new Image[]{pieces[0].getTexture(), pieces[1].getTexture(), pieces[2].getTexture(), pieces[3].getTexture()};
        int[][] offsets = new int[][] {
                {(int) (((57.74 + 28.88) * 5 - 5) * this.scale) + 43, (int) ((450) * this.scale) + 47}, {(int) (((57.74 + 28.88) * 5 - 5) * this.scale) + 43*2 + 80, (int) ((450) * this.scale) + 47},
                {(int) (((57.74 + 28.88) * 5 - 5) * this.scale) + 43, (int) ((450) * this.scale) + 47*2 + 80}, {(int) (((57.74 + 28.88) * 5 - 5) * this.scale) + 43*2 + 80, (int) ((450) * this.scale) + 47*2 + 80}
        };
        for(int i = 0; i < images.length; i++){
            g.drawImage(images[i], offsets[i][0], offsets[i][1], (int) (80 * this.scale), (int) (80 * this.scale), b);
        }
    }
}

