package com.repliforce.chessgame.chess.pieces;

import com.repliforce.chessgame.boardgame.Board;
import com.repliforce.chessgame.boardgame.Position;
import com.repliforce.chessgame.chess.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KnightTest {

    @Test
    void testPossibleMoves_KnightInCenterOfEmptyBoard() {
        Board board = new Board(8, 8);
        Knight knight = new Knight(board, Color.WHITE);
        Position knightPosition = new Position(4, 4);
        board.placePiece(knight, knightPosition);

        boolean[][] possibleMoves = knight.possibleMoves();

        assertTrue(possibleMoves[3][2]); // 1 up, 2 left
        assertTrue(possibleMoves[2][3]); // 2 up, 1 left
        assertTrue(possibleMoves[2][5]); // 2 up, 1 right
        assertTrue(possibleMoves[3][6]); // 1 up, 2 right
        assertTrue(possibleMoves[5][6]); // 1 down, 2 right
        assertTrue(possibleMoves[6][5]); // 2 down, 1 right
        assertTrue(possibleMoves[6][3]); // 2 down, 1 left
        assertTrue(possibleMoves[5][2]); // 1 down, 2 left
    }

    @Test
    void testPossibleMoves_KnightBlockedBySameColorPieces() {
        Board board = new Board(8, 8);
        Knight knight = new Knight(board, Color.WHITE);
        Position knightPosition = new Position(4, 4);
        board.placePiece(knight, knightPosition);

        board.placePiece(new Rook(board, Color.WHITE), new Position(3, 2)); // 1 up, 2 left
        board.placePiece(new Rook(board, Color.WHITE), new Position(2, 3)); // 2 up, 1 left
        board.placePiece(new Rook(board, Color.WHITE), new Position(2, 5)); // 2 up, 1 right
        board.placePiece(new Rook(board, Color.WHITE), new Position(3, 6)); // 1 up, 2 right
        board.placePiece(new Rook(board, Color.WHITE), new Position(5, 6)); // 1 down, 2 right
        board.placePiece(new Rook(board, Color.WHITE), new Position(6, 5)); // 2 down, 1 right
        board.placePiece(new Rook(board, Color.WHITE), new Position(6, 3)); // 2 down, 1 left
        board.placePiece(new Rook(board, Color.WHITE), new Position(5, 2)); // 1 down, 2 left

        boolean[][] possibleMoves = knight.possibleMoves();

        assertFalse(possibleMoves[3][2]); // 1 up, 2 left
        assertFalse(possibleMoves[2][3]); // 2 up, 1 left
        assertFalse(possibleMoves[2][5]); // 2 up, 1 right
        assertFalse(possibleMoves[3][6]); // 1 up, 2 right
        assertFalse(possibleMoves[5][6]); // 1 down, 2 right
        assertFalse(possibleMoves[6][5]); // 2 down, 1 right
        assertFalse(possibleMoves[6][3]); // 2 down, 1 left
        assertFalse(possibleMoves[5][2]); // 1 down, 2 left
    }

    @Test
    void testPossibleMoves_KnightCapturingOpponentPieces() {
        Board board = new Board(8, 8);
        Knight knight = new Knight(board, Color.WHITE);
        Position knightPosition = new Position(4, 4);
        board.placePiece(knight, knightPosition);

        board.placePiece(new Rook(board, Color.BLACK), new Position(3, 2)); // 1 up, 2 left
        board.placePiece(new Rook(board, Color.BLACK), new Position(2, 3)); // 2 up, 1 left
        board.placePiece(new Rook(board, Color.BLACK), new Position(2, 5)); // 2 up, 1 right
        board.placePiece(new Rook(board, Color.BLACK), new Position(3, 6)); // 1 up, 2 right
        board.placePiece(new Rook(board, Color.BLACK), new Position(5, 6)); // 1 down, 2 right
        board.placePiece(new Rook(board, Color.BLACK), new Position(6, 5)); // 2 down, 1 right
        board.placePiece(new Rook(board, Color.BLACK), new Position(6, 3)); // 2 down, 1 left
        board.placePiece(new Rook(board, Color.BLACK), new Position(5, 2)); // 1 down, 2 left

        boolean[][] possibleMoves = knight.possibleMoves();

        assertTrue(possibleMoves[3][2]); // 1 up, 2 left
        assertTrue(possibleMoves[2][3]); // 2 up, 1 left
        assertTrue(possibleMoves[2][5]); // 2 up, 1 right
        assertTrue(possibleMoves[3][6]); // 1 up, 2 right
        assertTrue(possibleMoves[5][6]); // 1 down, 2 right
        assertTrue(possibleMoves[6][5]); // 2 down, 1 right
        assertTrue(possibleMoves[6][3]); // 2 down, 1 left
        assertTrue(possibleMoves[5][2]); // 1 down, 2 left
    }

    @Test
    void testPossibleMoves_KnightAtEdgeOfBoard() {
        Board board = new Board(8, 8);
        Knight knight = new Knight(board, Color.WHITE);
        Position knightPosition = new Position(0, 0);
        board.placePiece(knight, knightPosition);

        boolean[][] possibleMoves = knight.possibleMoves();

        assertTrue(possibleMoves[1][2]); // 1 down, 2 right
        assertTrue(possibleMoves[2][1]); // 2 down, 1 right
    }

    @Test
    void testPossibleMoves_KnightAtEdgeWithBlockedMoves() {
        Board board = new Board(8, 8);
        Knight knight = new Knight(board, Color.WHITE);
        Position knightPosition = new Position(0, 0);
        board.placePiece(knight, knightPosition);

        board.placePiece(new Rook(board, Color.WHITE), new Position(1, 2)); // 1 down, 2 right
        board.placePiece(new Rook(board, Color.WHITE), new Position(2, 1)); // 2 down, 1 right

        boolean[][] possibleMoves = knight.possibleMoves();

        assertFalse(possibleMoves[1][2]); // 1 down, 2 right
        assertFalse(possibleMoves[2][1]); // 2 down, 1 right
    }
}