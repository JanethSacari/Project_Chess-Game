package src.chess.pieces;

import src.boardgame.Board;
import src.boardgame.Position;
import src.chess.ChessPiece;
import src.chess.Color;

public class Knight extends ChessPiece {

    public Knight(Board board, Color color) {
        super(board, color);
    }

    private boolean canMove(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().selectedPiece(position);
        return piece == null || piece.getColor() != getColor();
    }

    @Override
    public String toString() {
        return "H";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] moves = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position pos = new Position(0, 0);

        pos.setValues(position.getRow() - 1, position.getColumn() - 2);
        if (getBoard().positionExists(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
        }

        pos.setValues(position.getRow() - 2 , position.getColumn() - 1);
        if (getBoard().positionExists(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
        }

        pos.setValues(position.getRow() - 2, position.getColumn() + 1);
        if (getBoard().positionExists(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
        }

        pos.setValues(position.getRow() - 1, position.getColumn() + 2);
        if (getBoard().positionExists(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
        }
        pos.setValues(position.getRow() + 1, position.getColumn() + 2);
        if (getBoard().positionExists(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
        }

        pos.setValues(position.getRow() + 2 , position.getColumn() + 1);
        if (getBoard().positionExists(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
        }

        pos.setValues(position.getRow() + 2, position.getColumn() - 1);
        if (getBoard().positionExists(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
        }

        pos.setValues(position.getRow() + 1, position.getColumn() - 2);
        if (getBoard().positionExists(pos) && canMove(pos)) {
            moves[pos.getRow()][pos.getColumn()] = true;
        }
        return moves;
    }

}