package org.example.minesweeper;

import java.util.*;

public class Minesweeper {
    private Cell[][] board;
    private int bombsNumber;
    private int xSize;
    private int ySize;

    private Minesweeper(int xSize, int ySize, int bombsNumber) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.board = new Cell[xSize][ySize];
        this.bombsNumber = bombsNumber;
    }

    public static Minesweeper withRandomBombs(int xSize, int ySize, int bombsPercentage) {
        Random random = new Random();

        double percentage = ((double) bombsPercentage) / 100;
        int bombsNumber = (int)(percentage * (xSize * ySize));

        Minesweeper minesweeper = initBoard(xSize, ySize, bombsNumber);

        int generatedBombsCounter = 0;

        while (generatedBombsCounter < bombsNumber) {
            int xPosition = random.nextInt(0, xSize);
            int yPosition = random.nextInt(0, ySize);

            if (!minesweeper.board[xPosition][yPosition].isBomb()) {
                minesweeper.board[xPosition][yPosition].setBomb(true);
                generatedBombsCounter++;
            }
        }

        minesweeper.updateCellsValues();

        return minesweeper;
    }

    private static Minesweeper initBoard(int xSize, int ySize, int bombsNumber) {
        Minesweeper minesweeper = new Minesweeper(xSize, ySize, bombsNumber);

        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                minesweeper.board[i][j] = new Cell(i, j);
            }
        }

        return minesweeper;
    }

    private boolean isPositionValid(int i, int j) {
        return i >= 0 && i < xSize &&
                j >= 0 && j < ySize;
    }

    private void updateCellsValues() {
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                Cell currentCell = this.board[i][j];

                if (currentCell.isBomb())
                    continue;

                for (int cellI = -1; cellI <= 1; cellI++) {
                    for (int cellJ = -1; cellJ <= 1; cellJ++) {

                        if ((cellI != 0 || cellJ != 0) && isPositionValid(i + cellI, j + cellJ)) {
                            if (this.board[i + cellI][j + cellJ].isBomb())
                                currentCell.setAdjacentBombs(currentCell.getAdjacentBombs() + 1);
                        }

                    }
                }
            }
        }
    }

    private void openEmptyCells(int i, int j) {
        Queue<Cell> queue = new LinkedList<>();

        queue.add(this.board[i][j]);
        this.board[i][j].setOpen(true);

        while (!queue.isEmpty()) {
            Cell currentCell = queue.remove();

            if (currentCell.getAdjacentBombs() == 0)
                addAllAdjacentCellsToQueue(queue, currentCell);
        }
    }

    private void addAllAdjacentCellsToQueue(Queue<Cell> queue, Cell cell) {
        for (int cellI = -1; cellI <= 1; cellI++) {
            for (int cellJ = -1; cellJ <= 1; cellJ++) {

                if (isPositionValid(cell.getIPosition() + cellI, cell.getJPosition() + cellJ)) {
                    Cell currentCell = this.board[cell.getIPosition() + cellI][cell.getJPosition() + cellJ];

                    if (!currentCell.isOpen()) {
                        currentCell.setOpen(true);
                        queue.add(currentCell);
                    }

                }

            }
        }
    }

    public boolean openAndGetIfCellIsBomb(int i, int j) {
        if (!isPositionValid(i, j))
            return false;

        Cell selectedCell = this.board[i][j];

        if (selectedCell.isMarked())
            return false;

        if (selectedCell.isOpen())
            return false;

        if (selectedCell.isBomb()) {
            selectedCell.setOpen(true);
            return true;
        }

        if (selectedCell.getAdjacentBombs() != 0)
            selectedCell.setOpen(true);

        if (selectedCell.getAdjacentBombs() == 0)
            openEmptyCells(i, j);

        return false;
    }

    public void mark(int i, int j) {
        if (isPositionValid(i, j)) {
            Cell selectedCell = this.board[i][j];
            selectedCell.setMarked(!selectedCell.isMarked());
        }
    }

    public boolean foundAllBombs() {
        int cellsWithoutBombOpen = 0;
        int totalCellsWithoutBomb = (this.xSize * this.ySize) - this.bombsNumber;

        for (int i = 0; i < this.xSize; i++) {
            for (int j = 0; j < this.ySize; j++) {
                Cell currentCell = this.board[i][j];

                if (currentCell.isOpen())
                    cellsWithoutBombOpen++;
            }
        }

        return cellsWithoutBombOpen == totalCellsWithoutBomb;
    }

    private String getEmptySpaces(String str, int offset) {
        int nSpaces = offset - str.length();
        return " ".repeat(Math.max(0, nSpaces));
    }

    public String toStringWithPositions() {
        StringBuilder str = new StringBuilder();

        int leftOffset = String.valueOf(xSize).length();
        int middleOffset = String.valueOf(ySize).length();

        str.append(" ");
        str.append(getEmptySpaces("", leftOffset));

        for (int i = 0; i < ySize; i++) {
            if (i != 0)
                str.append("|");

            String cellPosition = String.valueOf(i + 1);
            str.append(cellPosition);
            str.append(getEmptySpaces(cellPosition, middleOffset));
        }

        str.append('\n');

        for (int i = 0; i < xSize; i++) {
            String cellPosition = String.valueOf(i + 1);

            str.append(cellPosition);
            str.append(getEmptySpaces(cellPosition, leftOffset + 1));

            for (int j = 0; j < ySize; j++) {
                String cellStr = this.board[i][j].toString();

                str.append(cellStr);
                str.append(getEmptySpaces(cellStr, middleOffset + 1));
            }

            str.append('\n');
        }

        return str.toString();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                str.append(this.board[i][j].toString()).append(" ");
            }

            str.append('\n');
        }

        return str.toString();
    }
}
