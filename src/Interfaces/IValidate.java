package Interfaces;

import App.Board;
import Entity.Piece.*;
import Entity.Tile.Tile;

import java.util.ArrayList;

import static App.Board.*;

public interface IValidate {
     default boolean validateMove(int origin, int destination, Board board){
        ArrayList<Tile> testboard = new ArrayList<>();
        for(Tile tile : board.board){
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
        if(destination == board.enPassant){
            if(orig.isWhite() == 1){
                testboard.get(destination + 11).setPiece(new Empty(destRank - 1, destFile));
            } else {
                testboard.get(destination - 11).setPiece(new Empty(destRank + 1, destFile));
            }
        }
        ArrayList<Integer> checks = findChecks(testboard, board);
        return (checks.size() == 0);
    }

    default ArrayList<Integer> findChecks(ArrayList<Tile> board, Board parent){

        ArrayList<Integer> checkingIndexes = new ArrayList<>();
        int kingPosition = -1;
        for(int i = 0; i < 121; i++){
            Tile tile = board.get(i);
            if(tile == null){
                continue;
            }
//            System.out.println(tile.getPiece());
            if(tile.getPiece().getClass() != King.class || tile.getPiece().isWhite() != parent.whiteTurn){
                continue;
            }
            kingPosition = i;
        }

        int rank = board.get(kingPosition).getRank();
        String file = board.get(kingPosition).getFile();
        // look for knight checks
        Piece piece = new Piece(-1, "");
        {
            int[][] moveArray = {{1, 0}, {1, 2}, {3, 2}, {3, 4}, {5, 4}, {5, 6}, {7, 6}, {7, 8}, {9, 8}, {9, 10}, {11, 10}, {11, 0}};
            ArrayList<Integer> possibleKnightChecks = piece.generateMovesFromArray(parent, moveArray, rank, file, false, false);

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
                if (board.get(move).getPiece().isWhite() == parent.whiteTurn){
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
                ArrayList<Integer> Return = piece.generateMovesFromArray(parent, moveArray, rank, file, true, false);
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
                        if (tile.getPiece().isWhite() == parent.whiteTurn) {
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
            int[][] moveArray = moveArrayArray[1 - parent.whiteTurn];
            ArrayList<Integer> Return = piece.generateMovesFromArray(parent, moveArray, rank, file, true, false);
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
                    if (tile.getPiece().isWhite() == parent.whiteTurn) {
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
}
