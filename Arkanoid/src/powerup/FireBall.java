package powerup;

import entity.Ball;
import entity.Elemental;
import entity.Paddle;

import java.util.List;

public class FireBall extends PowerUp {
    public FireBall(int x, int y, int width, int height, int time, String type) {
        super(x, y, width, height, time, type);
    }

    @Override
    public void applyEffect(Paddle paddle) {
        List<Ball> balls = paddle.getBalls();
        for(Ball ball : balls) {
            ball.setElemental(Elemental.FIRE);
        }
        consumed = true;
        active = true;
    }

    @Override
    public void removeEffect(Paddle paddle) {
        List<Ball> balls = paddle.getBalls();
        for(Ball ball : balls) {
            ball.setElemental(Elemental.NONE);
        }
        active = false;
    }

}
