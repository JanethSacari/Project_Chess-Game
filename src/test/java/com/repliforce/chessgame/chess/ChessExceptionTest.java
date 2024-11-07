package com.repliforce.chessgame.chess;

import static org.junit.jupiter.api.Assertions.*;

import com.repliforce.chessgame.boardgame.BoardException;
import org.junit.jupiter.api.Test;

public class ChessExceptionTest {

    @Test
    public void testChessExceptionMessage() {
        String errorMessage = "Invalid chess move!";
        ChessException exception = assertThrows(ChessException.class, () -> {
            throw new ChessException(errorMessage);
        });

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    public void testChessExceptionIsInstanceOfBoardException() {
        ChessException exception = new ChessException("Test message");

        assertTrue(exception instanceof BoardException);
    }
}
