package powerup.Fire;

import entity.Ball;
import entity.Paddle;
import powerup.PowerUp;

public class FastBall extends PowerUp {
    private int Speedold;
    public FastBall(int x, int y, int width, int height, int time, String type) {
        super(x, y, width, height, time, type);
    }
    // Tăng tốc độ bóng và biến bóng thành bóng nhanh(đã cài trong Elemental), để phân biệt vs bóng đỏ
    @Override
    public void applyEffect(Paddle paddle, Ball ball) {
        ball = paddle.getAnyBall();
        if(ball != null) {
            Speedold = ball.getSpeed();
            ball.setSpeed(ball.getSpeed() + 5);
            paddle.applyPowerUp(type);
            active = true;
            consumed = true;
        }
    }

    @Override
    public void removeEffect(Paddle paddle, Ball ball) {
        ball = paddle.getAnyBall();
        if (ball != null) {
            ball.setSpeed(Speedold);
        }
    }
}
