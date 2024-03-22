package src.game;

import src.chess.ChessMatch;
import src.chess.ChessPiece;
import src.chess.ChessPosition;

import java.util.Scanner;

public class ChessMainSystem {

    public static void main(String ... args){

        Scanner scanner = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();

        while (true) {
            UserInterface.printBoard(chessMatch.getPieces());
            System.out.println();
            System.out.print("Source: ");
            ChessPosition source = UserInterface.readChessPosition(scanner);

            System.out.println();
            System.out.print("Target: ");
            ChessPosition target = UserInterface.readChessPosition(scanner);

            ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
        }


    }

}
