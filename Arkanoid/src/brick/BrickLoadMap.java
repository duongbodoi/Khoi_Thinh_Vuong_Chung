package brick;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class BrickLoadMap {
    public static List<Brick> loadBricks(String file, int brickWidth, int brickHeight) {
        List<Brick> bricks = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int row = 0;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] tokens = line.trim().split("\\s+");

                for (int col = 0; col < tokens.length; col++) {
                    int value = Integer.parseInt(tokens[col]);
                    if (value == 0) continue;

                    int x = col * brickWidth;
                    int y = row * brickHeight;
                    int hitPoints = value;
                    String type = "type" + value;

                    Brick brick = new Brick(x, y, brickWidth, brickHeight, hitPoints, type);
                    bricks.add(brick);
                }

                row++;
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi đọc map: " + e.getMessage());
        }

        return bricks;
    }
}
