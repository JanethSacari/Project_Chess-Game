package com.repliforce.chessgame.game.fx;

import com.repliforce.chessgame.chess.ChessException;
import com.repliforce.chessgame.chess.ChessMatch;
import com.repliforce.chessgame.chess.ChessPiece;
import com.repliforce.chessgame.chess.ChessPosition;
import com.repliforce.chessgame.chess.Color;
import javafx.scene.control.ChoiceDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChessGameController {

    private ChessMatch chessMatch = new ChessMatch();
    private final List<ChessPiece> capturedPieces = new ArrayList<>();
    private final ChessBoardView view;
    private boolean[][] possibleMoves = new boolean[8][8];
    private ChessPosition selectedPosition;

    public ChessGameController() {
        view = new ChessBoardView(this::handleSquareClick, this::startNewGame);
        refreshView(null);
    }

    public ChessBoardView getView() {
        return view;
    }

    private void handleSquareClick(int row, int column) {
        if (chessMatch.getCheckMate()) {
            return;
        }

        ChessPosition clickedPosition = new ChessPosition((char) ('a' + column), 8 - row);
        if (selectedPosition == null) {
            selectPiece(clickedPosition);
            return;
        }

        if (possibleMoves[row][column]) {
            movePiece(clickedPosition);
        } else {
            selectedPosition = null;
            possibleMoves = new boolean[8][8];
            selectPiece(clickedPosition);
        }
    }

    private void selectPiece(ChessPosition position) {
        try {
            possibleMoves = chessMatch.possibleMoves(position);
            selectedPosition = position;
            refreshView(null);
        } catch (ChessException exception) {
            selectedPosition = null;
            possibleMoves = new boolean[8][8];
            refreshView(exception.getMessage());
        }
    }

    private void movePiece(ChessPosition targetPosition) {
        try {
            ChessPiece capturedPiece = chessMatch.performChessMove(selectedPosition, targetPosition);
            if (capturedPiece != null) {
                capturedPieces.add(capturedPiece);
            }
            if (chessMatch.getPromoted() != null) {
                choosePromotion();
            }
            selectedPosition = null;
            possibleMoves = new boolean[8][8];
            refreshView(null);
        } catch (ChessException exception) {
            selectedPosition = null;
            possibleMoves = new boolean[8][8];
            refreshView(exception.getMessage());
        }
    }

    private void choosePromotion() {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Q", "Q", "R", "B", "H");
        dialog.setTitle("Promocao de peao");
        dialog.setHeaderText("Escolha a peca para promover");
        dialog.setContentText("Peca:");
        Optional<String> choice = dialog.showAndWait();
        choice.ifPresent(chessMatch::replacePromotedPiece);
    }

    private void startNewGame() {
        chessMatch = new ChessMatch();
        capturedPieces.clear();
        selectedPosition = null;
        possibleMoves = new boolean[8][8];
        refreshView(null);
    }

    private void refreshView(String message) {
        view.render(chessMatch.getPieces(), possibleMoves,
                selectedPosition == null ? -1 : 8 - selectedPosition.getRow(),
                selectedPosition == null ? -1 : selectedPosition.getColumn() - 'a');
        view.setCapturedPieces(formatCapturedPieces());
        view.setStatus(message == null ? formatStatus() : message + " | " + formatStatus());
    }

    private String formatStatus() {
        if (chessMatch.getCheckMate()) {
            return "Xeque-mate! Vencedor: " + playerName(chessMatch.getCurrentPlayer());
        }
        String status = "Turno " + chessMatch.getTurn() + " | Vez das " + playerName(chessMatch.getCurrentPlayer());
        return chessMatch.getCheck() ? status + " | XEQUE!" : status;
    }

    private String formatCapturedPieces() {
        List<String> blue = new ArrayList<>();
        List<String> red = new ArrayList<>();
        for (ChessPiece piece : capturedPieces) {
            (piece.getColor() == Color.BLUE ? blue : red).add(piece.toString());
        }
        return "Capturadas - Azuis: " + blue + "   Vermelhas: " + red;
    }

    private String playerName(Color color) {
        return color == Color.BLUE ? "azuis" : "vermelhas";
    }
}
