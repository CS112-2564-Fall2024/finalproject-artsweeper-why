package edu.miracosta.cs112.finalproject.finalproject;

/**
 * This class constructs a Tile[][] minefield central to the game
 */
public class Minefield {
    Tile[][] field;
    int height;
    int width;

    /**
     * This constructor creates the minefield based on some parameters
     */
    public Minefield(Tile[][] field, int height, int width) {
        this.field = field;
        this.height = height;
        this.width = width;
    }

    /**
     * This method returns the Tile stored in field[y][x]
     */
    public Tile getTile(int x, int y) {
        return field[x][y];
    }

    /**
     * This method converts the minefield to a string
     * This can be used to visualize the minefield easily for testing
     */
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

    /**
     * These are all the setters and getters
     */
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