package src;

import boardgame.Board;
import boardgame.Position;

public class ChessMainSystem {

    public static void main(String ... args){

        System.out.println("Game started!");

        Position position = new Position(2, 3);
        System.out.println(position);

        Board board = new Board(8, 8);

    }

}
