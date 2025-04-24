package edu.miracosta.cs112.finalproject.finalproject;

public class Minefield {
    Tile[][] field;
    int height;
    int width;

    // setters and getters
    public Minefield(Tile[][] field, int height, int width) {
        this.field = field;
        this.height = height;
        this.width = width;
    }

    public Tile getTile(int x, int y) {
        return field[x][y];
    }

    public String toString() {
        StringBuilder output = new StringBuilder();
        for (Tile[] row : field) {
            for (Tile tile : row) {
                output.append(tile.toString());
            }
            output.append("\n");
        }
        return output.toString();
    }

    // getters and setters
    public Tile[][] getField() {
        return field;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setField(Tile[][] field) {
        this.field = field;
    }
}