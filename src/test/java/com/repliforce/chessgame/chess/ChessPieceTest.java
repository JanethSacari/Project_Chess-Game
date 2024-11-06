package com.repliforce.chessgame.chess;

import com.repliforce.chessgame.boardgame.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class ChessPieceTest {

    @Mock
    private Board boardMock;

    private ChessPiece chessPiece;

    private class TestChessPiece extends ChessPiece {
        public TestChessPiece(Board board, Color color) {
            super(board, color);
        }

        @Override
        public boolean[][] possibleMoves() {
            return new boolean[8][8];
        }
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        chessPiece = new TestChessPiece(boardMock, Color.BLUE);
    }

    @Test
    public void testGetColor() {
        assertEquals(Color.BLUE, chessPiece.getColor());
    }

    @Test
    public void testGetMoveCount_InitialValue() {
        assertEquals(0, chessPiece.getMoveCount());
    }

    @Test
    public void testIncreaseMoveCount() {
        chessPiece.increaseMoveCount();
        assertEquals(1, chessPiece.getMoveCount());
    }

    @Test
    public void testDecreaseMoveCount() {
        chessPiece.increaseMoveCount();
        chessPiece.decreaseMoveCount();
        assertEquals(0, chessPiece.getMoveCount());
    }

    @Test
    public void testPossibleMoves() {
        boolean[][] possibleMoves = chessPiece.possibleMoves();

        assertNotNull(possibleMoves);
        assertEquals(8, possibleMoves.length);
        assertEquals(8, possibleMoves[0].length);
    }
}
