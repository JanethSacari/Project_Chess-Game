package com.repliforce.chessgame.game;

import com.repliforce.chessgame.chess.ChessMatch;
import com.repliforce.chessgame.chess.ChessPiece;
import com.repliforce.chessgame.chess.ChessPosition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
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
    public void testClearScreen() throws Exception {
        String result = tapSystemOut(() -> {
            UserInterface.clearScreen();
        });
        assertTrue(result.contains("\033[H\033[2J"));
    }

    @Test
    public void testReadChessPosition_ValidInput() {
        Scanner scanner = new Scanner("e2\n");
        ChessPosition position = UserInterface.readChessPosition(scanner);
        assertEquals('e', position.getColumn());
        assertEquals(2, position.getRow());
    }

    @Test
    public void testReadChessPosition_InvalidInput() {
        Scanner scanner = new Scanner("z9\n");
        assertThrows(InputMismatchException.class, () -> {
            UserInterface.readChessPosition(scanner);
        });
    }

    @Test
    public void testPrintBoard() throws Exception {
        ChessPiece[][] pieces = new ChessPiece[8][8];
        String result = tapSystemOut(() -> {
            UserInterface.printBoard(pieces);
        });
        assertTrue(result.contains("  a b c d e f g h"));
    }

    @Test
    public void testPrintMatch() throws Exception {
        ChessMatch chessMatch = new ChessMatch();
        List<ChessPiece> capturedPieces = new ArrayList<>();

        String result = tapSystemOut(() -> {
            UserInterface.printMatch(chessMatch, capturedPieces);
        });

        assertTrue(result.contains("Turn:"));
        assertTrue(result.contains("Waiting player:"));
    }
}
