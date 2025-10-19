package brick;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class UnbreakBrick extends Brick {

    public UnbreakBrick ( int x, int y, int width, int height,int hitPoint, String type, Image[] image) {
        super(x, y, width, height, hitPoint, type, image);
    }

    @Override
    public void render(GraphicsContext gc) {
        if (image != null) {
            gc.drawImage(image, x, y, width, height);
        } else {
            // fallback — nếu ảnh lỗi, vẽ tạm để debug
            gc.setFill(javafx.scene.paint.Color.BLACK);
            gc.fillRect(x, y, width, height);
            gc.setStroke(javafx.scene.paint.Color.GRAY);
            gc.strokeRect(x, y, width, height);
        }
    }

    @Override
    public void takeHit() {
        // Không làm gì cả — gạch này không thể phá
    }
}