package com.repliforce.chessgame.boardgame;

import com.repliforce.chessgame.chess.ChessMatch;
import com.repliforce.chessgame.chess.Color;
import com.repliforce.chessgame.chess.pieces.Pawn;
import com.repliforce.chessgame.chess.pieces.Rook;
import com.repliforce.chessgame.chess.pieces.Queen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardExtendedTest {

    private Board board;
    private ChessMatch chessMatch;

    @BeforeEach
    public void setUp() {
        board = new Board(8, 8);
        chessMatch = new ChessMatch();
    }

    @Test
    public void testBoardBoundaryCorners() {
        Pawn bluePawn = new Pawn(board, Color.BLUE, chessMatch);

        Position topLeft = new Position(0, 0);
        board.placePiece(bluePawn, topLeft);
        assertEquals(bluePawn, board.selectedPiece(topLeft));
        board.removePiece(topLeft);

        Pawn redPawn = new Pawn(board, Color.RED, chessMatch);
        Position bottomRight = new Position(7, 7);
        board.placePiece(redPawn, bottomRight);
        assertEquals(redPawn, board.selectedPiece(bottomRight));
    }

    @Test
    public void testBoardDimensionVariations() {
        Board small = new Board(4, 4);
        assertEquals(4, small.getRows());
        assertEquals(4, small.getColumns());

        Board large = new Board(16, 16);
        assertEquals(16, large.getRows());
        assertEquals(16, large.getColumns());
    }

    @Test
    public void testRemovePieceMultipleTimes() {
        Position position = new Position(2, 3);
        Pawn pawn = new Pawn(board, Color.BLUE, chessMatch);

        board.placePiece(pawn, position);
        Piece first = board.removePiece(position);
        assertNotNull(first);

        Piece second = board.removePiece(position);
        assertNull(second);
    }

    @Test
    public void testPositionExistsAtBoundaries() {
        Position valid1 = new Position(0, 0);
        Position valid2 = new Position(7, 7);
        Position invalid1 = new Position(-1, 0);
        Position invalid2 = new Position(8, 8);

        assertTrue(board.positionExists(valid1));
        assertTrue(board.positionExists(valid2));
        assertFalse(board.positionExists(invalid1));
        assertFalse(board.positionExists(invalid2));
    }

    @Test
    public void testThereIsAPieceMultiplePieces() {
        Position pos1 = new Position(0, 0);
        Position pos2 = new Position(1, 1);
        Position pos3 = new Position(2, 2);

        Pawn pawn1 = new Pawn(board, Color.BLUE, chessMatch);
        Pawn pawn2 = new Pawn(board, Color.RED, chessMatch);
        Rook rook = new Rook(board, Color.BLUE);

        board.placePiece(pawn1, pos1);
        board.placePiece(pawn2, pos2);
        board.placePiece(rook, pos3);

        assertTrue(board.thereIsAPiece(pos1));
        assertTrue(board.thereIsAPiece(pos2));
        assertTrue(board.thereIsAPiece(pos3));
        assertFalse(board.thereIsAPiece(new Position(3, 3)));
    }

    @Test
    public void testSelectedPieceByRowColumn() {
        Position position = new Position(3, 4);
        Rook rook = new Rook(board, Color.RED);

        board.placePiece(rook, position);

        Piece retrieved = board.selectedPiece(3, 4);
        assertEquals(rook, retrieved);
    }

    @Test
    public void testSelectedPieceReturnsNull() {
        Position emptyPosition = new Position(5, 5);
        Piece piece = board.selectedPiece(emptyPosition);

        assertNull(piece);
    }

    @Test
    public void testBoardIntegrity() {
        Pawn pawn1 = new Pawn(board, Color.BLUE, chessMatch);
        Pawn pawn2 = new Pawn(board, Color.RED, chessMatch);
        Queen queen = new Queen(board, Color.BLUE);

        board.placePiece(pawn1, new Position(2, 0));
        board.placePiece(pawn2, new Position(2, 1));
        board.placePiece(queen, new Position(3, 3));

        assertNotNull(board.selectedPiece(2, 0));
        assertNotNull(board.selectedPiece(2, 1));
        assertNotNull(board.selectedPiece(3, 3));

        board.removePiece(new Position(2, 0));
        assertNull(board.selectedPiece(2, 0));
        assertNotNull(board.selectedPiece(2, 1));
        assertNotNull(board.selectedPiece(3, 3));
    }

    @Test
    public void testInvalidBoardDimensions() {
        Exception exception1 = assertThrows(BoardException.class, () -> {
            new Board(0, 0);
        });
        assertTrue(exception1.getMessage().contains("must be at least"));

        Exception exception2 = assertThrows(BoardException.class, () -> {
            new Board(-5, 8);
        });
        assertTrue(exception2.getMessage().contains("must be at least"));
    }

    @Test
    public void testEdgeCasePositions() {
        Position[] edges = {
            new Position(0, 3),    // Top edge
            new Position(7, 3),    // Bottom edge
            new Position(3, 0),    // Left edge
            new Position(3, 7)     // Right edge
        };

        for (int i = 0; i < edges.length; i++) {
            Pawn pawn = new Pawn(board, Color.BLUE, chessMatch);
            board.placePiece(pawn, edges[i]);
            assertTrue(board.thereIsAPiece(edges[i]));
            board.removePiece(edges[i]);
        }
    }
}