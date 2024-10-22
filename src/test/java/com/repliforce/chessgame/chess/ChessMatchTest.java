package com.repliforce.chessgame.chess;

import com.repliforce.chessgame.chess.pieces.King;
import com.repliforce.chessgame.chess.pieces.Pawn;
import com.repliforce.chessgame.chess.pieces.Queen;
import com.repliforce.chessgame.chess.pieces.Rook;
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

}