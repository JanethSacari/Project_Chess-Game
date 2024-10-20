package com.repliforce.chessgame.boardgame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardExceptionTest {

    @Test
    public void testBoardExceptionMessage() {
        String errorMessage = "Error creating board";
        BoardException exception = new BoardException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    public void testBoardExceptionInheritance() {
        BoardException exception = new BoardException("Test message");
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    public void testBoardExceptionNoMessage() {
        BoardException exception = new BoardException(null);

        assertNull(exception.getMessage());
    }
}