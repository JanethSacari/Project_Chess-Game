package com.repliforce.chessgame.game;

import com.repliforce.chessgame.chess.ChessPosition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class UserInterfaceTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void clearScreen() {
    }

    @Test
    void readChessPosition() {
        Scanner scanner = new Scanner("e2");
        ChessPosition position = UserInterface.readChessPosition(scanner);
        
        assertEquals('e', position.getColumn());
        assertEquals(2, position.getRow());
    }

    @Test
    public void testReadChessPositionInvalid() {
        Scanner scanner = new Scanner("z9");

        assertThrows(InputMismatchException.class, () -> {
            UserInterface.readChessPosition(scanner);
        });
    }

    @Test
    public void testPrintCapturedPieces() throws Exception {
        ChessPiece bluePiece = new ChessPiece(Color.BLUE, "Knight");
        ChessPiece redPiece = new ChessPiece(Color.RED, "Queen");
        List<ChessPiece> capturedPieces = Arrays.asList(bluePiece, redPiece);

        String output = tapSystemOut(() -> {
            UserInterface.printCapturedPieces(capturedPieces);
        });
        
        assertTrue(output.contains("Blue:"));
        assertTrue(output.contains("Red:"));
        assertTrue(output.contains("Knight"));
        assertTrue(output.contains("Queen"));
    }

    @Test
    void printMatch() {
    }

    @Test
    void printBoard() {
    }

    @Test
    void testPrintBoard() {
    }
}
