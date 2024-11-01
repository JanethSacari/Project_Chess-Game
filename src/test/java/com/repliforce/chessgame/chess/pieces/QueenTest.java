package com.repliforce.chessgame.chess.pieces;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.repliforce.chessgame.boardgame.Board;
import com.repliforce.chessgame.boardgame.Position;
import com.repliforce.chessgame.chess.Color;

public class QueenTest {

    private Board board;
    private Queen queen;

    @BeforeEach
    public void setUp() {
        board = new Board(8, 8);
        queen = new Queen(board, Color.BLUE);
        board.placePiece(queen, new Position(3, 3)); // Central position for testing
    }

    @Test
    public void testToString() {
        assertEquals("Q", queen.toString());
    }

    @Test
    public void testPossibleMoves() {
        boolean[][] moves = queen.possibleMoves();

        // All possible moves for a queen in the center of the board (3,3)
        assertTrue(moves[2][3]); // above
        assertTrue(moves[1][3]);
        assertTrue(moves[0][3]);

        assertTrue(moves[3][2]); // left
        assertTrue(moves[3][1]);
        assertTrue(moves[3][0]);

        assertTrue(moves[3][4]); // right
        assertTrue(moves[3][5]);
        assertTrue(moves[3][6]);
        assertTrue(moves[3][7]);

        assertTrue(moves[4][3]); // below
        assertTrue(moves[5][3]);
        assertTrue(moves[6][3]);
        assertTrue(moves[7][3]);

        assertTrue(moves[2][2]); // northwest
        assertTrue(moves[1][1]);
        assertTrue(moves[0][0]);

        assertTrue(moves[2][4]); // northeast
        assertTrue(moves[1][5]);
        assertTrue(moves[0][6]);

        assertTrue(moves[4][4]); // southeast
        assertTrue(moves[5][5]);
        assertTrue(moves[6][6]);
        assertTrue(moves[7][7]);

        assertTrue(moves[4][2]); // southwest
        assertTrue(moves[5][1]);
        assertTrue(moves[6][0]);
    }

    @Test
    public void testBlockedMoves() {
        // Block some of the queen's moves with pieces of the same color
        board.placePiece(new Queen(board, Color.BLUE), new Position(4, 3)); // Block below
        board.placePiece(new Queen(board, Color.BLUE), new Position(3, 4)); // Block right

        boolean[][] moves = queen.possibleMoves();

        // The moves in blocked directions should not be possible
        assertFalse(moves[4][3]); // Blocked below
        assertFalse(moves[3][4]); // Blocked right
    }

    @Test
    public void testCaptureOpponentPiece() {
        // Place an opponent piece for capture
        board.placePiece(new Queen(board, Color.RED), new Position(5, 5)); // Diagonal move

        boolean[][] moves = queen.possibleMoves();

        // Check that the capture is possible
        assertTrue(moves[5][5]); // Capture the piece on (5,5)
    }
}