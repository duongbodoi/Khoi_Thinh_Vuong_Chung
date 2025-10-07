package brick;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class UnbreakBrick extends Brick {

    public UnbreakBrick(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.hitPoints = 200;
    }

    @Override
    public void render(GraphicsContext gc) {
        if(!destroy && hitPoints >= 100) {
            gc.setFill(Color.BLACK);
            gc.fillRect(x, y, width, height);
        }
    }
}

