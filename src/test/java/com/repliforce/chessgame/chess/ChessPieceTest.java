package com.repliforce.chessgame.chess;

import com.repliforce.chessgame.boardgame.Board;
import com.repliforce.chessgame.boardgame.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

public class ChessPieceTest {

    private ChessPiece chessPiece;
    private Board board;

    class TestChessPiece extends ChessPiece {
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
        board = Mockito.mock(Board.class);
        chessPiece = new TestChessPiece(board, Color.RED);
    }

    @Test
    public void testGetColor() {
        assertEquals(Color.RED, chessPiece.getColor());
    }

    @Test
    public void testGetMoveCountInitiallyZero() {
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
    public void testIsThereOpponentPieceTrue() {
        ChessPiece opponentPiece = new TestChessPiece(board, Color.BLUE);
        Position position = new Position(3, 3);
        Mockito.when(board.selectedPiece(position)).thenReturn(opponentPiece);

        assertTrue(chessPiece.isThereOpponentPiece(position));
    }

    @Test
    public void testIsThereOpponentPieceFalseSameColor() {
        ChessPiece sameColorPiece = new TestChessPiece(board, Color.RED);
        Position position = new Position(3, 3);
        Mockito.when(board.selectedPiece(position)).thenReturn(sameColorPiece);

        assertFalse(chessPiece.isThereOpponentPiece(position));
    }

    @Test
    public void testIsThereOpponentPieceFalseNoPiece() {
        Position position = new Position(3, 3);
        Mockito.when(board.selectedPiece(position)).thenReturn(null);

        assertFalse(chessPiece.isThereOpponentPiece(position));
    }
}