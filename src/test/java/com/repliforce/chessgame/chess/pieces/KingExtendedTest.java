package com.repliforce.chessgame.chess.pieces;

import com.repliforce.chessgame.boardgame.Board;
import com.repliforce.chessgame.boardgame.Position;
import com.repliforce.chessgame.chess.ChessMatch;
import com.repliforce.chessgame.chess.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KingExtendedTest {

    private Board board;
    private King king;
    private ChessMatch chessMatch;

    @BeforeEach
    public void setUp() {
        board = new Board(8, 8);
        chessMatch = new ChessMatch();
        king = new King(board, Color.BLUE, chessMatch);
    }

    @Test
    void testKingMovesOneSquareInAllDirections() {
        Position position = new Position(4, 4);
        board.placePiece(king, position);

        boolean[][] moves = king.possibleMoves();

        assertTrue(moves[3][3]);
        assertTrue(moves[3][4]);
        assertTrue(moves[3][5]);
        assertTrue(moves[4][3]);
        assertTrue(moves[4][5]);
        assertTrue(moves[5][3]);
        assertTrue(moves[5][4]);
        assertTrue(moves[5][5]);
    }

    @Test
    void testKingCannotMoveMoreThanOneSquare() {
        Position position = new Position(4, 4);
        board.placePiece(king, position);

        boolean[][] moves = king.possibleMoves();

        assertFalse(moves[2][4]);
        assertFalse(moves[1][4]);
    }

    @Test
    void testKingCanCaptureOpponent() {
        Position kingPos = new Position(4, 4);
        board.placePiece(king, kingPos);

        Pawn opponent = new Pawn(board, Color.RED, chessMatch);
        board.placePiece(opponent, new Position(3, 4));

        boolean[][] moves = king.possibleMoves();
        assertTrue(moves[3][4]);
    }

    @Test
    void testKingCannotCaptureAlly() {
        Position kingPos = new Position(4, 4);
        board.placePiece(king, kingPos);

        Pawn ally = new Pawn(board, Color.BLUE, chessMatch);
        board.placePiece(ally, new Position(3, 4));

        boolean[][] moves = king.possibleMoves();

        assertFalse(moves[3][4]);
    }

    @Test
    void testKingToString() {
        assertEquals("K", king.toString());
    }

    @Test
    void testKingEdgePositions() {
        King topKing = new King(board, Color.BLUE, chessMatch);
        board.placePiece(topKing, new Position(0, 4));
        boolean[][] topMoves = topKing.possibleMoves();
        assertTrue(topMoves[1][3]);
        assertTrue(topMoves[1][4]);
        assertTrue(topMoves[1][5]);
        board.removePiece(new Position(0, 4));

        King bottomKing = new King(board, Color.RED, chessMatch);
        board.placePiece(bottomKing, new Position(7, 4));
        boolean[][] bottomMoves = bottomKing.possibleMoves();
        assertTrue(bottomMoves[6][3]);
        assertTrue(bottomMoves[6][4]);
        assertTrue(bottomMoves[6][5]);
    }

    @Test
    void testKingSurroundedByAllies() {
        Position kingPos = new Position(4, 4);
        board.placePiece(king, kingPos);

        for (int r = 3; r <= 5; r++) {
            for (int c = 3; c <= 5; c++) {
                if (r != 4 || c != 4) {
                    Pawn pawn = new Pawn(board, Color.BLUE, chessMatch);
                    board.placePiece(pawn, new Position(r, c));
                }
            }
        }

        boolean[][] moves = king.possibleMoves();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                assertFalse(moves[i][j]);
            }
        }
    }

    @Test
    void testKingSurroundedByEnemies() {
        Position kingPos = new Position(4, 4);
        board.placePiece(king, kingPos);

        for (int r = 3; r <= 5; r++) {
            for (int c = 3; c <= 5; c++) {
                if (r != 4 || c != 4) {
                    Pawn pawn = new Pawn(board, Color.RED, chessMatch);
                    board.placePiece(pawn, new Position(r, c));
                }
            }
        }

        boolean[][] moves = king.possibleMoves();

        assertTrue(moves[3][3]);
        assertTrue(moves[3][4]);
        assertTrue(moves[3][5]);
        assertTrue(moves[4][3]);
        assertTrue(moves[4][5]);
        assertTrue(moves[5][3]);
        assertTrue(moves[5][4]);
        assertTrue(moves[5][5]);
    }

    @Test
    void testKingMixedSurroundings() {
        Position kingPos = new Position(4, 4);
        board.placePiece(king, kingPos);

        Pawn ally1 = new Pawn(board, Color.BLUE, chessMatch);
        board.placePiece(ally1, new Position(3, 4));

        Pawn enemy1 = new Pawn(board, Color.RED, chessMatch);
        board.placePiece(enemy1, new Position(4, 3));

        boolean[][] moves = king.possibleMoves();

        assertFalse(moves[3][4]);
        assertTrue(moves[4][3]);
    }
}