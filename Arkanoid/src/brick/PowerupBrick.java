package brick;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.GregorianCalendar;

//CHIẾN
public class PowerupBrick extends Brick {
    //gần như tương tự chỉ overide lại các hàm cha là được
    public PowerupBrick ( int x, int y, int width, int height,int hitPoint, String type) {
        super(x, y, width, height, hitPoint, type);
    }

    @Override
    public void render(GraphicsContext gc) {
        if(!destroy) {
            gc.setFill(Color.BLUE);
            gc.fillRect(x, y, width, height);
        }
    }
}
