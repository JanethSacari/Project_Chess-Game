package com.repliforce.chessgame.chess;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReplacePromotedPieceTest {

    @Test
    void testReplacePromotedPieceNullThrowsException() {
        ChessMatch match = new ChessMatch();

        assertThrows(IllegalStateException.class, () -> {
            match.replacePromotedPiece("Q");
        });
    }

    @Test
    void testReplacePromotedPieceInvalidTypeReturnsPromoted() {
        ChessMatch match = new ChessMatch();

        assertThrows(IllegalStateException.class, () -> {
            match.replacePromotedPiece("X");
        });
    }

    @Test
    void testReplacePromotedPieceExceptionMessage() {
        ChessMatch match = new ChessMatch();

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            match.replacePromotedPiece("Q");
        });

        assertEquals("There is no piece to be promoted", exception.getMessage());
    }

    @Test
    void testReplacePromotedPieceValidTypeQueen() {
        ChessMatch match = new ChessMatch();

        assertThrows(IllegalStateException.class, () -> {
            match.replacePromotedPiece("Q");
        });
    }

    @Test
    void testReplacePromotedPieceValidTypeRook() {
        ChessMatch match = new ChessMatch();

        assertThrows(IllegalStateException.class, () -> {
            match.replacePromotedPiece("R");
        });
    }

    @Test
    void testReplacePromotedPieceValidTypeBishop() {
        ChessMatch match = new ChessMatch();

        assertThrows(IllegalStateException.class, () -> {
            match.replacePromotedPiece("B");
        });
    }

    @Test
    void testReplacePromotedPieceValidTypeKnight() {
        ChessMatch match = new ChessMatch();

        assertThrows(IllegalStateException.class, () -> {
            match.replacePromotedPiece("N");
        });
    }

    @Test
    void testReplacePromotedPieceInvalidTypePawn() {
        ChessMatch match = new ChessMatch();

        assertThrows(IllegalStateException.class, () -> {
            match.replacePromotedPiece("P");
        });
    }

    @Test
    void testReplacePromotedPieceInvalidTypeKing() {
        ChessMatch match = new ChessMatch();

        assertThrows(IllegalStateException.class, () -> {
            match.replacePromotedPiece("K");
        });
    }

    @Test
    void testReplacePromotedPieceValidTypes() {
        String[] validTypes = {"Q", "R", "B", "N"};

        for (String type : validTypes) {
            ChessMatch match = new ChessMatch();
            assertThrows(IllegalStateException.class, () -> {
                match.replacePromotedPiece(type);
            });
        }
    }

    @Test
    void testReplacePromotedPieceLowercaseInvalid() {
        ChessMatch match = new ChessMatch();

        assertThrows(IllegalStateException.class, () -> {
            match.replacePromotedPiece("q");
        });
    }

    @Test
    void testReplacePromotedPieceEmptyString() {
        ChessMatch match = new ChessMatch();

        assertThrows(IllegalStateException.class, () -> {
            match.replacePromotedPiece("");
        });
    }

    @Test
    void testReplacePromotedPieceNullType() {
        ChessMatch match = new ChessMatch();

        assertThrows(IllegalStateException.class, () -> {
            match.replacePromotedPiece(null);
        });
    }

    @Test
    void testReplacePromotedPieceNumberType() {
        ChessMatch match = new ChessMatch();

        assertThrows(IllegalStateException.class, () -> {
            match.replacePromotedPiece("1");
        });
    }

    @Test
    void testReplacePromotedPieceMultipleCharacters() {
        ChessMatch match = new ChessMatch();

        assertThrows(IllegalStateException.class, () -> {
            match.replacePromotedPiece("QQ");
        });
    }

    @Test
    void testReplacePromotedPieceSpecialCharacter() {
        ChessMatch match = new ChessMatch();

        assertThrows(IllegalStateException.class, () -> {
            match.replacePromotedPiece("!");
        });
    }

    @Test
    void testReplacePromotedPieceEarlyReturnOnInvalidType() {
        ChessMatch match = new ChessMatch();

        assertThrows(IllegalStateException.class, () -> {
            match.replacePromotedPiece("INVALID");
        });
    }

    @Test
    void testReplacePromotedPieceInitialState() {
        ChessMatch match = new ChessMatch();

        assertThrows(IllegalStateException.class, () -> {
            match.replacePromotedPiece("Q");
        });
    }

    @Test
    void testReplacePromotedPieceInvalidReturnsOriginal() {
        ChessMatch match = new ChessMatch();

        assertThrows(IllegalStateException.class, () -> {
            match.replacePromotedPiece("INVALID");
        });
    }

    @Test
    void testReplacePromotedPieceCaseSensitive() {
        ChessMatch match = new ChessMatch();

        String[] invalidLowercase = {"q", "r", "b", "n"};

        for (String type : invalidLowercase) {
            assertThrows(IllegalStateException.class, () -> {
                match.replacePromotedPiece(type);
            });
        }
    }
}