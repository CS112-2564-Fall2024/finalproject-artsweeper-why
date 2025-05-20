package edu.miracosta.cs112.finalproject.finalproject;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class ImageMinefieldGenerator extends MinefieldGenerator {
    String imagePath;
    int intensity;
    int temperature;
    boolean deterministic;

    public ImageMinefieldGenerator(String imagePath, int intensity, int temperature, boolean deterministic) {
        this.imagePath = imagePath;
        this.intensity = intensity;
        this.temperature = temperature;
        this.deterministic = deterministic;
    }

    @Override
    public Minefield generateMinefield() {
        try {
            System.out.println(imagePath);
            BufferedImage image = ImageIO.read(getClass().getResource(imagePath));
            if (image == null) {
                System.out.println("Image not found!");
                return null;
            }

            Tile[][] map = ImageProcessor.processImage(image, intensity, temperature, deterministic);
            return new Minefield(map, map.length, map[0].length);
        }
        catch (IOException e) {
            System.out.println("Error while processing image: " + e.getMessage());
        }
        return null;
    }

    private static class ImageProcessor {
        public static Tile[][] processImage(BufferedImage image, int intensity, int temperature, boolean deterministic) {
            Tile[][] map = new Tile[image.getHeight()][image.getWidth()];

            for (int row = 0; row < image.getHeight(); row++) {
                for (int col = 0; col < image.getWidth(); col++) {
                    int pixel = image.getRGB(col, row);
                    int red = (pixel >> 16) & 0xff;
                    int green = (pixel >> 8) & 0xff;
                    int blue = pixel & 0xff;
                    int greyValue = (red + green + blue) / 3;

                    if (greyValue < 20) {
                        System.out.println("Dark pixel");
                        System.out.println(greyValue);
                    }

                    Random rand = new Random();
                    greyValue += rand.nextInt(temperature * 2) - temperature;
                    greyValue = Math.max(0, Math.min(255, greyValue));
                    greyValue -= intensity;

                    System.out.println("Intensity: " + intensity);
                    System.out.println("Temperature: " + temperature);

                    // calculate chance, clamp between 0.05 and 0.95
                    double chance = Math.max(Math.min((greyValue) / 255.0, 0.95), 0.05);
                    System.out.println("Chance: " + chance);

                    Tile t;
                    if(deterministic) {
                        if (greyValue < 70) {
                            t = new Tile(true, greyValue, col, row);
                        }
                        else {
                            t = new Tile(false, greyValue, col, row);
                        }
                    }
                    else {
                        if (rand.nextDouble() > chance) {
                            t = new Tile(true, greyValue, col, row);
                        } else {
                            t = new Tile(false, greyValue, col, row);
                        }
                    }
                    map[row][col] = t;
                }
            }

            return map;
        }
    }
}
