package src.game;

import src.chess.ChessException;
import src.chess.ChessMatch;
import src.chess.ChessPiece;
import src.chess.ChessPosition;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ChessMainSystem {

    public static void main(String ... args){

        Scanner scanner = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();
        List<ChessPiece> capturedPieces = new ArrayList<>();

        while (!chessMatch.getCheckMate()) {
            try {
                UserInterface.clearScreen();
                UserInterface.printMatch(chessMatch, capturedPieces);
                System.out.println();
                System.out.print("Source: ");
                ChessPosition source = UserInterface.readChessPosition(scanner);

                boolean[][] possibleMoves = chessMatch.possibleMoves(source);
                UserInterface.clearScreen();
                UserInterface.printBoard(chessMatch.getPieces(), possibleMoves);
                System.out.println();
                System.out.print("Target: ");
                ChessPosition target = UserInterface.readChessPosition(scanner);

                ChessPiece capturedPiece = chessMatch.performChessMove(source, target);

                if (capturedPiece != null) {
                    capturedPieces.add(capturedPiece);
                }

                if (chessMatch.getPromoted() != null) {
                    System.out.print("Enter piece for promotion (B/H/R/Q): ");
                    String type = scanner.nextLine();
                    chessMatch.replacePromotedPiece(type);
                }
            } catch (ChessException | InputMismatchException exception) {
                System.out.println(exception.getMessage());
                scanner.nextLine();
            }
        }
        UserInterface.clearScreen();
        UserInterface.printMatch(chessMatch, capturedPieces);

    }

}
