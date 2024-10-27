package com.repliforce.chessgame.chess.pieces;

import com.repliforce.chessgame.boardgame.Board;
import com.repliforce.chessgame.boardgame.Position;
import com.repliforce.chessgame.chess.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RookTest {

    private Board board;
    private Rook rook;

    @BeforeEach
    void setUp() {
        board = new Board(8, 8);
        rook = new Rook(board, Color.WHITE);
        board.placePiece(rook, new Position(4, 4));
    }

    @Test
    void testRookPossibleMoves_CenterOfBoard() {
        boolean[][] moves = rook.possibleMoves();

        for (int i = 3; i >= 0; i--) {
            assertTrue(moves[i][4], "Rook should be able to move upwards.");
        }

        for (int i = 5; i <= 7; i++) {
            assertTrue(moves[i][4], "Rook should be able to move downwards.");
        }

        for (int i = 3; i >= 0; i--) {
            assertTrue(moves[4][i], "Rook should be able to move left.");
        }

        for (int i = 5; i <= 7; i++) {
            assertTrue(moves[4][i], "Rook should be able to move right.");
        }
    }

    @Test
    void testRookBlockedBySameColorPiece() {
        board.placePiece(new Rook(board, Color.WHITE), new Position(3, 4));
        board.placePiece(new Rook(board, Color.WHITE), new Position(5, 4));
        board.placePiece(new Rook(board, Color.WHITE), new Position(4, 3));
        board.placePiece(new Rook(board, Color.WHITE), new Position(4, 5));

        boolean[][] moves = rook.possibleMoves();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                assertFalse(moves[i][j], "Rook should not move when blocked by own pieces.");
            }
        }
    }

    @Test
    void testRookCaptureOpponentPiece() {
        board.placePiece(new Rook(board, Color.BLACK), new Position(3, 4));
        board.placePiece(new Rook(board, Color.BLACK), new Position(5, 4));
        board.placePiece(new Rook(board, Color.BLACK), new Position(4, 3));
        board.placePiece(new Rook(board, Color.BLACK), new Position(4, 5));

        boolean[][] moves = rook.possibleMoves();

        assertTrue(moves[3][4], "Rook should capture the piece above.");
        assertTrue(moves[5][4], "Rook should capture the piece below.");
        assertTrue(moves[4][3], "Rook should capture the piece to the left.");
        assertTrue(moves[4][5], "Rook should capture the piece to the right.");
    }

    @Test
    void testRookAtBoardEdge() {
        board.placePiece(rook, new Position(7, 0));

        boolean[][] moves = rook.possibleMoves();

        for (int i = 6; i >= 0; i--) {
            assertTrue(moves[i][0], "Rook should move upwards.");
        }
        for (int i = 1; i <= 7; i++) {
            assertTrue(moves[7][i], "Rook should move right.");
        }
    }
}