package src.chess.pieces;

import src.boardgame.Board;
import src.boardgame.Position;
import src.chess.ChessMatch;
import src.chess.ChessPiece;
import src.chess.Color;

public class King extends ChessPiece {

    private ChessMatch chessMatch;

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public String toString() {
        return "K";
    }

    private boolean canMove(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().selectedPiece(position);
        return piece == null || piece.getColor() != getColor();
    }

    private boolean testRookCastling(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().selectedPiece(position);
        return piece != null && piece instanceof Rook && piece.getColor() == getColor() && piece.getMoveCount() == 0;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] moves = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position pos = new Position(0, 0);
        // above
        pos.setValues(position.getRow() - 1, position.getColumn());
        if (getBoard().positionExists(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
        }
        // below
        pos.setValues(position.getRow() + 1, position.getColumn());
        if (getBoard().positionExists(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
        }
        // left
        pos.setValues(position.getRow(), position.getColumn() - 1);
        if (getBoard().positionExists(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
        }
        // right
        pos.setValues(position.getRow(), position.getColumn() + 1);
        if (getBoard().positionExists(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
        }
        // nw
        pos.setValues(position.getRow() - 1, position.getColumn() - 1);
        if (getBoard().positionExists(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
        }
        // ne
        pos.setValues(position.getRow() - 1, position.getColumn() + 1);
        if (getBoard().positionExists(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
        }
        // sw
        pos.setValues(position.getRow() + 1, position.getColumn() - 1);
        if (getBoard().positionExists(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
        }
        // se
        pos.setValues(position.getRow() + 1, position.getColumn() + 1);
        if (getBoard().positionExists(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
        }

        // Special move Castling
        if (getMoveCount() == 0 && !chessMatch.getCheck()) {
            //King Side Rook
            Position posT1 = new Position(position.getRow(), position.getColumn() + 3);
            if (testRookCastling(posT1)) {
                Position pos1 = new Position(position.getRow(), position.getColumn() + 1);
                Position pos2 = new Position(position.getRow(), position.getColumn() + 2);
                if (getBoard().selectedPiece(pos1) == null && getBoard().selectedPiece(pos2) == null) {
                    moves[position.getRow()][position.getColumn() + 2] = true;
                }
            }
            //Queen Side Rook
            Position posT2 = new Position(position.getRow(), position.getColumn() - 4);
            if (testRookCastling(posT2)) {
                Position pos1 = new Position(position.getRow(), position.getColumn() - 1);
                Position pos2 = new Position(position.getRow(), position.getColumn() - 2);
                Position pos3 = new Position(position.getRow(), position.getColumn() - 3);
                if (getBoard().selectedPiece(pos1) == null && getBoard().selectedPiece(pos2) == null && getBoard().selectedPiece(pos3) == null) {
                    moves[position.getRow()][position.getColumn() - 2] = true;
                }
            }
        }

        return moves;
    }
}
