package src.game;

import src.chess.ChessMatch;

public class ChessMainSystem {

    public static void main(String ... args){

        System.out.println("Game started!");

        ChessMatch chessMatch = new ChessMatch();
        UserInterface.printBoard(chessMatch.getPieces());

    }

}
