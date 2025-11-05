package powerup.Water;

import entity.Ball;
import entity.Elemental;
import entity.Paddle;
import javafx.scene.image.Image;
import powerup.PowerUp;

import java.util.List;

public class WaterBall extends PowerUp {
    public WaterBall(int x , int y, int with, int height, int time, String type, Image image) {
        super(x, y, with, height, time, type,image);
    }

    public void applyEffect(Paddle paddle) {
        List<Ball> balls = paddle.getBalls();
        for(Ball ball1 : balls) {
            ball1.setElemental(Elemental.WATER);
        }
        active = true;
        consumed = true;
    }

    public void removeEffect(Paddle paddle) {
        List<Ball> balls = paddle.getBalls();
        for(Ball ball1 : balls) {
            ball1.setElemental(Elemental.NONE);
        }
        active = false;
        consumed = false;
    }
}
