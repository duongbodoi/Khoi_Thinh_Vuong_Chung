package brick;

import engine.LoadImage;
import engine.ExplosionControl.SoilExplosion;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

//CHIẾN
public class SoilBrick extends Brick {
    //gần như tương tự chỉ overide lại các hàm cha là được
    public SoilBrick (int x, int y, int width, int height, int hitPoint, String type, Image[] image) {
        super(x, y, width, height, hitPoint, type, image);
    }


    @Override
    public void createExplosion(Pane root, LoadImage loader) {
        new SoilExplosion(x + width / 2.0, y + height / 2.0, root, loader);
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!destroy) {
            if (image != null) {
                gc.drawImage(image, x, y, width, height);
            } else {
                // fallback — nếu ảnh không tồn tại, vẽ tạm để debug
                gc.setFill(javafx.scene.paint.Color.BROWN);
                gc.fillRect(x, y, width, height);
                gc.setStroke(javafx.scene.paint.Color.BLACK);
                gc.strokeRect(x, y, width, height);
            }
        }
    }
}