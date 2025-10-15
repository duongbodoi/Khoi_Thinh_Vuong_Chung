package brick;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

//CHIẾN
public class StrongBrick extends Brick {
    public StrongBrick ( int x, int y, int width, int height,int hitPoint, String type, Image image) {
        super(x, y, width, height, hitPoint, type, image);
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!destroy) {
            if (image != null) {
                gc.drawImage(image, x, y, width, height);
            } else {
                // fallback — nếu không có ảnh thì vẽ tạm
                gc.setFill(javafx.scene.paint.Color.RED);
                gc.fillRect(x, y, width, height);
                gc.setStroke(javafx.scene.paint.Color.BLACK);
                gc.strokeRect(x, y, width, height);
            }
        }
    }
}