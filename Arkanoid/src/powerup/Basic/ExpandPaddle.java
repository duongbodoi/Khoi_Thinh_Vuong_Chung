package powerup.Basic;

import entity.Paddle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import powerup.PowerUp;

public class ExpandPaddle extends PowerUp {

    private int oldWidth; //độ dài cũ paddle
    private final int expandFactor = 2; //mở rộng 2 lần (hệ số)

    public ExpandPaddle(int x, int y, int width, int height, Image image) {
        super(x, y, width, height, 5000, "E",image); // thời gian 30s, loại E
    }

    @Override
    public void applyEffect(Paddle paddle) {
        active  = true;
        consumed = true;
        oldWidth = paddle.getWidth();
        paddle.setWidth(oldWidth * expandFactor);
        if(paddle.getWidth() > 3*paddle.getOldWidth())
            paddle.setWidth((int)3*paddle.getOldWidth());
        }

    @Override
    public void removeEffect(Paddle paddle) {

    }


}