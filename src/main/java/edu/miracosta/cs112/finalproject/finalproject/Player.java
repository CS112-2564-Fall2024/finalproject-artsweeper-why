package edu.miracosta.cs112.finalproject.finalproject;

public class Player {
    private static void flush(Tile[][] map, int row, int col) {
        int height = map.length;
        int width = map[0].length;

        if (row < 0 || row >= height || col < 0 || col >= width) return;

        Tile t = map[row][col];

        if (t.getIsClicked() || t.getIsMine()) return;

        t.setClicked(true);

        int mineCount = 0;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                int newRow = row + dr;
                int newCol = col + dc;
                if (newRow >= 0 && newRow < height && newCol >= 0 && newCol < width) {
                    if (map[newRow][newCol].getIsMine()) {
                        mineCount++;
                    }
                }
            }
        }

        if (t.getLabel() != null) {
            int grey = t.getGreyVal();
            String bgColor = String.format("rgb(%d, %d, %d)", grey, grey, grey);

            String textColor = switch (mineCount) {
                case 1 -> "#0000FF";      // Blue
                case 2 -> "#008000";      // Green
                case 3 -> "#FF0000";      // Red
                case 4 -> "#000080";      // Dark Blue
                case 5 -> "#800000";      // Maroon
                case 6 -> "#008B8B";      // Turquoise
                case 7 -> "#000000";      // Black
                case 8 -> "#808080";      // Gray
                default -> "#0000FF";
            };

            t.getLabel().setStyle("-fx-background-color: " + bgColor + ";"
                    + "-fx-text-fill: " + textColor + ";");
            t.getLabel().setText(mineCount == 0 ? "" : String.valueOf(mineCount));
        }

        if (mineCount == 0) {
            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    if (dr != 0 || dc != 0) {
                        flush(map, row + dr, col + dc);
                    }
                }
            }
        }
    }

    static void clickTile(Tile[][] map, Tile t) throws GameOverException {
        if(t.getIsClicked() || t.getIsFlagged()) return;

        System.out.println("Clicked tile at " + t.getX() + ", " + t.getY());
        if (t.getIsMine()) {
            throw new GameOverException("Game over! You clicked on a mine at (" + t.getX() + ", " + t.getY() + ")");
        } else {
            flush(map, t.getY(), t.getX());
        }
    }

    static void flagTile(Tile t) {
        if(t.getIsClicked()) return;
        t.setIsFlagged(!t.getIsFlagged());

        System.out.println(t.getIsFlagged());

        if (t.getIsFlagged()) {
            t.getLabel().setText("⚑");
            t.getLabel().setStyle("-fx-background-color: #ff4d00; -fx-border-color: black;");
        } else {
            t.getLabel().setText("");
            t.getLabel().setStyle("-fx-background-color: #5ED500FF; -fx-border-color: rgba(0,0,0,0);");
        }
    }
}