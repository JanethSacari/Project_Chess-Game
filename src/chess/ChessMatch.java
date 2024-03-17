package src.chess;

import src.boardgame.Board;
import src.boardgame.Position;
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

    private void initialSetup() {
        board.placePiece(new Rook(board, Color.BLUE), new Position(4,5 ));
        board.placePiece(new King(board, Color.RED), new Position(0, 4));
        board.placePiece(new King(board, Color.BLUE), new Position(7, 4));
    }

}
