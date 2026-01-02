package App;

import Entity.Piece.*;
import Entity.Tile.Tile;
import Images.Images;
import Interfaces.IMoves;

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
    private Promotion[] promotionPieces;
    private final App window;
    private Board getSelf(){
        return this;
    }
    private int clickedIndex = -1;
    private final ArrayList<String> moveHistory = new ArrayList<>();
    public ArrayList<String> getMoveHistory() {
        return moveHistory;
    }
    private static ArrayList<Integer> moves = new ArrayList<>();
    private String position;
    private MouseAdapter exitAdapter;
    private void parseFen() {
        int size = 11;
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
                    case '2', '3', '4', '5', '6', '7', '8', '9' -> wait = Integer.parseInt(String.valueOf(this.position.charAt(index))) -1;
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

    public void setPosition(String position, int whiteTurn, int enPassant) {
        this.whiteTurn = whiteTurn;
        this.enPassant = enPassant;
        this.position = position;

        board = new ArrayList<>();
        this.parseFen();
    }

    public Board(String position, int whiteTurn, int enPassant, App window){
//        super();

        int width = 1400;
        int height = 1200;

        this.setSize(new Dimension(width, height));
        this.position = position;

        if ((this.getHeight() / this.getWidth()) > width/height) {
            this.scale = (this.getHeight() / (double)width);
        } else {
            this.scale = (this.getWidth() / (double)height);
        }
        this.promotion = -1;
        this.window = window;
        this.setPosition(position, whiteTurn, enPassant);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int clickedTile = -1;
                Point p = new Point(e.getX(), e.getY());
                if (promotion != -1){
                    for(int i = 0; i < promotionPieces.length; i++){
                        if (promotionPieces[i].getPolygon().contains(p)){
                            Piece piececlone = board.get(promotion).getPiece();
                            switch (i) {
                                case 0 -> board.get(promotion).setPiece(new Queen(piececlone.getRank(), piececlone.getFile(), piececlone.isWhite()));
                                case 1 -> board.get(promotion).setPiece(new Rook(piececlone.getRank(), piececlone.getFile(), piececlone.isWhite()));
                                case 2 -> board.get(promotion).setPiece(new Bishop(piececlone.getRank(), piececlone.getFile(), piececlone.isWhite()));
                                case 3 -> board.get(promotion).setPiece(new Knight(piececlone.getRank(), piececlone.getFile(), piececlone.isWhite()));
                            }
                            promotion = -1;
                            break;
                        }
                    }
                    repaint();
                    return;
                }
                for (int i = 0; i < 121; i++) {
                    Tile tile = board.get(i);
                    if(tile == null){
                        continue;
                    }
                    if (!(tile.getPolygon().contains(p))) {
                        continue;
                    }

                    if (clickedIndex == i){
                        for (int j = 0; j < 121; j++) {
                            Tile tile2 = board.get(j);
                            if(tile2 == null){
                                continue;
                            }
                            tile2.getIndicator().setIndicator(false);
                            tile2.getIndicator().setIndicator(false, 1);
                        }
                        clickedIndex = -1;
                        repaint();
                        break;
                    }

                    if(clickedIndex > 0 & board.get(i).getPiece().isWhite() != getSelf().whiteTurn){
                        clickedTile = clickedIndex;
                    }
                    clickedIndex = i;
                    repaint();
                    break;

                }
                if(clickedIndex < 0){
                    return;
                }
                if (board.get(clickedIndex).getPiece().isWhite() != getSelf().whiteTurn){
                    if(clickedTile > 0) {
                        if (board.get(clickedIndex).getIndicator().getIndicator(0)) {
                            movePiece(clickedTile, clickedIndex);
                        }
                        for (int j = 0; j < 121; j++) {
                            Tile tile2 = board.get(j);
                            if (tile2 == null) {
                                continue;
                            }
                            tile2.getIndicator().setIndicator(false);
                            tile2.getIndicator().setIndicator(false, 1);
                        }
                        clickedIndex = -1;
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
                        tile.getIndicator().setIndicator(false);
                        tile.getIndicator().setIndicator(false, 1);
                    }
                    repaint();
                    return;
                }

                for (int i = 0; i < 121; i++) {
                    Tile tile = board.get(i);
                    if(tile == null){
                        continue;
                    }
                    tile.getIndicator().setIndicator(false);
                    tile.getIndicator().setIndicator(false, 1);
                }

                moves = board.get(clickedIndex).getPiece().getPossibleMoves(getSelf());
                board.get(clickedIndex).getIndicator().setIndicator(true, 1);
                for(int index: moves){
                    Tile tile = board.get(index);
                    if(tile != null){ tile.getIndicator().setIndicator(true);}
                }
                repaint();
            }
        });
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {

        if ((this.window.getSize().getWidth() / this.window.getSize().getHeight()) < (1400.0 / 1200.0)){
            this.scale = this.window.getSize().getWidth() / 1400.0;
        } else {
            this.scale = this.window.getSize().getHeight() / 1200.0;
        }

        setBackground(Color.BLACK);
        super.paintComponent(g);
        Image bg = new ImageIcon(Images.class.getResource("background.jpg")).getImage();
        Image exit = new ImageIcon(Images.class.getResource("exit.png")).getImage();
        g.drawImage(bg, 0, 0, this.getWidth(), this.getHeight(), this);

        int width = 200;
        int height = (int) ((exit.getHeight(null) / (double) (exit.getWidth(null))) * width);
        g.drawImage(exit, this.getWidth() * 2 / 3, 100, width, height, this);

        int w = this.getWidth() * 2/3, h = 100;
        Polygon exitPolygon = new Polygon();
        exitPolygon.addPoint(w + 0,         h + height / 4);
        exitPolygon.addPoint(w + width / 2, h + 0);
        exitPolygon.addPoint(w + width,     h + height / 4);
        exitPolygon.addPoint(w + width,     h + height * 3 / 4);
        exitPolygon.addPoint(w + width / 2, h + height);
        exitPolygon.addPoint(w + 0,         h + height * 3 / 4);
        this.removeMouseListener(exitAdapter);
        exitAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (exitPolygon.contains(e.getX(), e.getY())) {
                    window.showMenu();
                }
            }
        };
        this.addMouseListener(exitAdapter);

        for (Tile tile : board) {
            if (tile == null) {
                continue;
            }
            tile.setCheck(false);

            int check = getCheckedKing(board, getSelf());
            if (tile.getPiece().isWhite() == check & tile.getPiece().getClass() == King.class) {
                tile.setCheck(true);
            }

            tile.draw(g, this.scale, this);

            if (tile.getPiece() != null) {
                tile.getPiece().draw(g, this.scale, this);
            }
            tile.getIndicator().draw(g, this.scale, this);
        }
        if(this.promotion != -1){
            promotePiece(g, this);
        }
        ArrayList<Integer> moves = getMovesByPlayer(this, whiteTurn);
        if (moves.size() == 0) {
            window.changeLayout(1, 1 - whiteTurn, findChecks(board, this).size() > 0);
        }
        window.changeLayout(this);

    }


    private void movePiece(int origin, int destination){

        for (Tile tile: this.board) {
            if(tile == null) {continue;}
            tile.getIndicator().setIndicator(false, 3);
        }

        Piece dest = this.board.get(destination).getPiece();
        this.board.get(destination).getIndicator().setIndicator(true, 3);
        int destRank = dest.getRank();
        String destFile = dest.getFile();
        Piece orig = this.board.get(origin).getPiece();
        this.board.get(origin).getIndicator().setIndicator(true, 3);
        int origRank = orig.getRank();
        String origFile = orig.getFile();

        if(destination == this.enPassant && orig.getClass() == Pawn.class){
            this.board.get(destination + ((orig.isWhite() == 1) ? 11 : -11)).setPiece(new Empty(destRank + ((orig.isWhite() == 1) ? -1 : 1), destFile));
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
        orig.setFile(destFile);
        orig.setRank(destRank);
        this.board.get(destination).setPiece(orig);
        this.board.get(origin).setPiece(new Empty(origRank, origFile));

        if(orig.getClass() == Pawn.class && Math.abs(origin - destination) == 22){
            this.enPassant = origin + 11;
            if (orig.isWhite() == 1){
                this.enPassant = origin - 11;
            }
        } else {
            this.enPassant = -1;
        }
        if (this.whiteTurn == 1) {
            this.moveHistory.add(getBoardString());
        } else {
            int index = this.moveHistory.size() - 1;
            this.moveHistory.set(index, this.moveHistory.get(index) + getBoardString());
        }

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
        g.setColor(Color.RED);
        g.drawRect((int) (((57.74 + 28.88) * 5 - 5) * this.scale) - 1, (int) ((450) * this.scale) - 1, (int)(289 * this.scale), (int)(301 * this.scale));
        g.setColor(Color.GRAY);
        g.fillRect((int) (((57.74 + 28.88) * 5 - 5) * this.scale), (int) ((450) * this.scale), (int)(288 * this.scale), (int)(300 * this.scale));
        g.setColor(Color.GRAY);
        int color = this.promotion > 70 ? 0 : 1;


        this.promotionPieces =  new Promotion[]{new Promotion(0, color), new Promotion(1, color), new Promotion(2, color), new Promotion(3, color)};


        for (Promotion piece : this.promotionPieces) {
            piece.draw(g, this.scale, b);
        }
    }
}

