package brick;

import engine.LoadImage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class BrickLoadMap {
    public static List<Brick> loadBricks(String file, int screenWidth, LoadImage loadImage) {
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

        for (int row = 0; row < brickRows; row++) {
            String[] tokens = lines.get(row).split("\\s+");
            for (int col = 0; col < brickCols; col++) {
                int value = Integer.parseInt(tokens[col]);
                if (value == 0) continue;

                int x = dx + col * (brickWidth + dbrick);
                int y = dy + row * (brickHeight + dbrick);
                String type = "type" + value;
                String imagePath;

                switch (value) {
                    case 1:
                        //imagePath = "assets/IMAGE/green.png";
                        bricks.add(new NormalBrick(x, y, brickWidth, brickHeight, value, type, loadImage.getNormalBrick()));
                        break;
                    case 2:
                        //imagePath = "assets/IMAGE/red.png";
                        bricks.add(new StrongBrick(x, y, brickWidth, brickHeight, value, type, loadImage.getStrongBrick()));
                        break;
                    case 3:
                        //imagePath = "assets/IMAGE/blue.png";
                        bricks.add(new PowerupBrick(x, y, brickWidth, brickHeight, value, type, loadImage.getPowerUpBrick()));
                        break;
                    case 4:
                        bricks.add(new SoilBrick(x, y, brickWidth, brickHeight, value, type, loadImage.getSoilBrick()));
                    case 100:
                        //imagePath = "assets/IMAGE/black.png";
                        bricks.add(new UnbreakBrick(x, y, brickWidth, brickHeight, value, type, loadImage.getUnbreakBrick()));
                        break;
                    default:
                        System.out.println("⚠️ Không nhận diện được loại gạch: " + value);
                        break;
                }
            }
        }

        return bricks;
    }
}
