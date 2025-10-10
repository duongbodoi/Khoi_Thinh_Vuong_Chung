package brick;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class UnbreakBrick extends Brick {

    public UnbreakBrick(int x, int y, int width, int height,int hitPoint, String type) {
        super(x, y, width, height, hitPoint, type);
    }

    @Override
    public void render(GraphicsContext gc) {
        if(!destroy) {
            gc.setFill(Color.BLACK);
            gc.fillRect(x, y, width, height);
        }
    }
}

