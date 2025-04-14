package com.repliforce.chessgame.chess;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessMatchTest {

    @Test
    public void testNextTurn() {
        ChessMatch match = new ChessMatch();

        assertEquals(1, match.getTurn());
        assertEquals(Color.BLUE, match.getCurrentPlayer());

        ChessPosition source = new ChessPosition('a', 2);
        ChessPosition target = new ChessPosition('a', 3);
        match.performChessMove(source, target);

        assertEquals(2, match.getTurn());
        assertEquals(Color.RED, match.getCurrentPlayer());
    }

    @Test
    public void testInvalidMove() {
        ChessMatch match = new ChessMatch();
        ChessPosition source = new ChessPosition('a', 1);
        ChessPosition target = new ChessPosition('a', 3);

        assertThrows(ChessException.class, () -> {
            match.performChessMove(source, target);
        });
    }

    @Test
    void noCheckMateWhenKingHasEscapeMoves() {
        ChessMatch match = new ChessMatch();
        match.performChessMove(new ChessPosition('e', 2), new ChessPosition('e', 4));
        match.performChessMove(new ChessPosition('f', 7), new ChessPosition('f', 5));
        match.performChessMove(new ChessPosition('d', 1), new ChessPosition('h', 5));
        assertFalse(match.getCheckMate());
    }

    @Test
    void checkMateNotDetectedWhenNoCheck() {
        ChessMatch match = new ChessMatch();
        match.performChessMove(new ChessPosition('e', 2), new ChessPosition('e', 4));
        match.performChessMove(new ChessPosition('a', 7), new ChessPosition('a', 6));
        assertFalse(match.getCheckMate());
    }
}