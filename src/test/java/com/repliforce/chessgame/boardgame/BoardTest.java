package com.repliforce.chessgame.boardgame;

import com.repliforce.chessgame.chess.ChessMatch;
import com.repliforce.chessgame.chess.Color;
import com.repliforce.chessgame.chess.pieces.Pawn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board(8, 8);
    }

    @Test
    public void testBoardInitializationValid() {
        assertEquals(8, board.getRows());
        assertEquals(8, board.getColumns());
    }

    @Test
    public void testBoardInitializationInvalid() {
        Exception exception = assertThrows(BoardException.class, () -> {
            new Board(0, 0);
        });
        assertEquals("Error creating board: there must be at least 1 row and 1 column.", exception.getMessage());
    }

    @Test
    public void testPlacePieceValid() {
        Piece pawn = new Pawn(board, Color.BLUE, new ChessMatch());
        Position position = new Position(2, 3);

        board.placePiece(pawn, position);

        assertEquals(pawn, board.selectedPiece(position));
    }

    @Test
    public void testPlacePieceOnOccupiedPosition() {
        Piece pawn1 = new Pawn(board, Color.BLUE, new ChessMatch());
        Piece pawn2 = new Pawn(board, Color.RED, new ChessMatch());
        Position position = new Position(2, 3);

        board.placePiece(pawn1, position);

        Exception exception = assertThrows(BoardException.class, () -> {
            board.placePiece(pawn2, position);
        });
        assertEquals("There is already a piece on position " + position, exception.getMessage());
    }

    @Test
    public void testRemovePiece() {
        Piece pawn = new Pawn(board, Color.BLUE, new ChessMatch());
        Position position = new Position(2, 3);

        board.placePiece(pawn, position);
        Piece removedPiece = board.removePiece(position);

        assertEquals(pawn, removedPiece);
        assertNull(board.selectedPiece(position));
    }

    @Test
    public void testRemovePieceFromEmptyPosition() {
        Position position = new Position(2, 3);

        Piece removedPiece = board.removePiece(position);

        assertNull(removedPiece);
    }

    @Test
    public void testRemovePieceFromInvalidPosition() {
        Position invalidPosition = new Position(10, 10);

        Exception exception = assertThrows(BoardException.class, () -> {
            board.removePiece(invalidPosition);
        });
        assertEquals("Position not on the board!", exception.getMessage());
    }

    @Test
    public void testSelectedPieceValidPosition() {
        Piece pawn = new Pawn(board, Color.BLUE, new ChessMatch());
        Position position = new Position(2, 3);

        board.placePiece(pawn, position);

        Piece selectedPiece = board.selectedPiece(position);

        assertEquals(pawn, selectedPiece);
    }

    @Test
    public void testSelectedPieceInvalidPosition() {
        Position invalidPosition = new Position(10, 10);

        Exception exception = assertThrows(BoardException.class, () -> {
            board.selectedPiece(invalidPosition);
        });
        assertEquals("Position not on the board", exception.getMessage());
    }

    @Test
    public void testPositionExistsValid() {
        Position position = new Position(2, 3);

        assertTrue(board.positionExists(position));
    }

    @Test
    public void testPositionExistsInvalid() {
        Position invalidPosition = new Position(10, 10);

        assertFalse(board.positionExists(invalidPosition));
    }

    @Test
    public void testThereIsAPiece() {
        Piece pawn = new Pawn(board, Color.BLUE, new ChessMatch());
        Position position = new Position(2, 3);

        board.placePiece(pawn, position);

        assertTrue(board.thereIsAPiece(position));
    }

    @Test
    public void testThereIsNoPiece() {
        Position emptyPosition = new Position(2, 3);

        assertFalse(board.thereIsAPiece(emptyPosition));
    }

    @Test
    public void testThereIsAPieceInvalidPosition() {
        Position invalidPosition = new Position(10, 10);

        Exception exception = assertThrows(BoardException.class, () -> {
            board.thereIsAPiece(invalidPosition);
        });
        assertEquals("Position not on the board", exception.getMessage());
    }

    @Test
    public void testSelectedPieceEmptyPosition() {
        Piece selectedPiece = board.selectedPiece(2, 3);

        assertNull(selectedPiece);
    }

    @Test
    public void testSelectedPieceInvalidPosition2() {
        Exception exception = assertThrows(BoardException.class, () -> {
            board.selectedPiece(10, 10);
        });
        assertEquals("Position not on the board", exception.getMessage());
    }
}
