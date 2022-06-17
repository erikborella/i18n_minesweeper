package org.example.minesweeper;

public class Cell {
    private boolean isBomb;
    private boolean isOpen;
    private boolean isMarked;
    private int adjacentBombs;

    private int iPosition;
    private int jPosition;

    public Cell(int iPosition, int jPosition) {
        this.isBomb = false;
        this.isOpen = false;
        this.isMarked = false;
        this.adjacentBombs = 0;

        this.iPosition = iPosition;
        this.jPosition = jPosition;
    }

    int getIPosition() {
        return iPosition;
    }

    int getJPosition() {
        return jPosition;
    }

    boolean isBomb() {
        return isBomb;
    }

    void setBomb(boolean bomb) {
        isBomb = bomb;
    }

    int getAdjacentBombs() {
        return adjacentBombs;
    }

    void setAdjacentBombs(int adjacentBombs) {
        this.adjacentBombs = adjacentBombs;
    }

    boolean isOpen() {
        return isOpen;
    }

    void setOpen(boolean open) {
        isOpen = open;
    }

    boolean isMarked() {
        return isMarked;
    }

    void setMarked(boolean marked) {
        isMarked = marked;
    }

    @Override
    public String toString() {
        if (this.isMarked)
            return "p";

        if (!this.isOpen)
            return " ";
        else if (this.isBomb)
            return "x";
        else if (this.adjacentBombs == 0)
            return "-";
        else
            return String.valueOf(this.adjacentBombs);
    }
}
