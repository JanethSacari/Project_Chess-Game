package com.repliforce.chessgame.chess.pieces;

import com.repliforce.chessgame.boardgame.Board;
import com.repliforce.chessgame.boardgame.Position;
import com.repliforce.chessgame.chess.ChessMatch;
import com.repliforce.chessgame.chess.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class KingTest {

    @Test
    void testPossibleMoves_KingInCenterOfEmptyBoard() {
        // Arrange
        Board board = new Board(8, 8);
        ChessMatch chessMatch = new ChessMatch();
        King king = new King(board, Color.WHITE, chessMatch);
        Position kingPosition = new Position(4, 4); // Center of the board
        board.placePiece(king, kingPosition);

        // Act
        boolean[][] possibleMoves = king.possibleMoves();

        // Assert
        // The king can move one square in any direction
        assertTrue(possibleMoves[3][4]); // above
        assertTrue(possibleMoves[5][4]); // below
        assertTrue(possibleMoves[4][3]); // left
        assertTrue(possibleMoves[4][5]); // right
        assertTrue(possibleMoves[3][3]); // nw
        assertTrue(possibleMoves[3][5]); // ne
        assertTrue(possibleMoves[5][3]); // sw
        assertTrue(possibleMoves[5][5]); // se
    }

    @Test
    void testPossibleMoves_KingBlockedBySameColorPieces() {
        // Arrange
        Board board = new Board(8, 8);
        ChessMatch chessMatch = new ChessMatch();
        King king = new King(board, Color.WHITE, chessMatch);
        Position kingPosition = new Position(4, 4);
        board.placePiece(king, kingPosition);

        // Block surrounding positions with same color pieces
        board.placePiece(new Rook(board, Color.WHITE), new Position(3, 4)); // above
        board.placePiece(new Rook(board, Color.WHITE), new Position(5, 4)); // below
        board.placePiece(new Rook(board, Color.WHITE), new Position(4, 3)); // left
        board.placePiece(new Rook(board, Color.WHITE), new Position(4, 5)); // right
        board.placePiece(new Rook(board, Color.WHITE), new Position(3, 3)); // nw
        board.placePiece(new Rook(board, Color.WHITE), new Position(3, 5)); // ne
        board.placePiece(new Rook(board, Color.WHITE), new Position(5, 3)); // sw
        board.placePiece(new Rook(board, Color.WHITE), new Position(5, 5)); // se

        // Act
        boolean[][] possibleMoves = king.possibleMoves();

        // Assert
        // The king should not be able to move to any of the blocked squares
        assertFalse(possibleMoves[3][4]); // above
        assertFalse(possibleMoves[5][4]); // below
        assertFalse(possibleMoves[4][3]); // left
        assertFalse(possibleMoves[4][5]); // right
        assertFalse(possibleMoves[3][3]); // nw
        assertFalse(possibleMoves[3][5]); // ne
        assertFalse(possibleMoves[5][3]); // sw
        assertFalse(possibleMoves[5][5]); // se
    }

    @Test
    void testPossibleMoves_KingCapturingOpponentPieces() {
        // Arrange
        Board board = new Board(8, 8);
        ChessMatch chessMatch = new ChessMatch();
        King king = new King(board, Color.WHITE, chessMatch);
        Position kingPosition = new Position(4, 4);
        board.placePiece(king, kingPosition);

        board.placePiece(new Rook(board, Color.BLACK), new Position(3, 4)); // above
        board.placePiece(new Rook(board, Color.BLACK), new Position(5, 4)); // below
        board.placePiece(new Rook(board, Color.BLACK), new Position(4, 3)); // left
        board.placePiece(new Rook(board, Color.BLACK), new Position(4, 5)); // right
        board.placePiece(new Rook(board, Color.BLACK), new Position(3, 3)); // nw
        board.placePiece(new Rook(board, Color.BLACK), new Position(3, 5)); // ne
        board.placePiece(new Rook(board, Color.BLACK), new Position(5, 3)); // sw
        board.placePiece(new Rook(board, Color.BLACK), new Position(5, 5)); // se

        boolean[][] possibleMoves = king.possibleMoves();

        assertTrue(possibleMoves[3][4]); // above
        assertTrue(possibleMoves[5][4]); // below
        assertTrue(possibleMoves[4][3]); // left
        assertTrue(possibleMoves[4][5]); // right
        assertTrue(possibleMoves[3][3]); // nw
        assertTrue(possibleMoves[3][5]); // ne
        assertTrue(possibleMoves[5][3]); // sw
        assertTrue(possibleMoves[5][5]); // se
    }

    @Test
    void testPossibleMoves_KingSideCastling() {
        Board board = new Board(8, 8);
        ChessMatch chessMatch = new ChessMatch();
        King king = new King(board, Color.WHITE, chessMatch);
        Position kingPosition = new Position(7, 4);
        board.placePiece(king, kingPosition);

        Rook rook = new Rook(board, Color.WHITE);
        board.placePiece(rook, new Position(7, 7));

        boolean[][] possibleMoves = king.possibleMoves();

        assertTrue(possibleMoves[7][6]);
    }

    @Test
    void testPossibleMoves_QueenSideCastling() {
        // Arrange
        Board board = new Board(8, 8);
        ChessMatch chessMatch = new ChessMatch();
        King king = new King(board, Color.WHITE, chessMatch);
        Position kingPosition = new Position(7, 4);
        board.placePiece(king, kingPosition);

        Rook rook = new Rook(board, Color.WHITE);
        board.placePiece(rook, new Position(7, 0));

        boolean[][] possibleMoves = king.possibleMoves();

        assertTrue(possibleMoves[7][2]);
    }
}
