package brick;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

//CHIẾN
public class StrongBrick extends Brick {
    public StrongBrick (int x, int y, int width, int height,int hitPoint, String type) {
        super(x, y, width, height, hitPoint, type);
    }

    @Override
    public void render(GraphicsContext gc) {
        if(!destroy) {
                gc.setFill(Color.RED);
                gc.fillRect(x, y, width, height);
        }
    }
}
