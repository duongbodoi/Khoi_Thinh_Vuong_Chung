package powerup;

import entity.Ball;
import entity.Elemental;
import entity.Paddle;

import java.util.List;

public class WaterBall extends PowerUp {
    public WaterBall(int x , int y, int with, int height, int time, String type) {
        super(x, y, with, height, time, type);
    }

    public void applyEffect(Paddle paddle) {
        List<Ball> balls = paddle.getBalls();
        for(Ball ball : balls) {
            ball.setElemental(Elemental.WATER);
        }
        active = true;
        consumed = true;
    }

    public void removeEffect(Paddle paddle) {
        List<Ball> balls = paddle.getBalls();
        for(Ball ball : balls) {
            ball.setElemental(Elemental.NONE);
        }
        active = false;
        consumed = false;
    }
}