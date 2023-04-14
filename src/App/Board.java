package App;

import Entity.Piece.*;
import Entity.Tile.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class Board extends JPanel implements IHashMaps {
    public static ArrayList<Tile> board;
    private static int promotion;
    public static int whiteTurn;
    public static int enPassant;
    private static double scale;
    private static int clickedIndex = -1;
    private static ArrayList<Integer> moves = new ArrayList<>();
    public String getPosition() {
        return position;
    }

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

    public Board(String position, int whiteTurn, int enPassant){
        this.position = position;
        Board.scale = 1;
        Board.promotion = -1;
        this.setPosition(position, whiteTurn, enPassant);
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int startheight = -300;
                int clickedTile = -1;
                if (Board.promotion != -1){
                    int[][] offsets = new int[][] {
                            {(int) (((57.74 + 28.88) * 5 - 5) * Board.scale) + 43, (int) ((450) * Board.scale) + 47}, {(int) (((57.74 + 28.88) * 5 - 5) * Board.scale) + 43*2 + 80, (int) ((450) * Board.scale) + 47},
                            {(int) (((57.74 + 28.88) * 5 - 5) * Board.scale) + 43, (int) ((450) * Board.scale) + 47*2 + 80}, {(int) (((57.74 + 28.88) * 5 - 5) * Board.scale) + 43*2 + 80, (int) ((450) * Board.scale) + 47*2 + 80}
                    };
                    int length = (int) (80 * Board.scale);
                    for(int i = 0; i < 4; i++){
                        int[] offset = offsets[i];
                        if (e.getX() > offset[0] && e.getX() < (offset[0] + length) && e.getY() > offset[1] && e.getY() < offset[1] + length){
                            Piece piececlone = Board.board.get(Board.promotion).getPiece();
                            switch (i) {
                                case 0 -> Board.board.get(Board.promotion).setPiece(new Queen(piececlone.getRank(), piececlone.getFile(), piececlone.isWhite()));
                                case 1 -> Board.board.get(Board.promotion).setPiece(new Rook(piececlone.getRank(), piececlone.getFile(), piececlone.isWhite()));
                                case 2 -> Board.board.get(Board.promotion).setPiece(new Bishop(piececlone.getRank(), piececlone.getFile(), piececlone.isWhite()));
                                case 3 -> Board.board.get(Board.promotion).setPiece(new Knight(piececlone.getRank(), piececlone.getFile(), piececlone.isWhite()));
                            }
                            break;
                        }
                    }

                    Board.promotion = -1;
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
                    int startx = (int) ((hoffset + 17.5) * Board.scale);
                    int starty = (int) ((startheight + (100 * (12 - tile.getRank())) + offset + 10) * Board.scale);
                    int length = (int) (80 * Board.scale);

                    if (!(e.getX() > startx && e.getX() < startx + length &&
                        e.getY() > starty && e.getY() < starty + length
                    )){continue;}

                    if (Board.clickedIndex == i){
                        Board.moves = new ArrayList<>();
                        for (int j = 0; j < 121; j++) {
                            Tile tile2 = board.get(j);
                            if(tile2 == null){
                                continue;
                            }
                            tile2.setMoveIndicator(false);
                            tile2.setMoveIndicator(false, 1);
                        }
                        Board.clickedIndex = -1;
                        repaint();
                        break;
                    }

                    if(Board.clickedIndex > 0 & Board.board.get(i).getPiece().isWhite() != Board.whiteTurn){
                        clickedTile = Board.clickedIndex;
                    }
                    Board.clickedIndex = i;

                    break;

                }
                if(Board.clickedIndex < 0){
                    return;
                }
                if (Board.board.get(Board.clickedIndex).getPiece().isWhite() != Board.whiteTurn){
                    if(clickedTile > 0) {
                        if (Board.board.get(Board.clickedIndex).isMoveIndicator()) {
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
                        Board.clickedIndex = -1;
                        //Board.whiteTurn = !Board.whiteTurn;
                        repaint();
                        return;
                    }
                    Board.clickedIndex = -1;
                    Board.moves = new ArrayList<>();
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

                Board.moves = board.get(Board.clickedIndex).getPiece().getPossibleMoves();
                if (Board.moves.size() == 0 && board.get(Board.clickedIndex).getPiece().getClass() == King.class){
                    if(findChecks(Board.board).size() != 0) {
                        System.out.println("mate");
                    } else {
                        System.out.println("stalemate");
                    }
                }
                board.get(Board.clickedIndex).setMoveIndicator(true, 1);
                for(int index: Board.moves){
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
                g.drawImage(tile.getPiece().getTexture(), (int) ((hoffset + 17.5) * Board.scale), (int) ((startheight + (100 * (12 - tile.getRank())) + offset + 10) * Board.scale), (int) (80 * Board.scale), (int) (80 * Board.scale), this);
            }
            g.drawImage(tile.getMoveIndicatorTexture(), (int) ((hoffset + 42.5) * Board.scale), (int) ((startheight + (100 * (12 - tile.getRank())) + offset + 35) * Board.scale), (int) (30 * Board.scale), (int) (30 * Board.scale), this);


            //g.drawImage(hex, 100, 100,   this);
        }
        if(Board.promotion != -1){
            int offset = Board.offset.get("e") * 50;
            g.setColor(Color.RED);
            g.drawRect((int) (((57.74 + 28.88) * 5 - 5) * Board.scale) - 1, (int) ((450) * Board.scale) - 1, 290, 302);
            g.setColor(Color.GRAY);
            g.fillRect((int) (((57.74 + 28.88) * 5 - 5) * Board.scale), (int) ((450) * Board.scale), 288, 300);
            g.setColor(Color.GRAY);
            int color = Board.promotion > 70 ? 0 : 1;


            Piece[] pieces = new Piece[]{new Queen(-1, "0", color), new Rook(-1, "0", color), new Bishop(-1, "0", color), new Knight(-1, "0", color)};

            Image[] images = new Image[]{pieces[0].getTexture(), pieces[1].getTexture(), pieces[2].getTexture(), pieces[3].getTexture()};
            int[][] offsets = new int[][] {
                    {(int) (((57.74 + 28.88) * 5 - 5) * Board.scale) + 43, (int) ((450) * Board.scale) + 47}, {(int) (((57.74 + 28.88) * 5 - 5) * Board.scale) + 43*2 + 80, (int) ((450) * Board.scale) + 47},
                    {(int) (((57.74 + 28.88) * 5 - 5) * Board.scale) + 43, (int) ((450) * Board.scale) + 47*2 + 80}, {(int) (((57.74 + 28.88) * 5 - 5) * Board.scale) + 43*2 + 80, (int) ((450) * Board.scale) + 47*2 + 80}
            };
            for(int i = 0; i < images.length; i++){
                g.drawImage(images[i], offsets[i][0], offsets[i][1], (int) (80 * Board.scale), (int) (80 * Board.scale), this);
            }
        }

    }

    private static void movePiece(int origin, int destination){

        Piece dest = board.get(destination).getPiece();
        int destRank = dest.getRank();
        String destFile = dest.getFile();
        Piece orig = board.get(origin).getPiece();
        int origRank = orig.getRank();
        String origFile = orig.getFile();

        if(destination == Board.enPassant){
            board.get(destination + ((orig.isWhite() == 1) ? 11 : -11)).setPiece(new Empty(destRank + ((orig.isWhite() == 1) ? 1 : -1), destFile));
        }
        if(orig.getClass() == Pawn.class && ((dest.getRank() == 1 && orig.isWhite() == 0) || ((
                dest.getRank() == 6 && (dest.getFile().equals("a") || dest.getFile().equals("l")) ||
                dest.getRank() == 7 && (dest.getFile().equals("b") || dest.getFile().equals("k")) ||
                dest.getRank() == 8 && (dest.getFile().equals("c") || dest.getFile().equals("i")) ||
                dest.getRank() == 9 && (dest.getFile().equals("d") || dest.getFile().equals("h")) ||
                dest.getRank() == 10 && (dest.getFile().equals("e") || dest.getFile().equals("g")) ||
                dest.getRank() == 11 && dest.getFile().equals("f")) && orig.isWhite() == 1))){
            Board.promotion = destination;
        }

        board.get(origin).setPiece(new Empty(origRank, origFile));
        orig.setFile(destFile);
        orig.setRank(destRank);
        board.get(destination).setPiece(orig);
        if(orig.getClass() == Pawn.class && Math.abs(origin - destination) == 22){
            //System.out.println(Math.abs(origin - destination));
            Board.enPassant = origin + 11;
            if (orig.isWhite() == 1){
                Board.enPassant = origin - 11;
            }
        } else {
            Board.enPassant = -1;
        }
        Board.whiteTurn = 1 - Board.whiteTurn;
    }

    private static ArrayList<Integer> findChecks(ArrayList<Tile> board){

        ArrayList<Integer> checkingIndexes = new ArrayList<>();
        int kingPosition = -1;
        for(int i = 0; i < 121; i++){
            Tile tile = board.get(i);
            if(tile == null){
                continue;
            }
//            System.out.println(tile.getPiece());
            if(tile.getPiece().getClass() != King.class || tile.getPiece().isWhite() != whiteTurn){
                continue;
            }
            kingPosition = i;
        }

        int rank = board.get(kingPosition).getRank();
        String file = board.get(kingPosition).getFile();
        // look for knight checks
        {
            int[][] moveArray = {{1, 0}, {1, 2}, {3, 2}, {3, 4}, {5, 4}, {5, 6}, {7, 6}, {7, 8}, {9, 8}, {9, 10}, {11, 10}, {11, 0}};
            ArrayList<Integer> possibleKnightChecks = Piece.generateMovesFromArray(moveArray, rank, file, false, false);

            for(int move : possibleKnightChecks){


                if(move < 0){
                    continue;
                }
                if (board.get(move) == null){
                    continue;
                }
                if (board.get(move).getPiece().getClass() != Knight.class){
                    continue;
                }
                if (board.get(move).getPiece().isWhite() == whiteTurn){
                    continue;
                }
                checkingIndexes.add(move);
            }
        }
        // look for bishop or queen checks
        {
            int[][][] moveArrayArray = new int[][][]{
                    {
                            {1}, {3}, {5}, {7}, {9}, {11}
                    },
                    {
                            {0}, {2}, {4}, {6}, {8}, {10}
                    }
            };
            Class[] classes = new Class[]{Bishop.class, Rook.class};

            for (int i = 0; i < 2; i++) {
                int[][] moveArray = moveArrayArray[i];
                ArrayList<Integer> Return = Piece.generateMovesFromArray(moveArray, rank, file, true, false);
                ArrayList<ArrayList<Integer>> possibleChecks = new ArrayList<>();
                int index = -1;
                for (int move : Return) {
                    if (move < 0) {
                        index++;
                        possibleChecks.add(new ArrayList<>());
                        continue;
                    }
                    possibleChecks.get(index).add(move);

                }

                for (ArrayList<Integer> line : possibleChecks) {

                    for (int move : line) {
                        if(board.get(move) == null || move < 0){
                            continue;
                        }


//                        if (move == ignore) {
//                            continue;
//                        }
                        Tile tile = board.get(move);
                        if (tile == null) {
                            continue;
                        }
                        if (tile.getPiece().isWhite() == whiteTurn) {
                            break;
                        }
                        if (!(tile.getPiece().getClass() == classes[i] || tile.getPiece().getClass() == Queen.class)) {
                            continue;
                        }
                        checkingIndexes.add(move);
                    }
                }
            }

        }
        // look for pawn checks
        {
            int[][][] moveArrayArray = new int[][][]{{{2}, {10}}, {{4}, {8}}};
            int[][] moveArray = moveArrayArray[1 - whiteTurn];
            ArrayList<Integer> Return = Piece.generateMovesFromArray(moveArray, rank, file, true, false);
            ArrayList<ArrayList<Integer>> possibleChecks = new ArrayList<>();
            int index = -1;
            for (int move : Return) {
                if (move < 0) {
                    index++;
                    possibleChecks.add(new ArrayList<>());
                    continue;
                }
                possibleChecks.get(index).add(move);

            }
            for (ArrayList<Integer> line : possibleChecks) {

                for (int move : line) {
                    if(board.get(move) == null || move < 0){
                        continue;
                    }
//                    if (move == ignore) {
//                        continue;
//                    }
                    Tile tile = board.get(move);
                    if (tile == null) {
                        continue;
                    }
                    if (tile.getPiece().isWhite() == whiteTurn) {
                        break;
                    }
                    if (tile.getPiece().getClass() != Pawn.class) {
                        continue;
                    }
                    checkingIndexes.add(move);
                }
            }

        }
        return checkingIndexes;
    }

    public static boolean validateMove(int origin, int destination){
        ArrayList<Tile> testboard = new ArrayList<>();
        for(Tile tile : board){
            if(tile == null){
                testboard.add(null);
                continue;
            }
            Tile tile2 = new Tile(tile.getRank(), tile.getFile());
            Piece piece = new Empty(tile.getPiece().getRank(), tile.getPiece().getFile());
            if(tile.getPiece().getClass() == Pawn.class){
                piece = new Pawn(tile.getPiece().getRank(), tile.getPiece().getFile(), tile.getPiece().isWhite());
            } else if(tile.getPiece().getClass() == Knight.class){
                piece = new Knight(tile.getPiece().getRank(), tile.getPiece().getFile(), tile.getPiece().isWhite());
            } else if(tile.getPiece().getClass() == Bishop.class){
                piece = new Bishop(tile.getPiece().getRank(), tile.getPiece().getFile(), tile.getPiece().isWhite());
            } else if(tile.getPiece().getClass() == Rook.class){
                piece = new Rook(tile.getPiece().getRank(), tile.getPiece().getFile(), tile.getPiece().isWhite());
            } else if(tile.getPiece().getClass() == Queen.class){
                piece = new Queen(tile.getPiece().getRank(), tile.getPiece().getFile(), tile.getPiece().isWhite());
            } else if(tile.getPiece().getClass() == King.class){
                piece = new King(tile.getPiece().getRank(), tile.getPiece().getFile(), tile.getPiece().isWhite());

            }
            //System.out.println(tile.getPiece().isWhite());
            tile2.setPiece(piece);
            testboard.add(tile2);
        }

        //movePiece(testboard.get(origin), testboard.get(destination));
        Piece dest = testboard.get(destination).getPiece();
        int destRank = dest.getRank();
        String destFile = dest.getFile();
        Piece orig = testboard.get(origin).getPiece();
        int origRank = orig.getRank();
        String origFile = orig.getFile();
        testboard.get(origin).setPiece(new Empty(origRank, origFile));
        orig.setFile(destFile);
        orig.setRank(destRank);
        testboard.get(destination).setPiece(orig);
        if(destination == enPassant){
            if(orig.isWhite() == 1){
                testboard.get(destination + 11).setPiece(new Empty(destRank - 1, destFile));
            } else {
                testboard.get(destination - 11).setPiece(new Empty(destRank + 1, destFile));
            }
        }
        ArrayList<Integer> checks = Board.findChecks(testboard);
        return (checks.size() == 0);
    }
}
