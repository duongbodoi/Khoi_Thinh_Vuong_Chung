package brick;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

//CHIẾN
public class StrongBrick extends Brick {
    public StrongBrick (int x, int y, int width, int height) {
        super(x, y, width, height);
        this.hitPoints = 2;
    }

    @Override
    public void render(GraphicsContext gc) {
        if(!destroy) {
                gc.setFill(Color.RED);
                gc.fillRect(x, y, width, height);
        }
    }
}
