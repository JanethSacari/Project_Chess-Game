package com.repliforce.chessgame.chess.pieces;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.repliforce.chessgame.boardgame.Board;
import com.repliforce.chessgame.boardgame.Position;
import com.repliforce.chessgame.chess.Color;
import com.repliforce.chessgame.chess.ChessMatch;

public class PawnTest {

    private Board board;
    private ChessMatch chessMatch;
    private Pawn bluePawn;
    private Pawn redPawn;

    @BeforeEach
    public void setUp() {
        board = new Board(8, 8);
        chessMatch = new ChessMatch();
        bluePawn = new Pawn(board, Color.BLUE, chessMatch);
        redPawn = new Pawn(board, Color.RED, chessMatch);
    }

    @Test
    public void testBluePawnNormalMove() {
        Position initialPosition = new Position(6, 3);
        board.placePiece(bluePawn, initialPosition);

        boolean[][] possibleMoves = bluePawn.possibleMoves();
        assertTrue(possibleMoves[5][3], "Blue Pawn moves one position");
        assertTrue(possibleMoves[4][3], "Blue Pawn moves two times");
    }

    @Test
    public void testRedPawnNormalMove() {
        Position initialPosition = new Position(1, 3);
        board.placePiece(redPawn, initialPosition);

        boolean[][] possibleMoves = redPawn.possibleMoves();
        assertTrue(possibleMoves[2][3], "Red Pawn moves one time ahead.");
        assertTrue(possibleMoves[3][3], "Red Pawn moves two time ahead.");
    }

    @Test
    public void testBluePawnCaptureOpponent() {
        Position bluePawnPosition = new Position(4, 3);
        board.placePiece(bluePawn, bluePawnPosition);

        Position opponentPosition = new Position(3, 2);
        Pawn opponentPawn = new Pawn(board, Color.RED, chessMatch);
        board.placePiece(opponentPawn, opponentPosition);

        boolean[][] possibleMoves = bluePawn.possibleMoves();
        assertTrue(possibleMoves[3][2], "Blue Pawn can capture one Pawn.");
    }

    @Test
    public void testRedPawnCaptureOpponent() {
        Position redPawnPosition = new Position(3, 3);
        board.placePiece(redPawn, redPawnPosition);

        Position opponentPosition = new Position(4, 4);
        Pawn opponentPawn = new Pawn(board, Color.BLUE, chessMatch);
        board.placePiece(opponentPawn, opponentPosition);

        boolean[][] possibleMoves = redPawn.possibleMoves();
        assertTrue(possibleMoves[4][4], "Red Pawn can capture one Blue Pawn.");
    }

    @Test
    public void testBluePawnCannotMoveToOccupiedPosition() {
        Position bluePawnPosition = new Position(6, 3);
        board.placePiece(bluePawn, bluePawnPosition);

        Position blockingPosition = new Position(5, 3);
        Pawn blockingPawn = new Pawn(board, Color.BLUE, chessMatch);
        board.placePiece(blockingPawn, blockingPosition);

        boolean[][] possibleMoves = bluePawn.possibleMoves();
        assertFalse(possibleMoves[5][3], "Blue Pawn can not capture another Blue Pawn.");
    }

    @Test
    public void testRedPawnCannotMoveToOccupiedPosition() {
        Position redPawnPosition = new Position(1, 3);
        board.placePiece(redPawn, redPawnPosition);

        Position blockingPosition = new Position(2, 3);
        Pawn blockingPawn = new Pawn(board, Color.RED, chessMatch);
        board.placePiece(blockingPawn, blockingPosition);

        boolean[][] possibleMoves = redPawn.possibleMoves();
        assertFalse(possibleMoves[2][3], "Red Pawn can not capture another Red Pawn.");
    }
}
