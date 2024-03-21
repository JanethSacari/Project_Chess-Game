package src.chess;

import src.boardgame.Board;
import src.chess.pieces.King;
import src.chess.pieces.Rook;

public class ChessMatch {

    private Board board;

    public ChessMatch() {
        board = new Board(8, 8);
        initialSetup();
    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] structure = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i=0; i<board.getRows(); i++) {
            for (int j=0; j<board.getColumns(); j++) {
                structure[i][j] = (ChessPiece) board.selectedPiece(i, j);
            }
        }
        return structure;
    }

    private void  placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
    }

    private void initialSetup() {
        placeNewPiece('c', 1, new Rook(board, Color.RED));
        placeNewPiece('c', 2, new Rook(board, Color.RED));
        placeNewPiece('d', 2, new Rook(board, Color.RED));
        placeNewPiece('e', 2, new Rook(board, Color.RED));
        placeNewPiece('e', 1, new Rook(board, Color.RED));
        placeNewPiece('d', 1, new King(board, Color.RED));

        placeNewPiece('c', 7, new Rook(board, Color.BLUE));
        placeNewPiece('c', 8, new Rook(board, Color.BLUE));
        placeNewPiece('d', 7, new Rook(board, Color.BLUE));
        placeNewPiece('e', 7, new Rook(board, Color.BLUE));
        placeNewPiece('e', 8, new Rook(board, Color.BLUE));
        placeNewPiece('d', 8, new King(board, Color.BLUE));
    }

}
