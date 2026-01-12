package com.repliforce.chessgame.chess;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessMatchIntegrationTest {

    @Test
    void testQueensideCastlingBlue() {
        ChessMatch match = new ChessMatch();
        match.performChessMove(new ChessPosition('d', 2), new ChessPosition('d', 3));
        match.performChessMove(new ChessPosition('e', 7), new ChessPosition('e', 6));
        match.performChessMove(new ChessPosition('c', 1), new ChessPosition('f', 4));
        match.performChessMove(new ChessPosition('d', 7), new ChessPosition('d', 6));
        match.performChessMove(new ChessPosition('d', 1), new ChessPosition('d', 2));
        match.performChessMove(new ChessPosition('c', 7), new ChessPosition('c', 6));
        match.performChessMove(new ChessPosition('b', 1), new ChessPosition('c', 3));
        match.performChessMove(new ChessPosition('b', 7), new ChessPosition('b', 6));

        match.performChessMove(new ChessPosition('e', 1), new ChessPosition('c', 1));

        ChessPiece[][] pieces = match.getPieces();
        assertNotNull(pieces[7][2]);
        assertEquals(Color.BLUE, pieces[7][2].getColor());
    }

    @Test
    void testEnPassantNotAvailableAfterTurn() {
        ChessMatch match = new ChessMatch();
        match.performChessMove(new ChessPosition('e', 2), new ChessPosition('e', 4));
        match.performChessMove(new ChessPosition('d', 7), new ChessPosition('d', 5));
        match.performChessMove(new ChessPosition('e', 4), new ChessPosition('e', 5));

        match.performChessMove(new ChessPosition('a', 7), new ChessPosition('a', 6));

        assertThrows(ChessException.class, () -> {
            match.performChessMove(new ChessPosition('e', 5), new ChessPosition('d', 6));
        });
    }

    @Test
    void testSimpleCheck() {
        ChessMatch match = new ChessMatch();

        match.performChessMove(new ChessPosition('e', 2), new ChessPosition('e', 4));
        match.performChessMove(new ChessPosition('e', 7), new ChessPosition('e', 5));

        match.performChessMove(new ChessPosition('f', 1), new ChessPosition('c', 4));
        match.performChessMove(new ChessPosition('b', 8), new ChessPosition('c', 6));
        match.performChessMove(new ChessPosition('c', 4), new ChessPosition('f', 7));

        assertTrue(match.getCheck());
        assertFalse(match.getCheckMate());
    }

    @Test
    void testBlockCheck() {
        ChessMatch match = new ChessMatch();
        match.performChessMove(new ChessPosition('e', 2), new ChessPosition('e', 4));
        match.performChessMove(new ChessPosition('e', 7), new ChessPosition('e', 5));
        match.performChessMove(new ChessPosition('f', 1), new ChessPosition('c', 4));
        match.performChessMove(new ChessPosition('b', 8), new ChessPosition('c', 6));
        match.performChessMove(new ChessPosition('c', 4), new ChessPosition('f', 7));

        match.performChessMove(new ChessPosition('e', 8), new ChessPosition('f', 7));

        assertFalse(match.getCheck());
    }

    @Test
    void testMultipleKnightMoves() {
        ChessMatch match = new ChessMatch();
        match.performChessMove(new ChessPosition('g', 1), new ChessPosition('f', 3));
        assertEquals(Color.RED, match.getCurrentPlayer());

        match.performChessMove(new ChessPosition('g', 8), new ChessPosition('f', 6));
        assertEquals(Color.BLUE, match.getCurrentPlayer());
    }

    @Test
    void testBishopMovement() {
        ChessMatch match = new ChessMatch();
        match.performChessMove(new ChessPosition('e', 2), new ChessPosition('e', 4));
        match.performChessMove(new ChessPosition('e', 7), new ChessPosition('e', 5));

        match.performChessMove(new ChessPosition('f', 1), new ChessPosition('b', 5));
        assertEquals(Color.RED, match.getCurrentPlayer());
    }

    @Test
    void testQueenMovement() {
        ChessMatch match = new ChessMatch();
        match.performChessMove(new ChessPosition('e', 2), new ChessPosition('e', 4));
        match.performChessMove(new ChessPosition('e', 7), new ChessPosition('e', 5));

        match.performChessMove(new ChessPosition('d', 1), new ChessPosition('h', 5));
        assertEquals(Color.RED, match.getCurrentPlayer());
    }

    @Test
    void testPawnCantMoveBackwards() {
        ChessMatch match = new ChessMatch();
        match.performChessMove(new ChessPosition('e', 2), new ChessPosition('e', 4));
        match.performChessMove(new ChessPosition('d', 7), new ChessPosition('d', 5));

        assertThrows(ChessException.class, () -> {
            match.performChessMove(new ChessPosition('e', 4), new ChessPosition('e', 3));
        });
    }

    @Test
    void testKingCannotMoveIntoCheck() {
        ChessMatch match = new ChessMatch();
        match.performChessMove(new ChessPosition('e', 2), new ChessPosition('e', 4));
        match.performChessMove(new ChessPosition('e', 7), new ChessPosition('e', 5));
        match.performChessMove(new ChessPosition('f', 1), new ChessPosition('c', 4));
        match.performChessMove(new ChessPosition('d', 8), new ChessPosition('h', 4));
        match.performChessMove(new ChessPosition('g', 2), new ChessPosition('g', 3));

        assertThrows(ChessException.class, () -> {
            match.performChessMove(new ChessPosition('e', 1), new ChessPosition('e', 2));
        });
    }

    @Test
    void testInitialBoardState() {
        ChessMatch match = new ChessMatch();

        assertEquals(1, match.getTurn());
        assertEquals(Color.BLUE, match.getCurrentPlayer());
        assertFalse(match.getCheck());
        assertFalse(match.getCheckMate());
        assertNull(match.getPromoted());

        ChessPiece[][] pieces = match.getPieces();
        assertNotNull(pieces[0][4]); // Red King
        assertNotNull(pieces[7][4]); // Blue King
    }

    @Test
    void testNoMovesAvailable() {
        ChessMatch match = new ChessMatch();

        assertThrows(ChessException.class, () -> {
            match.performChessMove(new ChessPosition('a', 1), new ChessPosition('a', 2));
        });
    }

    @Test
    void testCheckStateNotPersistent() {
        ChessMatch match = new ChessMatch();
        match.performChessMove(new ChessPosition('f', 2), new ChessPosition('f', 3));
        match.performChessMove(new ChessPosition('e', 7), new ChessPosition('e', 5));
        match.performChessMove(new ChessPosition('g', 2), new ChessPosition('g', 4));
        match.performChessMove(new ChessPosition('d', 8), new ChessPosition('h', 4));

        assertTrue(match.getCheck());
        assertTrue(match.getCheckMate());
    }
}