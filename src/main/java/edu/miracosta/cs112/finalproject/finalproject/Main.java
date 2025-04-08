package edu.miracosta.cs112.finalproject.finalproject;

public class Main {
    public static void main(String[] args) {
        int height = 16;
        int width = 16;
        int mineCount = 100;

        // random test
        RandomMinefieldGenerator randomGenerator = new RandomMinefieldGenerator(height, width, mineCount);
        Minefield randomMinefield = randomGenerator.generateMinefield();

        System.out.println(randomMinefield.toString());

        // image test
        //String imagePath = "/images/apple.jpg";
        //ImageMinefieldGenerator imageMinefieldGenerator = new ImageMinefieldGenerator(imagePath, 1, 1, false);
        //Minefield imageMinefield = imageMinefieldGenerator.generateMinefield();

        //System.out.println(imageMinefield.toString());

    }
}