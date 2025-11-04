package powerup.Plant;

import entity.Ball;
import entity.Elemental;
import entity.Paddle;
import powerup.PowerUp;

import java.util.List;

public class LeafBall extends PowerUp {
    public LeafBall(int x ,int y, int with, int height, int time, String type) {
        super(x, y, with, height, time, type);
    }

    public void applyEffect(Paddle paddle, Ball ball) {
        List<Ball> balls = paddle.getBalls();
        for(Ball ball1 : balls) {
            ball1.setElemental(Elemental.LEAF);
        }
        active = true;
        consumed = true;
    }

    public void removeEffect(Paddle paddle, Ball ball) {
        List<Ball> balls = paddle.getBalls();
        for(Ball ball1 : balls) {
            ball1.setElemental(Elemental.NONE);
        }
        active = false;
        consumed = false;
    }
}