package com.repliforce.chessgame.chess.pieces;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.repliforce.chessgame.boardgame.Board;
import com.repliforce.chessgame.boardgame.Position;
import com.repliforce.chessgame.chess.Color;
import com.repliforce.chessgame.chess.ChessMatch;

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
        assertTrue(possibleMoves[5][3], "Peão azul deve mover-se para frente uma casa.");
        assertTrue(possibleMoves[4][3], "Peão azul deve mover-se duas casas à frente no primeiro movimento.");
    }

    @Test
    public void testRedPawnNormalMove() {
        Position initialPosition = new Position(1, 3);
        board.placePiece(redPawn, initialPosition);

        boolean[][] possibleMoves = redPawn.possibleMoves();
        assertTrue(possibleMoves[2][3], "Peão vermelho deve mover-se para frente uma casa.");
        assertTrue(possibleMoves[3][3], "Peão vermelho deve mover-se duas casas à frente no primeiro movimento.");
    }

    @Test
    public void testBluePawnCaptureOpponent() {
        Position bluePawnPosition = new Position(4, 3);
        board.placePiece(bluePawn, bluePawnPosition);

        Position opponentPosition = new Position(3, 2);
        Pawn opponentPawn = new Pawn(board, Color.RED, chessMatch);
        board.placePiece(opponentPawn, opponentPosition);

        boolean[][] possibleMoves = bluePawn.possibleMoves();
        assertTrue(possibleMoves[3][2], "Peão azul deve poder capturar o peão adversário vermelho na diagonal.");
    }

    @Test
    public void testRedPawnCaptureOpponent() {
        Position redPawnPosition = new Position(3, 3);
        board.placePiece(redPawn, redPawnPosition);

        Position opponentPosition = new Position(4, 4);
        Pawn opponentPawn = new Pawn(board, Color.BLUE, chessMatch);
        board.placePiece(opponentPawn, opponentPosition);

        boolean[][] possibleMoves = redPawn.possibleMoves();
        assertTrue(possibleMoves[4][4], "Peão vermelho deve poder capturar o peão adversário azul na diagonal.");
    }

    @Test
    public void testBluePawnCannotMoveToOccupiedPosition() {
        Position bluePawnPosition = new Position(6, 3);
        board.placePiece(bluePawn, bluePawnPosition);

        Position blockingPosition = new Position(5, 3);
        Pawn blockingPawn = new Pawn(board, Color.BLUE, chessMatch);
        board.placePiece(blockingPawn, blockingPosition);

        boolean[][] possibleMoves = bluePawn.possibleMoves();
        assertFalse(possibleMoves[5][3], "Peão azul não deve poder mover-se para uma posição ocupada por outra peça azul.");
    }

    @Test
    public void testRedPawnCannotMoveToOccupiedPosition() {
        Position redPawnPosition = new Position(1, 3);
        board.placePiece(redPawn, redPawnPosition);

        Position blockingPosition = new Position(2, 3);
        Pawn blockingPawn = new Pawn(board, Color.RED, chessMatch);
        board.placePiece(blockingPawn, blockingPosition);

        boolean[][] possibleMoves = redPawn.possibleMoves();
        assertFalse(possibleMoves[2][3], "Peão vermelho não deve poder mover-se para uma posição ocupada por outra peça vermelha.");
    }
}
