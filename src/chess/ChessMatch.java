package src.chess;

import src.boardgame.Board;
import src.boardgame.Piece;
import src.boardgame.Position;
import src.chess.pieces.King;
import src.chess.pieces.Rook;

public class ChessMatch {

    private int turn;
    private Color currentPlayer;
    private Board board;

    public ChessMatch() {
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.BLUE;
        initialSetup();
    }

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
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

    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.selectedPiece(position).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);
        nextTurn();
        return (ChessPiece) capturedPiece;
    }

    private Piece makeMove(Position source, Position target){
        Piece movedPiece = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(movedPiece, target);
        return capturedPiece;
    }

    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) {
            throw new ChessException("There is no piece on source position!");
        }
        if (currentPlayer != ((ChessPiece) board.selectedPiece(position)).getColor()) {
            throw new ChessException("The chosen piece is not yours!");
        }
        if (!board.selectedPiece(position).isThereAnyPossibleMove()) {
            throw new ChessException("There is no possible moves for the chosen piece!");
        }
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!board.selectedPiece(source).possibleMove(target)) {
            throw new ChessException("The chosen piece can't move to target position!");
        }
    }

    private void nextTurn() {
        turn ++;
        currentPlayer = (currentPlayer == Color.BLUE) ? Color.RED : Color.BLUE;
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
