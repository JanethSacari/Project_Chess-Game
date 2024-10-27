package com.repliforce.chessgame.chess.pieces;

import com.repliforce.chessgame.boardgame.Board;
import com.repliforce.chessgame.boardgame.Position;
import com.repliforce.chessgame.chess.ChessPiece;
import com.repliforce.chessgame.chess.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BishopTest {

    @Test
    void testPossibleMoves_BishopInCenterOfEmptyBoard() {
        Board board = new Board(8, 8);
        Bishop bishop = new Bishop(board, Color.WHITE);
        Position bishopPosition = new Position(4, 4);
        board.placePiece(bishop, bishopPosition);

        boolean[][] possibleMoves = bishop.possibleMoves();

        assertTrue(possibleMoves[3][3]); // NW diagonal
        assertTrue(possibleMoves[2][2]);
        assertTrue(possibleMoves[1][1]);
        assertTrue(possibleMoves[0][0]);

        assertTrue(possibleMoves[3][5]); // NE diagonal
        assertTrue(possibleMoves[2][6]);
        assertTrue(possibleMoves[1][7]);

        assertTrue(possibleMoves[5][5]); // SE diagonal
        assertTrue(possibleMoves[6][6]);
        assertTrue(possibleMoves[7][7]);

        assertTrue(possibleMoves[5][3]); // SW diagonal
        assertTrue(possibleMoves[6][2]);
        assertTrue(possibleMoves[7][1]);
    }

    @Test
    void testPossibleMoves_BishopBlockedBySameColorPieces() {
        Board board = new Board(8, 8);
        Bishop bishop = new Bishop(board, Color.WHITE);
        Position bishopPosition = new Position(4, 4);
        board.placePiece(bishop, bishopPosition);

        ChessPiece blockingPiece1 = new Bishop(board, Color.WHITE);
        ChessPiece blockingPiece2 = new Bishop(board, Color.WHITE);
        board.placePiece(blockingPiece1, new Position(3, 3)); // NW diagonal
        board.placePiece(blockingPiece2, new Position(5, 5)); // SE diagonal

        // Act
        boolean[][] possibleMoves = bishop.possibleMoves();

        // Assert
        // NW diagonal should be blocked by (3,3)
        assertFalse(possibleMoves[3][3]);
        assertFalse(possibleMoves[2][2]);

        // NE diagonal should be unblocked
        assertTrue(possibleMoves[3][5]);
        assertTrue(possibleMoves[2][6]);

        // SE diagonal should be blocked by (5,5)
        assertFalse(possibleMoves[5][5]);
        assertFalse(possibleMoves[6][6]);

        // SW diagonal should be unblocked
        assertTrue(possibleMoves[5][3]);
        assertTrue(possibleMoves[6][2]);
    }

    @Test
    void testPossibleMoves_BishopCapturingOpponentPieces() {
        Board board = new Board(8, 8);
        Bishop bishop = new Bishop(board, Color.WHITE);
        Position bishopPosition = new Position(4, 4);
        board.placePiece(bishop, bishopPosition);

        ChessPiece opponentPiece1 = new Bishop(board, Color.RED);
        ChessPiece opponentPiece2 = new Bishop(board, Color.RED);
        board.placePiece(opponentPiece1, new Position(3, 3)); // NW diagonal
        board.placePiece(opponentPiece2, new Position(5, 5)); // SE diagonal

        // Act
        boolean[][] possibleMoves = bishop.possibleMoves();

        assertTrue(possibleMoves[3][3]);

        assertTrue(possibleMoves[5][5]);

        assertTrue(possibleMoves[3][5]);
        assertTrue(possibleMoves[2][6]);
        assertTrue(possibleMoves[5][3]);
        assertTrue(possibleMoves[6][2]);
    }

}
