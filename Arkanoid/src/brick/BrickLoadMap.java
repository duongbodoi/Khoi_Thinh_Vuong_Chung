package brick;

import engine.LoadImage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class BrickLoadMap {
    private static int brickCount ;

    private static Brick[][] grid; // Mảng 2D để tra cứu logic
    private static int gridRows;
    private static int gridCols;
    public static List<Brick> loadBricks(String file, int screenWidth, LoadImage loadImage) {
        brickCount = 0;
        List<Brick> bricks = new ArrayList<>();
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line.trim());
                }
            }
        } catch (Exception e) {
            System.err.println("lỗi khi đọc map: " + e.getMessage());
            return bricks;
        }

        int brickRows = lines.size();
        int brickCols = lines.get(0).split("\\s+").length;

        int dbrick = 1;
        int dx = 10;
        int dy = 60;
        int brickWidth = (screenWidth - (brickCols + 1) * dbrick) / brickCols;
        int brickHeight = 25;

        gridRows = lines.size();
        if (gridRows == 0) return bricks;
        gridCols = lines.get(0).split("\\s+").length;
        grid = new Brick[gridRows][gridCols];

        for (int row = 0; row < brickRows; row++) {
            String[] tokens = lines.get(row).split("\\s+");
            for (int col = 0; col < brickCols; col++) {
                int value = Integer.parseInt(tokens[col]);
                if (value == 0) continue;

                int x = dx + col * (brickWidth + dbrick);
                int y = dy + row * (brickHeight + dbrick);
                String type = "type" + value;
                String imagePath;
                if(value!=100) brickCount ++;
                Brick newBrick = null;

                switch (value) {
                    case 1:
                        newBrick = new NormalBrick(x, y, brickWidth, brickHeight, value, type, loadImage.getNormalBrick());
                        break;
                    case 2:
                        newBrick = new StrongBrick(x, y, brickWidth, brickHeight, value, type, loadImage.getStrongBrick());
                        break;
                    case 3:
                        newBrick = new PowerupBrick(x, y, brickWidth, brickHeight, value, type, loadImage.getPowerUpBrick());
                        break;
                    case 4:
                        newBrick = new SoilBrick(x, y, brickWidth, brickHeight, value, type, loadImage.getSoilBrick());
                        break;
                    case 100:
                        newBrick = new UnbreakBrick(x, y, brickWidth, brickHeight, value, type, loadImage.getUnbreakBrick());
                        break;
                    default:
                        System.out.println("Không nhận diện được loại gạch: " + value);
                        break;
                }

                if (newBrick != null) {
                    newBrick.setGridPosition(col, row);
                    bricks.add(newBrick);
                    grid[row][col] = newBrick;
                }
            }
        }

        return bricks;
    }

    public static int getBrickCount() { return brickCount; }
    public static Brick[][] getGrid() { return grid; }
    public static int getGridRows() { return gridRows; }
    public static int getGridCols() { return gridCols; }
    public static void incrementBrickCount() { brickCount++; }
}
