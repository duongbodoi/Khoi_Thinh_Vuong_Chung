package brick;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

//CHIẾN
public class NormalBrick extends Brick {
    //gần như tương tự chỉ overide lại các hàm cha là được
    public NormalBrick (int x, int y, int width, int height, int hitPoint, String type, Image image) {
        super(x, y, width, height, hitPoint, type, image);
    }



    @Override
    public void render(GraphicsContext gc) {
        if (!destroy) {
            if (image != null) {
                gc.drawImage(image, x, y, width, height);
            } else {
                // fallback — nếu ảnh không tồn tại, vẽ tạm để debug
                gc.setFill(javafx.scene.paint.Color.GREEN);
                gc.fillRect(x, y, width, height);
                gc.setStroke(javafx.scene.paint.Color.BLACK);
                gc.strokeRect(x, y, width, height);
            }
        }
    }
}