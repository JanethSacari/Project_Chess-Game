package com.repliforce.chessgame.chess.pieces;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.repliforce.chessgame.boardgame.Board;
import com.repliforce.chessgame.boardgame.Position;
import com.repliforce.chessgame.chess.Color;
import com.repliforce.chessgame.chess.ChessMatch;

import java.lang.reflect.Field;

public class PawnTest {

    private Board board;
    private ChessMatch chessMatch;
    private Pawn bluePawn;
    private Pawn redPawn;

    @BeforeEach
    public void setUp() {
        board = new Board(8, 8);
        chessMatch = new ChessMatch();
        bluePawn = new Pawn(board, Color.BLUE, chessMatch);
        redPawn = new Pawn(board, Color.RED, chessMatch);
    }

    @Test
    public void testBluePawnNormalMove() {
        Position initialPosition = new Position(6, 3);
        board.placePiece(bluePawn, initialPosition);

        boolean[][] possibleMoves = bluePawn.possibleMoves();
        assertTrue(possibleMoves[5][3], "Blue Pawn moves one position");
        assertTrue(possibleMoves[4][3], "Blue Pawn moves two times");
    }

    @Test
    public void testRedPawnNormalMove() {
        Position initialPosition = new Position(1, 3);
        board.placePiece(redPawn, initialPosition);

        boolean[][] possibleMoves = redPawn.possibleMoves();
        assertTrue(possibleMoves[2][3], "Red Pawn moves one time ahead.");
        assertTrue(possibleMoves[3][3], "Red Pawn moves two time ahead.");
    }

    @Test
    public void testBluePawnCaptureOpponent() {
        Position bluePawnPosition = new Position(4, 3);
        board.placePiece(bluePawn, bluePawnPosition);

        Position opponentPosition = new Position(3, 2);
        Pawn opponentPawn = new Pawn(board, Color.RED, chessMatch);
        board.placePiece(opponentPawn, opponentPosition);

        boolean[][] possibleMoves = bluePawn.possibleMoves();
        assertTrue(possibleMoves[3][2], "Blue Pawn can capture one Pawn.");
    }

    @Test
    public void testRedPawnCaptureOpponent() {
        Position redPawnPosition = new Position(3, 3);
        board.placePiece(redPawn, redPawnPosition);

        Position opponentPosition = new Position(4, 4);
        Pawn opponentPawn = new Pawn(board, Color.BLUE, chessMatch);
        board.placePiece(opponentPawn, opponentPosition);

        boolean[][] possibleMoves = redPawn.possibleMoves();
        assertTrue(possibleMoves[4][4], "Red Pawn can capture one Blue Pawn.");
    }

    @Test
    public void testBluePawnCannotMoveToOccupiedPosition() {
        Position bluePawnPosition = new Position(6, 3);
        board.placePiece(bluePawn, bluePawnPosition);

        Position blockingPosition = new Position(5, 3);
        Pawn blockingPawn = new Pawn(board, Color.BLUE, chessMatch);
        board.placePiece(blockingPawn, blockingPosition);

        boolean[][] possibleMoves = bluePawn.possibleMoves();
        assertFalse(possibleMoves[5][3], "Blue Pawn can not capture another Blue Pawn.");
    }

    @Test
    public void testRedPawnCannotMoveToOccupiedPosition() {
        Position redPawnPosition = new Position(1, 3);
        board.placePiece(redPawn, redPawnPosition);

        Position blockingPosition = new Position(2, 3);
        Pawn blockingPawn = new Pawn(board, Color.RED, chessMatch);
        board.placePiece(blockingPawn, blockingPosition);

        boolean[][] possibleMoves = redPawn.possibleMoves();
        assertFalse(possibleMoves[2][3], "Red Pawn can not capture another Red Pawn.");
    }

    @Test
    void toStringReturnsCorrectSymbolForKnight() {
        Board board = new Board(8, 8);
        Knight knight = new Knight(board, Color.WHITE);

        assertEquals("H", knight.toString());
    }

    @Test
    void bluePawnCannotPerformEnPassantIfNotVulnerable() {
        Position bluePawnPosition = new Position(3, 3);
        board.placePiece(bluePawn, bluePawnPosition);

        Position opponentPawnPosition = new Position(3, 2);
        Pawn opponentPawn = new Pawn(board, Color.RED, chessMatch);
        board.placePiece(opponentPawn, opponentPawnPosition);

        boolean[][] possibleMoves = bluePawn.possibleMoves();
        assertFalse(possibleMoves[2][2], "Blue Pawn should not be able to perform en passant if the opponent is not vulnerable.");
    }

    @Test
    void redPawnCannotPerformEnPassantIfNotVulnerable() {
        Position redPawnPosition = new Position(4, 3);
        board.placePiece(redPawn, redPawnPosition);

        Position opponentPawnPosition = new Position(4, 2);
        Pawn opponentPawn = new Pawn(board, Color.BLUE, chessMatch);
        board.placePiece(opponentPawn, opponentPawnPosition);

        boolean[][] possibleMoves = redPawn.possibleMoves();
        assertFalse(possibleMoves[5][2], "Red Pawn should not be able to perform en passant if the opponent is not vulnerable.");
    }

    @Test
    void bluePawnCannotMoveTwoStepsAfterFirstMove() {
        Position bluePawnPosition = new Position(6, 3);
        board.placePiece(bluePawn, bluePawnPosition);
        bluePawn.increaseMoveCount(); // Simulate the pawn has already moved

        boolean[][] possibleMoves = bluePawn.possibleMoves();
        assertFalse(possibleMoves[4][3], "Blue Pawn should not be able to move two steps after the first move.");
    }

    @Test
    void redPawnCannotMoveTwoStepsAfterFirstMove() {
        Position redPawnPosition = new Position(1, 3);
        board.placePiece(redPawn, redPawnPosition);
        redPawn.increaseMoveCount(); // Simulate the pawn has already moved

        boolean[][] possibleMoves = redPawn.possibleMoves();
        assertFalse(possibleMoves[3][3], "Red Pawn should not be able to move two steps after the first move.");
    }

    @Test
    void bluePawnCannotCaptureWithoutOpponentPiece() {
        Position bluePawnPosition = new Position(4, 3);
        board.placePiece(bluePawn, bluePawnPosition);

        Position emptyPosition = new Position(3, 2); // No piece here
        boolean[][] possibleMoves = bluePawn.possibleMoves();
        assertFalse(possibleMoves[3][2], "Blue Pawn should not be able to capture without an opponent piece.");
    }

    @Test
    void redPawnCannotCaptureWithoutOpponentPiece() {
        Position redPawnPosition = new Position(3, 3);
        board.placePiece(redPawn, redPawnPosition);

        Position emptyPosition = new Position(4, 4); // No piece here
        boolean[][] possibleMoves = redPawn.possibleMoves();
        assertFalse(possibleMoves[4][4], "Red Pawn should not be able to capture without an opponent piece.");
    }

    @Test
    void bluePawnCannotPerformEnPassantIfNotOnRowThree() throws NoSuchFieldException, IllegalAccessException {
        Position bluePawnPosition = new Position(4, 3); // Not on row 3
        board.placePiece(bluePawn, bluePawnPosition);

        Position opponentPawnPosition = new Position(4, 2);
        Pawn opponentPawn = new Pawn(board, Color.RED, chessMatch);
        board.placePiece(opponentPawn, opponentPawnPosition);
        Field enPassantField = ChessMatch.class.getDeclaredField("enPassantVulnerable");
        enPassantField.setAccessible(true);
        enPassantField.set(chessMatch, opponentPawn);

        boolean[][] possibleMoves = bluePawn.possibleMoves();
        assertFalse(possibleMoves[3][2], "Blue Pawn should not be able to perform en passant if not on row 3.");
    }

    @Test
    void redPawnCannotPerformEnPassantIfNotOnRowFour() throws IllegalAccessException, NoSuchFieldException {
        Position redPawnPosition = new Position(3, 3); // Not on row 4
        board.placePiece(redPawn, redPawnPosition);

        Position opponentPawnPosition = new Position(3, 2);
        Pawn opponentPawn = new Pawn(board, Color.BLUE, chessMatch);
        board.placePiece(opponentPawn, opponentPawnPosition);
        Field enPassantField = ChessMatch.class.getDeclaredField("enPassantVulnerable");
        enPassantField.setAccessible(true);
        enPassantField.set(chessMatch, opponentPawn);

        boolean[][] possibleMoves = redPawn.possibleMoves();
        assertFalse(possibleMoves[4][2], "Red Pawn should not be able to perform en passant if not on row 4.");
    }

    @Test
    void bluePawnCanPerformEnPassantOnRightWhenConditionsAreMet() throws Exception {
        Position bluePawnPosition = new Position(3, 3);
        board.placePiece(bluePawn, bluePawnPosition);

        Position opponentPawnPosition = new Position(3, 4);
        Pawn opponentPawn = new Pawn(board, Color.RED, chessMatch);
        board.placePiece(opponentPawn, opponentPawnPosition);

        Field enPassantField = ChessMatch.class.getDeclaredField("enPassantVulnerable");
        enPassantField.setAccessible(true);
        enPassantField.set(chessMatch, opponentPawn);

        boolean[][] possibleMoves = bluePawn.possibleMoves();
        assertTrue(possibleMoves[2][4], "Blue Pawn should be able to perform en passant on the right.");
    }

    @Test
    void bluePawnCannotPerformEnPassantOnRightIfNoOpponentPiece() throws Exception {
        Position bluePawnPosition = new Position(3, 3);
        board.placePiece(bluePawn, bluePawnPosition);

        Position emptyPosition = new Position(3, 4); // No opponent piece here
        Field enPassantField = ChessMatch.class.getDeclaredField("enPassantVulnerable");
        enPassantField.setAccessible(true);
        enPassantField.set(chessMatch, null);

        boolean[][] possibleMoves = bluePawn.possibleMoves();
        assertFalse(possibleMoves[2][4], "Blue Pawn should not be able to perform en passant on the right without an opponent piece.");
    }

    @Test
    void redPawnCanCaptureOpponentOnLeft() {
        Position redPawnPosition = new Position(4, 3);
        board.placePiece(redPawn, redPawnPosition);

        Position opponentPosition = new Position(5, 2);
        Pawn opponentPawn = new Pawn(board, Color.BLUE, chessMatch);
        board.placePiece(opponentPawn, opponentPosition);

        boolean[][] possibleMoves = redPawn.possibleMoves();
        assertTrue(possibleMoves[5][2], "Red Pawn should be able to capture an opponent on the left.");
    }

    @Test
    void redPawnCannotCaptureWithoutOpponentOnLeft() {
        Position redPawnPosition = new Position(4, 3);
        board.placePiece(redPawn, redPawnPosition);

        Position emptyPosition = new Position(5, 2); // No opponent piece here
        boolean[][] possibleMoves = redPawn.possibleMoves();
        assertFalse(possibleMoves[5][2], "Red Pawn should not be able to capture without an opponent on the left.");
    }

    @Test
    void bluePawnCannotPerformEnPassantOnLeftIfNotVulnerable() throws Exception {
        Position bluePawnPosition = new Position(3, 3);
        board.placePiece(bluePawn, bluePawnPosition);

        Position opponentPawnPosition = new Position(3, 2);
        Pawn opponentPawn = new Pawn(board, Color.RED, chessMatch);
        board.placePiece(opponentPawn, opponentPawnPosition);

        Field enPassantField = ChessMatch.class.getDeclaredField("enPassantVulnerable");
        enPassantField.setAccessible(true);
        enPassantField.set(chessMatch, null);

        boolean[][] possibleMoves = bluePawn.possibleMoves();
        assertFalse(possibleMoves[2][2], "Blue Pawn should not be able to perform en passant on the left if the opponent is not vulnerable.");
    }

    @Test
    void redPawnCannotPerformEnPassantOnRightIfNotVulnerable() throws Exception {
        Position redPawnPosition = new Position(4, 3);
        board.placePiece(redPawn, redPawnPosition);

        Position opponentPawnPosition = new Position(4, 4);
        Pawn opponentPawn = new Pawn(board, Color.BLUE, chessMatch);
        board.placePiece(opponentPawn, opponentPawnPosition);

        Field enPassantField = ChessMatch.class.getDeclaredField("enPassantVulnerable");
        enPassantField.setAccessible(true);
        enPassantField.set(chessMatch, null);

        boolean[][] possibleMoves = redPawn.possibleMoves();
        assertFalse(possibleMoves[5][4], "Red Pawn should not be able to perform en passant on the right if the opponent is not vulnerable.");
    }

    @Test
    void bluePawnCanPerformEnPassantOnLeft() throws Exception {
        Position bluePawnPosition = new Position(3, 3);
        board.placePiece(bluePawn, bluePawnPosition);

        Position opponentPawnPosition = new Position(3, 2);
        Pawn opponentPawn = new Pawn(board, Color.RED, chessMatch);
        board.placePiece(opponentPawn, opponentPawnPosition);

        Field enPassantField = ChessMatch.class.getDeclaredField("enPassantVulnerable");
        enPassantField.setAccessible(true);
        enPassantField.set(chessMatch, opponentPawn);

        boolean[][] possibleMoves = bluePawn.possibleMoves();
        assertTrue(possibleMoves[2][2], "Blue Pawn should be able to perform en passant on the left.");
    }

    @Test
    void redPawnCanPerformEnPassantOnLeft() throws Exception {
        Position redPawnPosition = new Position(4, 3);
        board.placePiece(redPawn, redPawnPosition);

        Position opponentPawnPosition = new Position(4, 2);
        Pawn opponentPawn = new Pawn(board, Color.BLUE, chessMatch);
        board.placePiece(opponentPawn, opponentPawnPosition);

        Field enPassantField = ChessMatch.class.getDeclaredField("enPassantVulnerable");
        enPassantField.setAccessible(true);
        enPassantField.set(chessMatch, opponentPawn);

        boolean[][] possibleMoves = redPawn.possibleMoves();
        assertTrue(possibleMoves[5][2], "Red Pawn should be able to perform en passant on the left.");
    }

    @Test
    void bluePawnCanPerformEnPassantOnRight() throws Exception {
        Position bluePawnPosition = new Position(3, 3);
        board.placePiece(bluePawn, bluePawnPosition);

        Position opponentPawnPosition = new Position(3, 4);
        Pawn opponentPawn = new Pawn(board, Color.RED, chessMatch);
        board.placePiece(opponentPawn, opponentPawnPosition);

        Field enPassantField = ChessMatch.class.getDeclaredField("enPassantVulnerable");
        enPassantField.setAccessible(true);
        enPassantField.set(chessMatch, opponentPawn);

        boolean[][] possibleMoves = bluePawn.possibleMoves();
        assertTrue(possibleMoves[2][4], "Blue Pawn should be able to perform en passant on the right.");
    }

    @Test
    void redPawnCanPerformEnPassantOnRight() throws Exception {
        Position redPawnPosition = new Position(4, 3);
        board.placePiece(redPawn, redPawnPosition);

        Position opponentPawnPosition = new Position(4, 4);
        Pawn opponentPawn = new Pawn(board, Color.BLUE, chessMatch);
        board.placePiece(opponentPawn, opponentPawnPosition);

        Field enPassantField = ChessMatch.class.getDeclaredField("enPassantVulnerable");
        enPassantField.setAccessible(true);
        enPassantField.set(chessMatch, opponentPawn);

        boolean[][] possibleMoves = redPawn.possibleMoves();
        assertTrue(possibleMoves[5][4], "Red Pawn should be able to perform en passant on the right.");
    }
    @Test
    void bluePawnCanCaptureOpponentDiagonally() {
        Position bluePawnPosition = new Position(3, 3);
        board.placePiece(bluePawn, bluePawnPosition);

        Position opponentPosition = new Position(2, 2); // Diagonal position
        Pawn opponentPawn = new Pawn(board, Color.RED, chessMatch);
        board.placePiece(opponentPawn, opponentPosition);

        boolean[][] possibleMoves = bluePawn.possibleMoves();
        assertTrue(possibleMoves[2][2], "Blue Pawn should be able to capture an opponent diagonally.");
    }

    @Test
    void bluePawnCanCaptureOpponentOnUpperRightDiagonal() {
        Position bluePawnPosition = new Position(3, 3);
        board.placePiece(bluePawn, bluePawnPosition);

        Position opponentPosition = new Position(2, 4); // Diagonal direita superior
        Pawn opponentPawn = new Pawn(board, Color.RED, chessMatch);
        board.placePiece(opponentPawn, opponentPosition);

        boolean[][] possibleMoves = bluePawn.possibleMoves();
        assertTrue(possibleMoves[2][4], "Blue Pawn should be able to capture an opponent on the upper right diagonal.");
    }

    @Test
    void toStringReturnsCorrectSymbolForPawn() {
        Pawn pawn = new Pawn(board, Color.BLUE, chessMatch);
        assertEquals("P", pawn.toString());
    }

    @Test
    void toStringReturnsCorrectSymbolForRedPawn() {
        Pawn pawn = new Pawn(board, Color.RED, chessMatch);
        assertEquals("P", pawn.toString());
    }
}
