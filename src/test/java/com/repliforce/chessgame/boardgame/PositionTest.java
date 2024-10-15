package com.repliforce.chessgame.boardgame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PositionTest {

    @Test
    public void testPositionConstructor() {
        Position position = new Position(3, 5);
        assertEquals(3, position.getRow());
        assertEquals(5, position.getColumn());
    }

    @Test
    public void testGetRow() {
        Position position = new Position(2, 4);
        assertEquals(2, position.getRow());
    }

    @Test
    public void testSetRow() {
        Position position = new Position(2, 4);
        position.setRow(6);
        assertEquals(6, position.getRow());
    }

    @Test
    public void testGetColumn() {
        Position position = new Position(3, 7);
        assertEquals(7, position.getColumn());
    }

    @Test
    public void testSetColumn() {
        Position position = new Position(3, 7);
        position.setColumn(9); // Define uma nova coluna
        assertEquals(9, position.getColumn());
    }

    @Test
    public void testSetValues() {
        Position position = new Position(1, 2);
        position.setValues(5, 8);
        assertEquals(5, position.getRow());
        assertEquals(8, position.getColumn());
    }

    @Test
    public void testToString() {
        Position position = new Position(3, 6);
        assertEquals("3, 6", position.toString());
    }

}