package com.repliforce.chessgame.chess;

import com.repliforce.chessgame.boardgame.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessPositionExtendedTest {

    @Test
    void testChessPositionValidCornerA1() {
        ChessPosition pos = new ChessPosition('a', 1);
        Position converted = pos.toPosition();

        assertEquals(7, converted.getRow());
        assertEquals(0, converted.getColumn());
    }

    @Test
    void testChessPositionValidCornerH8() {
        ChessPosition pos = new ChessPosition('h', 8);
        Position converted = pos.toPosition();

        assertEquals(0, converted.getRow());
        assertEquals(7, converted.getColumn());
    }

    @Test
    void testChessPositionValidCornerA8() {
        ChessPosition pos = new ChessPosition('a', 8);
        Position converted = pos.toPosition();

        assertEquals(0, converted.getRow());
        assertEquals(0, converted.getColumn());
    }

    @Test
    void testChessPositionValidCornerH1() {
        ChessPosition pos = new ChessPosition('h', 1);
        Position converted = pos.toPosition();

        assertEquals(7, converted.getRow());
        assertEquals(7, converted.getColumn());
    }

    @Test
    void testChessPositionMidboard() {
        ChessPosition pos = new ChessPosition('d', 4);
        Position converted = pos.toPosition();

        assertEquals(4, converted.getRow());
        assertEquals(3, converted.getColumn());
    }

    @Test
    void testChessPositionToString() {
        ChessPosition pos = new ChessPosition('e', 4);
        String str = pos.toString();

        assertNotNull(str);
        assertTrue(str.contains("e") || str.contains("4"));
    }

    @Test
    void testChessPositionEquality() {
        ChessPosition pos1 = new ChessPosition('e', 4);
        ChessPosition pos2 = new ChessPosition('e', 4);

        // Different objects but same position
        assertEquals(pos1.toPosition().getRow(), pos2.toPosition().getRow());
        assertEquals(pos1.toPosition().getColumn(), pos2.toPosition().getColumn());
    }

    @Test
    void testChessPositionAllRows() {
        for (int row = 1; row <= 8; row++) {
            ChessPosition pos = new ChessPosition('a', row);
            Position converted = pos.toPosition();

            assertTrue(converted.getRow() >= 0 && converted.getRow() < 8);
        }
    }

    @Test
    void testChessPositionAllColumns() {
        for (char col = 'a'; col <= 'h'; col++) {
            ChessPosition pos = new ChessPosition(col, 1);
            Position converted = pos.toPosition();

            assertTrue(converted.getColumn() >= 0 && converted.getColumn() < 8);
        }
    }
}