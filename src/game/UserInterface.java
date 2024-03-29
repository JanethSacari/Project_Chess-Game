package src.game;

import src.chess.ChessMatch;
import src.chess.ChessPiece;
import src.chess.ChessPosition;
import src.chess.Color;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UserInterface {

    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    // https://stackoverflow.com/questions/2979383/java-clear-the-console
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static ChessPosition readChessPosition(Scanner scanner) {
        try {
            String positionString = scanner.nextLine();
            char column = positionString.charAt(0);
            int row = Integer.parseInt(positionString.substring(1));
            return new ChessPosition(column, row);
        } catch (RuntimeException exception) {
            throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8.");
        }
    }

    public static void printMatch(ChessMatch chessMatch, List<ChessPiece> capturedPieces) {
        printBoard(chessMatch.getPieces());
        System.out.println();
        printCapturedPieces(capturedPieces);
        System.out.println();
        System.out.println("Turn: " + chessMatch.getTurn());
        System.out.println("Waiting player: " + chessMatch.getCurrentPlayer());
        if (chessMatch.getCheck()) {
            System.out.println("CHECK!");
        }
    }

    public static void printBoard(ChessPiece[][] pieces) {
        for (int i=0; i<pieces.length; i++) {
            System.out.print((8 - i) + " ");
            for (int j=0; j<pieces.length; j++){
                printPiece(pieces[i][j], false);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
        for (int i=0; i<pieces.length; i++) {
            System.out.print((8 - i) + " ");
            for (int j=0; j<pieces.length; j++){
                printPiece(pieces[i][j], possibleMoves[i][j]);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    private static void printPiece(ChessPiece piece, boolean background) {
        if (background) {
            System.out.print(ANSI_PURPLE_BACKGROUND);
        }
        if (piece == null) {
            System.out.print("-" + ANSI_RESET);
        } else {
            if (piece.getColor() == Color.RED) {
                System.out.print(ANSI_RED + piece + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_BLUE + piece + ANSI_RESET);
            }
        }
        System.out.print(" ");
    }

    private static void printCapturedPieces(List<ChessPiece> capturedPieces) {
        List<ChessPiece> bluePieces = capturedPieces.stream().filter(x -> x.getColor() == Color.BLUE).collect(Collectors.toList());
        List<ChessPiece> redPieces = capturedPieces.stream().filter(x -> x.getColor() == Color.RED).collect(Collectors.toList());
        System.out.println("Captured pieces: ");
        System.out.print("Blue: ");
        System.out.print(ANSI_BLUE);
        System.out.println(Arrays.toString(bluePieces.toArray()));
        System.out.print(ANSI_RESET);
        System.out.print("Red: ");
        System.out.print(ANSI_RED);
        System.out.println(Arrays.toString(redPieces.toArray()));
        System.out.print(ANSI_RESET);
    }

}
