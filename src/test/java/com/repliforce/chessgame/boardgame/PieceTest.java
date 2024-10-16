package com.repliforce.chessgame.boardgame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MockPiece extends Piece {

    public MockPiece(Board board) {
        super(board);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] moves = new boolean[3][3];
        moves[0][0] = true;
        moves[1][1] = true;
        moves[2][2] = false;
        return moves;
    }
}

public class PieceTest {

    private Board board;
    private MockPiece piece;

    @BeforeEach
    public void setUp() {
        board = new Board(3, 3);
        piece = new MockPiece(board);
    }

    @Test
    public void testConstructor() {
        assertNotNull(piece.getBoard());
        assertNull(piece.position);
    }

    @Test
    public void testPossibleMoveTrue() {
        Position pos = new Position(0, 0);
        assertTrue(piece.possibleMove(pos));
    }

    @Test
    public void testPossibleMoveFalse() {
        Position pos = new Position(2, 2);
        assertFalse(piece.possibleMove(pos));
    }

    @Test
    public void testIsThereAnyPossibleMoveTrue() {
        assertTrue(piece.isThereAnyPossibleMove());
    }

    @Test
    public void testIsThereAnyPossibleMoveFalse() {
        Piece noMovesPiece = new Piece(board) {
            @Override
            public boolean[][] possibleMoves() {
                return new boolean[3][3];
            }
        };
        assertFalse(noMovesPiece.isThereAnyPossibleMove());
    }

    @Test
    public void testGetBoard() {
        assertEquals(board, piece.getBoard());
    }
}
