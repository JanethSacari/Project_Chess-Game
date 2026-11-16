package com.repliforce.chessgame.game.fx;

import com.repliforce.chessgame.chess.ChessPiece;
import com.repliforce.chessgame.chess.Color;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.function.BiConsumer;

public class ChessBoardView extends BorderPane {

    private static final String LIGHT_SQUARE = "#f0d9b5";
    private static final String DARK_SQUARE = "#b58863";
    private static final String POSSIBLE_SQUARE = "#a9c66e";
    private static final String SELECTED_SQUARE = "#f6f669";

    private final Button[][] squares = new Button[8][8];
    private final Label statusLabel = new Label();
    private final Label capturedLabel = new Label();

    public ChessBoardView(BiConsumer<Integer, Integer> squareAction, Runnable newGameAction) {
        setPadding(new Insets(24));
        setStyle("-fx-background-color: #202a35;");

        Label title = new Label("CHESS");
        title.setStyle("-fx-text-fill: #f4f1ea; -fx-font-size: 24px; -fx-font-weight: bold;");
        statusLabel.setStyle("-fx-text-fill: #f4f1ea; -fx-font-size: 15px;");
        VBox header = new VBox(8, title, statusLabel);
        header.setPadding(new Insets(0, 0, 18, 0));
        setTop(header);

        GridPane board = new GridPane();
        board.setAlignment(Pos.CENTER);
        board.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.35), 16, 0, 0, 5);");
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                Button square = new Button();
                square.setMinSize(76, 76);
                square.setPrefSize(76, 76);
                square.setMaxSize(76, 76);
                square.setFocusTraversable(false);
                final int squareRow = row;
                final int squareColumn = column;
                square.setOnAction(event -> squareAction.accept(squareRow, squareColumn));
                squares[row][column] = square;
                board.add(square, column, row);
            }
        }
        setCenter(board);

        capturedLabel.setStyle("-fx-text-fill: #d7dde4; -fx-font-size: 14px;");
        Button newGameButton = new Button("Nova partida");
        newGameButton.setOnAction(event -> newGameAction.run());
        newGameButton.setStyle("-fx-background-color: #d5a74f; -fx-text-fill: #202a35; -fx-font-weight: bold;");
        HBox footer = new HBox(18, capturedLabel, newGameButton);
        footer.setAlignment(Pos.CENTER_LEFT);
        footer.setPadding(new Insets(18, 0, 0, 0));
        setBottom(footer);
    }

    public void render(ChessPiece[][] pieces, boolean[][] possibleMoves, int selectedRow, int selectedColumn) {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                Button square = squares[row][column];
                ChessPiece piece = pieces[row][column];
                square.setText(piece == null ? (possibleMoves[row][column] ? "•" : "") : piece.toString());

                String background = (row + column) % 2 == 0 ? LIGHT_SQUARE : DARK_SQUARE;
                if (possibleMoves[row][column]) {
                    background = POSSIBLE_SQUARE;
                }
                if (row == selectedRow && column == selectedColumn) {
                    background = SELECTED_SQUARE;
                }

                String pieceColor = piece == null ? "#202a35"
                        : piece.getColor() == Color.BLUE ? "#2457a6" : "#b33939";
                square.setStyle("-fx-background-color: " + background
                        + "; -fx-text-fill: " + pieceColor
                        + "; -fx-font-size: 34px; -fx-font-weight: bold;"
                        + " -fx-background-radius: 0; -fx-border-radius: 0; -fx-padding: 0;");
            }
        }
    }

    public void setStatus(String status) {
        statusLabel.setText(status);
    }

    public void setCapturedPieces(String capturedPieces) {
        capturedLabel.setText(capturedPieces);
    }
}
