package com.repliforce.chessgame.chess;

import com.repliforce.chessgame.boardgame.BoardException;

public class ChessException extends BoardException {

    private static final long serialVersionUID = 1L;

    public ChessException(String message) {
        super(message);
    }

}
