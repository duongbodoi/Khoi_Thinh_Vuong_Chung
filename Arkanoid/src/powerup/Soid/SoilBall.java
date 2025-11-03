package powerup.Soid;

import entity.Ball;
import entity.Elemental;
import entity.Paddle;
import powerup.PowerUp;

import java.util.List;

public class SoilBall extends PowerUp {
    public SoilBall(int x , int y, int with, int height, int time, String type) {
        super(x, y, with, height, time, type);
    }

    public void applyEffect(Paddle paddle) {
        List<Ball> balls = paddle.getBalls();
        for(Ball ball : balls) {
            ball.setElemental(Elemental.SOID);
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
