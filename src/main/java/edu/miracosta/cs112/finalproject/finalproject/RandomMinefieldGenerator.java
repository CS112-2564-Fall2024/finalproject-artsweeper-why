package edu.miracosta.cs112.finalproject.finalproject;

import java.util.Random;

public class RandomMinefieldGenerator extends MinefieldGenerator {
    int height;
    int width;
    int mineCount;

    RandomMinefieldGenerator(int height, int width, int mineCount) {
        this.height = height;
        this.width = width;
        this.mineCount = mineCount;
    }

    @Override
    public Minefield generateMinefield() {
        // produce minefield
        Tile[][] map = new Tile[width][height];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                map[row][col] = new Tile(false, 0);
            }
        }

        // activate mines
        int count = 0;
        while (count < mineCount) {
            Random rand = new Random();
            double r = rand.nextInt(width*height);
            int row = (int) (r/width);
            int col = (int) (r%width);
            System.out.println("row: " + row + ", col: " + col + ", r: " + r);
            if (!map[row][col].isMine) {
                map[row][col].isMine = true;
                count++;
            }
        }

        return new Minefield(map, height, width);
    }
}
