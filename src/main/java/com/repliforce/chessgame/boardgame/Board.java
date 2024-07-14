package com.repliforce.chessgame.boardgame;

public class Board {

    private int rows;
    private int columns;
    private Piece[][] pieces;

    public Board (int rows, int columns) {
        if (rows < 1 || columns < 1) {
            throw new BoardException("Error creating board: there must be at least 1 row and 1 column.");
        }
        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Piece selectedPiece(int selectedRow, int selectedColumn){
        if(!positionExists(selectedRow, selectedColumn)) {
            throw new BoardException("Position not on the board");
        }
        return pieces[selectedRow][selectedColumn];
    }

    public Piece selectedPiece(Position selectedPosition) {
        if(!positionExists(selectedPosition)) {
            throw new BoardException("Position not on the board");
        }
        return pieces[selectedPosition.getRow()][selectedPosition.getColumn()];
    }

    public void placePiece(Piece piece, Position position) {
        if (thereIsAPiece(position)) {
            throw new BoardException("There is already a piece on position " + position);
        }
        pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }

    public Piece removePiece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Position not on the board!");
        }
        if (selectedPiece(position) == null) {
            return null;
        }
        Piece helper = selectedPiece(position);
        helper.position = null;
        pieces[position.getRow()][position.getColumn()] = null;
        return helper;
    }

    private boolean positionExists (int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    public boolean positionExists(Position position) {
        return positionExists(position.getRow(), position.getColumn());
    }

    public boolean thereIsAPiece(Position position) {
        if(!positionExists(position)) {
            throw new BoardException("Position not on the board");
        }
        return selectedPiece(position) != null;
    }

}
