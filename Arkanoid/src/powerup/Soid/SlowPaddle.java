package powerup.Soid;

import entity.Ball;
import entity.Paddle;
import powerup.Plant.StunPaddle;
import powerup.PowerUp;

public class SlowPaddle extends PowerUp {
    private int reduceSpeed;
    public SlowPaddle(int x, int y, int width, int height, int time, String type) {
        super(x, y, width, height, time, type);
    }

    @Override
    public void applyEffect(Paddle paddle) {
        reduceSpeed = paddle.getSpeed();
        paddle.setSpeed(Math.max(1, paddle.getSpeed() / 2));
        paddle.applyPowerUp(type);
        consumed = true;
        active = true;
    }

    @Override
    public void removeEffect(Paddle paddle) {
        paddle.setSpeed(reduceSpeed);
    }

}
