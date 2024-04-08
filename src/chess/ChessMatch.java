package src.chess;

import src.boardgame.Board;
import src.boardgame.Piece;
import src.boardgame.Position;
import src.chess.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {

    private int turn;
    private Color currentPlayer;
    private Board board;
    private boolean check;
    private boolean checkMate;
    private ChessPiece enPassantVulnerable;

    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();

    public ChessMatch() {
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.BLUE;
        check = false;
        initialSetup();
    }

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean getCheck() {
        return check;
    }

    public boolean getCheckMate() {
        return checkMate;
    }

    public ChessPiece getEnPassantVulnerable() {
        return enPassantVulnerable;
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
        if (testCheck(currentPlayer)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("You can't put yourself in check");
        }
        ChessPiece movedPiece = (ChessPiece) board.selectedPiece(target);
        check = (testCheck(opponent(currentPlayer))) ? true : false;
        if (testCheckMate(opponent(currentPlayer))) {
            checkMate = true;
        }
        else {
            nextTurn();
        }
        // Specialmove en passant
        if (movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
            enPassantVulnerable = movedPiece;
        } else {
            enPassantVulnerable = null;
        }
        return (ChessPiece) capturedPiece;
    }

    private Piece makeMove(Position source, Position target){
        ChessPiece movedPiece = (ChessPiece) board.removePiece(source);
        movedPiece.increaseMoveCount();
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(movedPiece, target);

        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }

        //Specialmove castling kingside rook
        if (movedPiece instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
            Position targetT = new Position(source.getRow(), source.getColumn() + 1);
            ChessPiece rook =  (ChessPiece) board.removePiece(sourceT);
            board.placePiece(rook, targetT);
            rook.increaseMoveCount();
        }
        //Specialmove castling queenside rook
        if (movedPiece instanceof King && target.getColumn() == source.getColumn() - 2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
            Position targetT = new Position(source.getRow(), source.getColumn() - 1);
            ChessPiece rook =  (ChessPiece) board.removePiece(sourceT);
            board.placePiece(rook, targetT);
            rook.increaseMoveCount();
        }
        //Specialmove en passant
        if (movedPiece instanceof Pawn) {
            if (source.getColumn() != target.getColumn() && capturedPiece == null) {
                Position pawnPosition;
                if (movedPiece.getColor() == Color.BLUE) {
                    pawnPosition = new Position(target.getRow() + 1, target.getColumn());
                } else {
                    pawnPosition = new Position(target.getRow() - 1, target.getColumn());
                }
                capturedPiece = board.removePiece(pawnPosition);
                capturedPieces.add(capturedPiece);
                piecesOnTheBoard.remove(capturedPiece);
            }
        }

        return capturedPiece;
    }

    private void undoMove(Position source, Position target, Piece capturedPiece) {
        ChessPiece piece = (ChessPiece) board.removePiece(target);
        piece.decreaseMoveCount();
        board.placePiece(piece, source);

        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }

        //Specialmove castling kingside rook
        if (piece instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
            Position targetT = new Position(source.getRow(), source.getColumn() + 1);
            ChessPiece rook =  (ChessPiece) board.removePiece(targetT);
            board.placePiece(rook, sourceT);
            rook.decreaseMoveCount();
        }
        //Specialmove castling queenside rook
        if (piece instanceof King && target.getColumn() == source.getColumn() - 2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
            Position targetT = new Position(source.getRow(), source.getColumn() - 1);
            ChessPiece rook =  (ChessPiece) board.removePiece(targetT);
            board.placePiece(rook, sourceT);
            rook.decreaseMoveCount();
        }
        //Specialmove en passant
        if (piece instanceof Pawn) {
            if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
                ChessPiece pawn = (ChessPiece) board.removePiece(target);
                Position pawnPosition;
                if (piece.getColor() == Color.BLUE) {
                    pawnPosition = new Position(3, target.getColumn());
                } else {
                    pawnPosition = new Position(4, target.getColumn());
                }
                board.placePiece(pawn, pawnPosition);
            }
        }
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

    private Color opponent(Color color) {
        return (color == Color.BLUE) ? Color.RED : Color.BLUE;
    }

    private ChessPiece king(Color color) {
        List<Piece> kings = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
        for (Piece piece : kings) {
            if (piece instanceof King) {
                return (ChessPiece) piece;
            }
        }
        throw new IllegalStateException("There is no " + color + " king on the board");
    }

    private boolean testCheck(Color color) {
        Position kingPosition = king(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
        for (Piece piece : opponentPieces) {
            boolean[][] structure = piece.possibleMoves();
            if(structure[kingPosition.getRow()][kingPosition.getColumn()]) {
                return true;
            }
        }
        return false;
    }

    private boolean testCheckMate(Color color) {
        if (!testCheck(color)) {
            return false;
        }
        List<Piece> pieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
        for (Piece piece : pieces) {
            boolean[][] moves = piece.possibleMoves();
            for (int i=0; i<board.getRows(); i++) {
                for (int j=0; j<board.getColumns(); j++) {
                    if (moves[i][j]) {
                        Position source = ((ChessPiece)piece).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source,  target);
                        boolean testCheck = testCheck(color);
                        undoMove(source, target, capturedPiece);
                        if (!testCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void  placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
    }

    private void initialSetup() {
        placeNewPiece('a', 1, new Rook(board, Color.BLUE));
        placeNewPiece('b', 1, new Knight(board, Color.BLUE));
        placeNewPiece('c', 1, new Bishop(board, Color.BLUE));
        placeNewPiece('d', 1, new Queen(board, Color.BLUE));
        placeNewPiece('e', 1, new King(board, Color.BLUE, this));
        placeNewPiece('f', 1, new Bishop(board, Color.BLUE));
        placeNewPiece('g', 1, new Knight(board, Color.BLUE));
        placeNewPiece('h', 1, new Rook(board, Color.BLUE));
        placeNewPiece('a', 2, new Pawn(board, Color.BLUE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.BLUE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.BLUE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.BLUE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.BLUE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.BLUE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.BLUE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.BLUE, this));

        placeNewPiece('a', 8, new Rook(board, Color.RED));
        placeNewPiece('b', 8, new Knight(board, Color.RED));
        placeNewPiece('c', 8, new Bishop(board, Color.RED));
        placeNewPiece('d', 8, new Queen(board, Color.RED));
        placeNewPiece('e', 8, new King(board, Color.RED, this));
        placeNewPiece('f', 8, new Bishop(board, Color.RED));
        placeNewPiece('g', 8, new Knight(board, Color.RED));
        placeNewPiece('h', 8, new Rook(board, Color.RED));
        placeNewPiece('a', 7, new Pawn(board, Color.RED, this));
        placeNewPiece('b', 7, new Pawn(board, Color.RED, this));
        placeNewPiece('c', 7, new Pawn(board, Color.RED, this));
        placeNewPiece('d', 7, new Pawn(board, Color.RED, this));
        placeNewPiece('e', 7, new Pawn(board, Color.RED, this));
        placeNewPiece('f', 7, new Pawn(board, Color.RED, this));
        placeNewPiece('g', 7, new Pawn(board, Color.RED, this));
        placeNewPiece('h', 7, new Pawn(board, Color.RED, this));

    }

}
