package src.game;

import src.chess.ChessException;
import src.chess.ChessMatch;
import src.chess.ChessPiece;
import src.chess.ChessPosition;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ChessMainSystem {

    public static void main(String ... args){

        Scanner scanner = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();

        while (true) {
            try {
                UserInterface.clearScreen();
                UserInterface.printMatch(chessMatch);
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
            } catch (ChessException | InputMismatchException exception) {
                System.out.println(exception.getMessage());
                scanner.nextLine();
            }
        }


    }

}
