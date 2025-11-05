package powerup.Soid;

import entity.Ball;
import entity.Effect;
import entity.Paddle;
import javafx.scene.image.Image;
import powerup.Plant.StunPaddle;
import powerup.PowerUp;
import powerup.Type;

public class SlowPaddle extends PowerUp {

    public SlowPaddle(int x, int y, int width, int height, int time, String type, Image image) {
        super(x, y, width, height, time, type,image);
        powerUpType= Type.BUFF;

    }

    @Override
    public void applyEffect(Paddle paddle) {
        paddle.setEffect(Effect.SLOW);
        paddle.setSpeed(Math.max(1, paddle.getSpeed() / 2));
        paddle.applyPowerUp(type);
        consumed = true;
        active = true;
    }

    @Override
    public void removeEffect(Paddle paddle) {
        paddle.setEffect(Effect.NONE);
        paddle.setSpeed(paddle.getOldSpeed());
        active = false;
    }

}
