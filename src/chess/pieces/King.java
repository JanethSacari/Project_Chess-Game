package src.chess.pieces;

import src.boardgame.Board;
import src.boardgame.Position;
import src.chess.ChessPiece;
import src.chess.Color;

public class King extends ChessPiece {

    public King(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "K";
    }

    private boolean canMove(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().selectedPiece(position);
        return piece == null || piece.getColor() != getColor();
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
        return moves;
    }
}
