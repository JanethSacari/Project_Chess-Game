package src.chess.pieces;

import src.boardgame.Board;
import src.boardgame.Position;
import src.chess.ChessPiece;
import src.chess.Color;

public class Pawn extends ChessPiece {
    public Pawn(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] moves = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position pos = new Position(0, 0);
        if (getColor() == Color.BLUE) {
            pos.setValues(position.getRow() - 1, position.getColumn());
            if (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
                moves[pos.getRow()][pos.getColumn()] = true;
            }
            pos.setValues(position.getRow() - 2, position.getColumn());
            Position pos2 = new Position(position.getRow() - 1, position.getColumn());
            if (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos) && getBoard().positionExists(pos2) && getMoveCount() == 0) {
                moves[pos.getRow()][pos.getColumn()] = true;
            }
            pos.setValues(position.getRow() - 1, position.getColumn() - 1);
            if (getBoard().positionExists(pos) && isThereOpponentPiece(pos)) {
                moves[pos.getRow()][pos.getColumn()] = true;
            }
            pos.setValues(position.getRow() - 1, position.getColumn() + 1);
            if (getBoard().positionExists(pos) && isThereOpponentPiece(pos)) {
                moves[pos.getRow()][pos.getColumn()] = true;
            }
        }
        else {
            pos.setValues(position.getRow() + 1, position.getColumn());
            if (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos)) {
                moves[pos.getRow()][pos.getColumn()] = true;
            }
            pos.setValues(position.getRow() + 2, position.getColumn());
            Position pos2 = new Position(position.getRow() + 1, position.getColumn());
            if (getBoard().positionExists(pos) && !getBoard().thereIsAPiece(pos) && getBoard().positionExists(pos2) && getMoveCount() == 0) {
                moves[pos.getRow()][pos.getColumn()] = true;
            }
            pos.setValues(position.getRow() + 1, position.getColumn() - 1);
            if (getBoard().positionExists(pos) && isThereOpponentPiece(pos)) {
                moves[pos.getRow()][pos.getColumn()] = true;
            }
            pos.setValues(position.getRow() + 1, position.getColumn() + 1);
            if (getBoard().positionExists(pos) && isThereOpponentPiece(pos)) {
                moves[pos.getRow()][pos.getColumn()] = true;
            }
        }
        return moves;
    }

    @Override
    public String toString() {
        return "P";
    }

}
