package com.repliforce.chessgame.chess.pieces;

import com.repliforce.chessgame.boardgame.Board;
import com.repliforce.chessgame.boardgame.Position;
import com.repliforce.chessgame.chess.ChessMatch;
import com.repliforce.chessgame.chess.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

        assertTrue(moves[0][0]);
        assertTrue(moves[7][7]);
    }

    @Test
    void testBishopDiagonalDirections() {
        Position position = new Position(4, 4);
        board.placePiece(bishop, position);

        boolean[][] moves = bishop.possibleMoves();

        assertTrue(moves[3][3]);
        assertTrue(moves[5][5]);

        assertTrue(moves[3][5]);
        assertTrue(moves[5][3]);
    }

    @Test
    void testBishopBlockedByFriendly() {
        Position bishopPos = new Position(4, 4);
        board.placePiece(bishop, bishopPos);

        Pawn friendly = new Pawn(board, Color.BLUE, chessMatch);
        board.placePiece(friendly, new Position(2, 2));

        boolean[][] moves = bishop.possibleMoves();

        assertTrue(moves[3][3]);
        assertFalse(moves[2][2]);
        assertFalse(moves[1][1]);
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

        assertFalse(moves[4][0]);
        assertFalse(moves[4][7]);
        assertFalse(moves[0][4]);
        assertFalse(moves[7][4]);
    }

    @Test
    void testBishopLongRange() {
        Position bishopPos = new Position(0, 0);
        board.placePiece(bishop, bishopPos);

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

        assertTrue(moves[1][6]);
        assertTrue(moves[7][0]);
    }

    @Test
    void testBishopSurroundedByAllies() {
        Position bishopPos = new Position(4, 4);
        board.placePiece(bishop, bishopPos);

        Pawn p1 = new Pawn(board, Color.BLUE, chessMatch);
        board.placePiece(p1, new Position(3, 3));

        Pawn p2 = new Pawn(board, Color.BLUE, chessMatch);
        board.placePiece(p2, new Position(3, 5));

        Pawn p3 = new Pawn(board, Color.BLUE, chessMatch);
        board.placePiece(p3, new Position(5, 3));

        Pawn p4 = new Pawn(board, Color.BLUE, chessMatch);
        board.placePiece(p4, new Position(5, 5));

        boolean[][] moves = bishop.possibleMoves();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                assertFalse(moves[i][j]);
            }
        }
    }
}