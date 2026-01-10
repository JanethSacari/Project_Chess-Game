package com.repliforce.chessgame.chess.pieces;

import com.repliforce.chessgame.boardgame.Board;
import com.repliforce.chessgame.boardgame.Position;
import com.repliforce.chessgame.chess.ChessMatch;
import com.repliforce.chessgame.chess.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueenExtendedTest {

    private Board board;
    private Queen queen;
    private ChessMatch chessMatch;

    @BeforeEach
    public void setUp() {
        board = new Board(8, 8);
        chessMatch = new ChessMatch();
        queen = new Queen(board, Color.BLUE);
    }

    @Test
    void testQueenRookLikeMoves() {
        Position position = new Position(4, 4);
        board.placePiece(queen, position);

        boolean[][] moves = queen.possibleMoves();

        assertTrue(moves[4][0]);
        assertTrue(moves[4][7]);

        assertTrue(moves[0][4]);
        assertTrue(moves[7][4]);
    }

    @Test
    void testQueenBishopLikeMoves() {
        Position position = new Position(4, 4);
        board.placePiece(queen, position);

        boolean[][] moves = queen.possibleMoves();

        assertTrue(moves[1][1]);
        assertTrue(moves[7][7]);

        assertTrue(moves[1][7]);
        assertTrue(moves[7][1]);
    }

    @Test
    void testQueenCanCaptureOpponent() {
        Position queenPos = new Position(4, 4);
        board.placePiece(queen, queenPos);

        Pawn opponent = new Pawn(board, Color.RED, chessMatch);
        board.placePiece(opponent, new Position(4, 7));

        boolean[][] moves = queen.possibleMoves();

        assertTrue(moves[4][7]);
    }

    @Test
    void testQueenCannotCaptureAlly() {
        Position queenPos = new Position(4, 4);
        board.placePiece(queen, queenPos);

        Pawn ally = new Pawn(board, Color.BLUE, chessMatch);
        board.placePiece(ally, new Position(4, 6));

        boolean[][] moves = queen.possibleMoves();

        assertTrue(moves[4][5]);
        assertFalse(moves[4][6]);
        assertFalse(moves[4][7]);
    }

    @Test
    void testQueenBlockedByFriendlyPiece() {
        Position queenPos = new Position(4, 4);
        board.placePiece(queen, queenPos);

        Pawn friendly = new Pawn(board, Color.BLUE, chessMatch);
        board.placePiece(friendly, new Position(2, 2));

        boolean[][] moves = queen.possibleMoves();

        assertTrue(moves[3][3]);
        assertFalse(moves[2][2]);
        assertFalse(moves[1][1]);
    }

    @Test
    void testQueenFromCorner() {
        Position cornerPos = new Position(0, 0);
        board.placePiece(queen, cornerPos);

        boolean[][] moves = queen.possibleMoves();

        assertTrue(moves[0][7]);
        assertTrue(moves[7][0]);
        assertTrue(moves[7][7]);
    }

    @Test
    void testQueenToString() {
        assertEquals("Q", queen.toString());
    }

    @Test
    void testQueenMoveCountIncreases() {
        assertEquals(0, queen.getMoveCount());
        queen.increaseMoveCount();
        assertEquals(1, queen.getMoveCount());
    }

    @Test
    void testQueenAllEightDirections() {
        Position position = new Position(3, 3);
        board.placePiece(queen, position);

        boolean[][] moves = queen.possibleMoves();

        assertTrue(moves[0][3]);
        assertTrue(moves[7][3]);
        assertTrue(moves[3][0]);
        assertTrue(moves[3][7]);
        assertTrue(moves[0][0]);
        assertTrue(moves[0][6]);
        assertTrue(moves[7][7]);
    }

    @Test
    void testQueenBlockedInMultipleDirections() {
        Position queenPos = new Position(3, 3);
        board.placePiece(queen, queenPos);

        Pawn rightPawn = new Pawn(board, Color.BLUE, chessMatch);
        board.placePiece(rightPawn, new Position(3, 5));

        Pawn diagPawn = new Pawn(board, Color.BLUE, chessMatch);
        board.placePiece(diagPawn, new Position(5, 5));

        boolean[][] moves = queen.possibleMoves();

        assertTrue(moves[3][4]);
        assertFalse(moves[3][5]);
        assertFalse(moves[3][6]);

        assertTrue(moves[4][4]);
        assertFalse(moves[5][5]);
        assertFalse(moves[6][6]);
    }

    @Test
    void testQueenLimitedMoves() {
        Position queenPos = new Position(0, 0);
        board.placePiece(queen, queenPos);

        Pawn pawn = new Pawn(board, Color.BLUE, chessMatch);
        board.placePiece(pawn, new Position(1, 1));

        boolean[][] moves = queen.possibleMoves();

        assertTrue(moves[1][0]);
        assertTrue(moves[0][1]);
        assertFalse(moves[1][1]);
    }

    @Test
    void testQueenCapturesAndStops() {
        Position queenPos = new Position(3, 3);
        board.placePiece(queen, queenPos);

        Pawn enemy = new Pawn(board, Color.RED, chessMatch);
        board.placePiece(enemy, new Position(3, 6));

        Pawn ally = new Pawn(board, Color.BLUE, chessMatch);
        board.placePiece(ally, new Position(3, 7));

        boolean[][] moves = queen.possibleMoves();

        assertTrue(moves[3][6]);
        assertFalse(moves[3][7]);
    }
}