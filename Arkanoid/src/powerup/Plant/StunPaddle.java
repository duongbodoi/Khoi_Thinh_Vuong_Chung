package powerup.Plant;
import entity.Ball;
import entity.Effect;
import entity.Paddle;
import javafx.scene.image.Image;
import powerup.PowerUp;
import powerup.Type;

public class StunPaddle extends PowerUp {
    public StunPaddle(int x, int y, int width, int height, int time, String type, Image image) {
        super(x, y, width, height, time,type,image);
        powerUpType= Type.BUFF;
    }

    // làm paddle ko thể di chuyển, kiểu như bị dây leo trói lại ko đi được v
    @Override
    public void applyEffect(Paddle paddle) {
        if(paddle != null) {
            paddle.setEffect(Effect.STUNNED);
            paddle.setMove(false);
            active = true;
            consumed = true;
        }
    }

    @Override
    public void removeEffect(Paddle paddle) {
        if (paddle != null) {
            paddle.setEffect(Effect.NONE);
            paddle.setMove(true);
        }
        active = false;
        consumed = false;
    }
}
