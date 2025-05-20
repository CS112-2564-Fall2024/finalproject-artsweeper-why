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

            // Step 1: Compute average brightness of the image
            long totalBrightness = 0;
            int width = image.getWidth();
            int height = image.getHeight();

            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    int pixel = image.getRGB(col, row);
                    int red = (pixel >> 16) & 0xff;
                    int green = (pixel >> 8) & 0xff;
                    int blue = pixel & 0xff;
                    int grey = (red + green + blue) / 3;
                    totalBrightness += grey;
                }
            }

            double avgBrightness = totalBrightness / (double) (width * height);


            Random rand = new Random();

            double contrast = 0.6;

            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    int pixel = image.getRGB(col, row);
                    int red = (pixel >> 16) & 0xff;
                    int green = (pixel >> 8) & 0xff;
                    int blue = pixel & 0xff;

                    int greyValue = (red + green + blue) / 3;
                    greyValue += rand.nextInt(temperature * 2) - temperature;
                    greyValue = Math.max(0, Math.min(255, greyValue));
                    greyValue -= intensity;

                    double globalWeight = 2.0;

                    double darknessFactor = ((avgBrightness - greyValue) * globalWeight) / 255.0;

                    double mineBias = 0.1;
                    double mineChance = 0.5 + darknessFactor * contrast;
                    mineChance -= mineBias;
                    mineChance = Math.max(0.05, Math.min(mineChance, 0.95));

                    boolean isMine = rand.nextDouble() < mineChance;
                    Tile t = new Tile(isMine, greyValue, col, row);
                    map[row][col] = t;
                }
            }

            return map;
        }
    }
}
