package com.repliforce.chessgame.chess;

import com.repliforce.chessgame.boardgame.Position;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ChessPositionTest {

    @Test
    public void testValidChessPosition() {
        ChessPosition chessPosition = new ChessPosition('e', 4);

        assertEquals('e', chessPosition.getColumn());
        assertEquals(4, chessPosition.getRow());
    }

    @Test
    public void testInvalidChessPosition() {
        assertThrows(ChessException.class, () -> {
            new ChessPosition('z', 4);
        });

        assertThrows(ChessException.class, () -> {
            new ChessPosition('e', 9);
        });

        assertThrows(ChessException.class, () -> {
            new ChessPosition('x', 0);
        });
    }

    @Test
    public void testToPosition() {
        ChessPosition chessPosition = new ChessPosition('d', 5);

        Position position = chessPosition.toPosition();
        assertEquals(3, position.getRow());
        assertEquals(3, position.getColumn());
    }

    @Test
    public void testFromPosition() {
        Position position = new Position(2, 2);
        ChessPosition chessPosition = ChessPosition.fromPosition(position);

        assertEquals('c', chessPosition.getColumn());
        assertEquals(6, chessPosition.getRow());
    }

    @Test
    public void testToString() {
        ChessPosition chessPosition = new ChessPosition('f', 7);
        assertEquals("f7", chessPosition.toString());
    }
}
