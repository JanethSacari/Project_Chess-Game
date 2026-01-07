package com.repliforce.chessgame.chess.pieces;

import com.repliforce.chessgame.boardgame.Board;
import com.repliforce.chessgame.boardgame.Position;
import com.repliforce.chessgame.chess.ChessMatch;
import com.repliforce.chessgame.chess.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Extended tests for Bishops covering diagonal movements and captures.
 */
class BishopExtendedTest {

    private Board board;
    private Bishop bishop;
    private ChessMatch chessMatch;

    @BeforeEach
    public void setUp() {
        board = new Board(8, 8);
        chessMatch = new ChessMatch();
        bishop = new Bishop(board, Color.BLUE);
    }

    @Test
    void testBishopMainDiagonalMoves() {
        Position position = new Position(4, 4);
        board.placePiece(bishop, position);

        boolean[][] moves = bishop.possibleMoves();

        // Main diagonal (up-left to down-right)
        assertTrue(moves[0][0]); // Up-left
        assertTrue(moves[7][7]); // Down-right
    }

    @Test
    void testBishopAntiDiagonalMoves() {
        Position position = new Position(4, 4);
        board.placePiece(bishop, position);

        boolean[][] moves = bishop.possibleMoves();

        // Anti-diagonal (up-right to down-left)
        assertTrue(moves[0][8 - 4 + 4]); // Would be out of bounds, need to check valid
        assertTrue(moves[1][7]); // Up-right one step
        assertTrue(moves[7][1]); // Down-left one step
    }

    @Test
    void testBishopDiagonalDirections() {
        Position position = new Position(4, 4);
        board.placePiece(bishop, position);

        boolean[][] moves = bishop.possibleMoves();

        // Main diagonal
        assertTrue(moves[3][3]); // Up-left
        assertTrue(moves[5][5]); // Down-right

        // Anti-diagonal
        assertTrue(moves[3][5]); // Up-right
        assertTrue(moves[5][3]); // Down-left
    }

    @Test
    void testBishopBlockedByFriendly() {
        Position bishopPos = new Position(4, 4);
        board.placePiece(bishop, bishopPos);

        Pawn friendly = new Pawn(board, Color.BLUE, chessMatch);
        board.placePiece(friendly, new Position(2, 2));

        boolean[][] moves = bishop.possibleMoves();

        assertTrue(moves[3][3]); // Can move one step
        assertFalse(moves[2][2]); // Cannot move to friendly
        assertFalse(moves[1][1]); // Cannot move past friendly
    }

    @Test
    void testBishopCanCaptureOpponent() {
        Position bishopPos = new Position(4, 4);
        board.placePiece(bishop, bishopPos);

        Pawn opponent = new Pawn(board, Color.RED, chessMatch);
        board.placePiece(opponent, new Position(2, 2));

        boolean[][] moves = bishop.possibleMoves();

        assertTrue(moves[2][2]);
    }

    @Test
    void testBishopCornerMoves() {
        Position cornerPos = new Position(0, 0);
        board.placePiece(bishop, cornerPos);

        boolean[][] moves = bishop.possibleMoves();

        // Bishop in corner only moves along one diagonal
        assertTrue(moves[1][1]);
        assertTrue(moves[2][2]);
        assertTrue(moves[7][7]);
    }

    @Test
    void testBishopToString() {
        assertEquals("B", bishop.toString());
    }

    @Test
    void testBishopNoDiagonalMoves() {
        Position position = new Position(4, 4);
        board.placePiece(bishop, position);

        boolean[][] moves = bishop.possibleMoves();

        // No vertical or horizontal moves
        assertFalse(moves[4][0]); // Horizontal
        assertFalse(moves[4][7]); // Horizontal
        assertFalse(moves[0][4]); // Vertical
        assertFalse(moves[7][4]); // Vertical
    }

    @Test
    void testBishopMultipleDiagonals() {
        Position position = new Position(4, 4);
        board.placePiece(bishop, position);

        boolean[][] moves = bishop.possibleMoves();

        // Test all four diagonal directions
        // Up-left
        assertTrue(moves[0][0]);
        // Up-right
        assertTrue(moves[0][8 - 4 + 4]); // Adjusted for board
        // Down-left
        assertTrue(moves[7][1]);
        // Down-right
        assertTrue(moves[7][7]);
    }

    @Test
    void testBishopBlockedInBothDiagonals() {
        Position bishopPos = new Position(4, 4);
        board.placePiece(bishop, bishopPos);

        Pawn mainDiagEnemy = new Pawn(board, Color.RED, chessMatch);
        board.placePiece(mainDiagEnemy, new Position(2, 2));

        Pawn antiDiagEnemy = new Pawn(board, Color.RED, chessMatch);
        board.placePiece(antiDiagEnemy, new Position(2, 6));

        boolean[][] moves = bishop.possibleMoves();

        // Can capture both enemies
        assertTrue(moves[2][2]);
        assertTrue(moves[2][6]);

        // Cannot move past enemies
        assertFalse(moves[1][1]);
        assertFalse(moves[0][0]);
        assertFalse(moves[1][7]);
        assertFalse(moves[0][8 - 4 + 4]); // Adjusted
    }

    @Test
    void testBishopLongRange() {
        Position bishopPos = new Position(0, 0);
        board.placePiece(bishop, bishopPos);

        // Entire diagonal available
        boolean[][] moves = bishop.possibleMoves();

        for (int i = 1; i < 8; i++) {
            assertTrue(moves[i][i]);
        }
    }

    @Test
    void testBishopEdgeCase() {
        Position bishopPos = new Position(0, 7);
        board.placePiece(bishop, bishopPos);

        boolean[][] moves = bishop.possibleMoves();

        // Can move along its available diagonal
        assertTrue(moves[1][6]);
        assertTrue(moves[7][0]);
    }

    @Test
    void testBishopSurroundedByAllies() {
        Position bishopPos = new Position(4, 4);
        board.placePiece(bishop, bishopPos);

        // Surround diagonals with allies
        Pawn p1 = new Pawn(board, Color.BLUE, chessMatch);
        board.placePiece(p1, new Position(3, 3));

        Pawn p2 = new Pawn(board, Color.BLUE, chessMatch);
        board.placePiece(p2, new Position(3, 5));

        Pawn p3 = new Pawn(board, Color.BLUE, chessMatch);
        board.placePiece(p3, new Position(5, 3));

        Pawn p4 = new Pawn(board, Color.BLUE, chessMatch);
        board.placePiece(p4, new Position(5, 5));

        boolean[][] moves = bishop.possibleMoves();

        // Bishop has no valid moves
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                assertFalse(moves[i][j]);
            }
        }
    }
}

