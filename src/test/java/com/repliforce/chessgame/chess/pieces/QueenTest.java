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

    @Test
    void queenCanMoveRightUntilBlockedByPiece() {
        board.placePiece(new Queen(board, Color.BLUE), new Position(3, 6)); // Block at (3,6)
        boolean[][] moves = queen.possibleMoves();
        assertTrue(moves[3][4]);
        assertTrue(moves[3][5]);
        assertFalse(moves[3][6]);
        assertFalse(moves[3][7]);
    }

    @Test
    void queenCanCaptureOpponentOnRight() {
        board.placePiece(new Queen(board, Color.RED), new Position(3, 5)); // Opponent at (3,5)
        boolean[][] moves = queen.possibleMoves();
        assertTrue(moves[3][4]);
        assertTrue(moves[3][5]);
        assertFalse(moves[3][6]);
    }

    @Test
    void queenCannotMoveRightOutOfBounds() {
        board.placePiece(new Queen(board, Color.BLUE), new Position(3, 7)); // At the edge
        boolean[][] moves = queen.possibleMoves();
        assertFalse(moves[3][7]);
    }

    @Test
    void queenCanMoveDownUntilBlockedByPiece() {
        board.placePiece(new Queen(board, Color.BLUE), new Position(6, 3)); // Block at (6,3)
        boolean[][] moves = queen.possibleMoves();
        assertTrue(moves[4][3]);
        assertTrue(moves[5][3]);
        assertFalse(moves[6][3]);
        assertFalse(moves[7][3]);
    }

    @Test
    void queenCanCaptureOpponentBelow() {
        board.placePiece(new Queen(board, Color.RED), new Position(5, 3)); // Opponent at (5,3)
        boolean[][] moves = queen.possibleMoves();
        assertTrue(moves[4][3]);
        assertTrue(moves[5][3]);
        assertFalse(moves[6][3]);
    }

    @Test
    void queenCannotMoveDownOutOfBounds() {
        board.placePiece(new Queen(board, Color.BLUE), new Position(7, 3)); // At the edge
        boolean[][] moves = queen.possibleMoves();
        assertFalse(moves[7][3]);
    }

    @Test
    void queenCanCaptureOpponentOnNorthwestDiagonal() {
        board.placePiece(new Queen(board, Color.RED), new Position(1, 1)); // Opponent at (1,1)
        boolean[][] moves = queen.possibleMoves();
        assertTrue(moves[2][2]);
        assertTrue(moves[1][1]);
        assertFalse(moves[0][0]);
    }

    @Test
    void queenCannotMoveNorthwestOutOfBounds() {
        board.placePiece(new Queen(board, Color.BLUE), new Position(0, 0)); // At the edge
        boolean[][] moves = queen.possibleMoves();
        assertFalse(moves[0][0]);
    }

    @Test
    void queenCanCaptureOpponentOnNortheastDiagonal() {
        board.placePiece(new Queen(board, Color.RED), new Position(1, 5)); // Opponent at (1,5)
        boolean[][] moves = queen.possibleMoves();
        assertTrue(moves[2][4]);
        assertTrue(moves[1][5]);
        assertFalse(moves[0][6]);
    }

    @Test
    void queenCannotMoveNortheastOutOfBounds() {
        board.placePiece(new Queen(board, Color.BLUE), new Position(0, 7)); // At the edge
        boolean[][] moves = queen.possibleMoves();
        assertFalse(moves[0][7]);
    }

    @Test
    void queenCanMoveAboveUntilBlockedByPiece() {
        board.placePiece(new Queen(board, Color.BLUE), new Position(1, 3)); // Block at (1,3)
        boolean[][] moves = queen.possibleMoves();
        assertTrue(moves[2][3]);
        assertFalse(moves[1][3]);
        assertFalse(moves[0][3]);
    }

    @Test
    void queenCanCaptureOpponentAbove() {
        board.placePiece(new Queen(board, Color.RED), new Position(1, 3)); // Opponent at (1,3)
        boolean[][] moves = queen.possibleMoves();
        assertTrue(moves[2][3]);
        assertTrue(moves[1][3]);
        assertFalse(moves[0][3]);
    }

    @Test
    void queenCannotMoveAboveOutOfBounds() {
        board.placePiece(new Queen(board, Color.BLUE), new Position(0, 3)); // At the edge
        boolean[][] moves = queen.possibleMoves();
        assertFalse(moves[0][3]);
    }

    @Test
    void queenCanMoveLeftUntilBlockedByPiece() {
        board.placePiece(new Queen(board, Color.BLUE), new Position(3, 1)); // Block at (3,1)
        boolean[][] moves = queen.possibleMoves();
        assertTrue(moves[3][2]);
        assertFalse(moves[3][1]);
        assertFalse(moves[3][0]);
    }

    @Test
    void queenCanCaptureOpponentOnLeft() {
        board.placePiece(new Queen(board, Color.RED), new Position(3, 1)); // Opponent at (3,1)
        boolean[][] moves = queen.possibleMoves();
        assertTrue(moves[3][2]);
        assertTrue(moves[3][1]);
        assertFalse(moves[3][0]);
    }

    @Test
    void queenCannotMoveLeftOutOfBounds() {
        board.placePiece(new Queen(board, Color.BLUE), new Position(3, 0)); // At the edge
        boolean[][] moves = queen.possibleMoves();
        assertFalse(moves[3][0]);
    }

    @Test
    void queenCanCaptureOpponentOnSouthwestDiagonal() {
        board.placePiece(new Queen(board, Color.RED), new Position(5, 1)); // Opponent at (5,1)
        boolean[][] moves = queen.possibleMoves();
        assertTrue(moves[4][2]);
        assertTrue(moves[5][1]);
        assertFalse(moves[6][0]);
    }

    @Test
    void queenCannotMoveSouthwestOutOfBounds() {
        board.placePiece(new Queen(board, Color.BLUE), new Position(7, 0)); // At the edge
        boolean[][] moves = queen.possibleMoves();
        assertFalse(moves[7][0]);
    }
}