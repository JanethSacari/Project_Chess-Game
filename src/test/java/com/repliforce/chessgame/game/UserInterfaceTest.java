package com.repliforce.chessgame.game;

import com.repliforce.chessgame.chess.ChessPiece;
import com.repliforce.chessgame.chess.ChessPosition;
import org.junit.Test;

import java.util.InputMismatchException;
import java.util.Scanner;

import static org.junit.Assert.*;

public class UserInterfaceTest {

    @Test
    public void testReadChessPosition_ValidInput() {
        Scanner scanner = new Scanner("e2\n");
        ChessPosition position = UserInterface.readChessPosition(scanner);
        assertEquals('e', position.getColumn());
        assertEquals(2, position.getRow());
    }

    @Test(expected = InputMismatchException.class)
    public void testReadChessPosition_InvalidInput() {
        Scanner scanner = new Scanner("z9\n");
        UserInterface.readChessPosition(scanner);
    }

    @Test
    public void testPrintBoard_EmptyBoard() {
        ChessPiece[][] emptyBoard = new ChessPiece[8][8];
        UserInterface.printBoard(emptyBoard);
    }

    @Test
    public void testPrintBoardWithPossibleMoves_EmptyBoard() {
        ChessPiece[][] emptyBoard = new ChessPiece[8][8];
        boolean[][] possibleMoves = new boolean[8][8];
        possibleMoves[2][2] = true;
        UserInterface.printBoard(emptyBoard, possibleMoves);
    }

    @Test
    public void testClearScreen() {
        UserInterface.clearScreen();
    }
}