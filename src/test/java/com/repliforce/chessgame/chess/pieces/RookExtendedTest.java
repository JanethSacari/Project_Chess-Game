package com.repliforce.chessgame.chess.pieces;

import com.repliforce.chessgame.boardgame.Board;
import com.repliforce.chessgame.boardgame.Position;
import com.repliforce.chessgame.chess.ChessMatch;
import com.repliforce.chessgame.chess.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RookExtendedTest {

    private Board board;
    private Rook rook;
    private ChessMatch chessMatch;

    @BeforeEach
    public void setUp() {
        board = new Board(8, 8);
        chessMatch = new ChessMatch();
        rook = new Rook(board, Color.BLUE);
    }

    @Test
    void testRookHorizontalMoves() {
        Position position = new Position(4, 4);
        board.placePiece(rook, position);

        boolean[][] moves = rook.possibleMoves();

        assertTrue(moves[4][3]);
        assertTrue(moves[4][2]);
        assertTrue(moves[4][1]);
        assertTrue(moves[4][0]);

        assertTrue(moves[4][5]);
        assertTrue(moves[4][6]);
        assertTrue(moves[4][7]);
    }

    @Test
    void testRookVerticalMoves() {
        Position position = new Position(4, 4);
        board.placePiece(rook, position);

        boolean[][] moves = rook.possibleMoves();

        assertTrue(moves[3][4]);
        assertTrue(moves[2][4]);
        assertTrue(moves[1][4]);
        assertTrue(moves[0][4]);

        assertTrue(moves[5][4]);
        assertTrue(moves[6][4]);
        assertTrue(moves[7][4]);
    }

    @Test
    void testRookBlockedByFriendlyPiece() {
        Position rookPos = new Position(4, 4);
        board.placePiece(rook, rookPos);

        Pawn friendlyPawn = new Pawn(board, Color.BLUE, chessMatch);
        board.placePiece(friendlyPawn, new Position(4, 6));

        boolean[][] moves = rook.possibleMoves();

        assertTrue(moves[4][5]);
        assertFalse(moves[4][6]);
        assertFalse(moves[4][7]);
    }

    @Test
    void testRookCanCaptureOpponent() {
        Position rookPos = new Position(4, 4);
        board.placePiece(rook, rookPos);

        Pawn opponent = new Pawn(board, Color.RED, chessMatch);
        board.placePiece(opponent, new Position(4, 6));

        boolean[][] moves = rook.possibleMoves();

        assertTrue(moves[4][6]);
        assertFalse(moves[4][7]);
    }

    @Test
    void testRookCornerMoves() {
        Position cornerPos = new Position(0, 0);
        board.placePiece(rook, cornerPos);

        boolean[][] moves = rook.possibleMoves();

        assertTrue(moves[0][1]);
        assertTrue(moves[0][7]);

        assertTrue(moves[1][0]);
        assertTrue(moves[7][0]);
    }

    @Test
    void testRookToString() {
        assertEquals("R", rook.toString());
    }

    @Test
    void testRookMovesAlongSingleFile() {
        Position position = new Position(0, 0);
        board.placePiece(rook, position);

        Pawn pawn1 = new Pawn(board, Color.RED, chessMatch);
        board.placePiece(pawn1, new Position(3, 0));

        boolean[][] moves = rook.possibleMoves();

        assertTrue(moves[1][0]);
        assertTrue(moves[2][0]);
        assertTrue(moves[3][0]);
        assertFalse(moves[4][0]);
    }

    @Test
    void testRookMultiplePiecesBlocking() {
        Position rookPos = new Position(3, 3);
        board.placePiece(rook, rookPos);

        Pawn up = new Pawn(board, Color.RED, chessMatch);
        Pawn down = new Pawn(board, Color.RED, chessMatch);
        Pawn left = new Pawn(board, Color.RED, chessMatch);
        Pawn right = new Pawn(board, Color.RED, chessMatch);

        board.placePiece(up, new Position(2, 3));
        board.placePiece(down, new Position(4, 3));
        board.placePiece(left, new Position(3, 2));
        board.placePiece(right, new Position(3, 4));

        boolean[][] moves = rook.possibleMoves();

        assertTrue(moves[2][3]);
        assertTrue(moves[4][3]);
        assertTrue(moves[3][2]);
        assertTrue(moves[3][4]);
        assertFalse(moves[1][3]);
        assertFalse(moves[5][3]);
        assertFalse(moves[3][1]);
        assertFalse(moves[3][5]);
    }

    @Test
    void testRookNoMovesWhenSurrounded() {
        Position rookPos = new Position(3, 3);
        board.placePiece(rook, rookPos);

        for (int i = 0; i < 4; i++) {
            Pawn pawn = new Pawn(board, Color.BLUE, chessMatch);
            switch (i) {
                case 0:
                    board.placePiece(pawn, new Position(2, 3));
                    break;
                case 1:
                    board.placePiece(pawn, new Position(4, 3));
                    break;
                case 2:
                    board.placePiece(pawn, new Position(3, 2));
                    break;
                case 3:
                    board.placePiece(pawn, new Position(3, 4));
                    break;
            }
        }

        boolean[][] moves = rook.possibleMoves();

        assertFalse(moves[2][3]);
        assertFalse(moves[4][3]);
        assertFalse(moves[3][2]);
        assertFalse(moves[3][4]);
    }
}