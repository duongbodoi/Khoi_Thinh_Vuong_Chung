package powerup.Plant;

import entity.Ball;
import entity.Elemental;
import entity.Paddle;
import javafx.scene.image.Image;
import powerup.PowerUp;
import powerup.Type;

import java.util.List;

public class LeafBall extends PowerUp {
    public LeafBall(int x , int y, int with, int height, int time, String type, Image image) {
        super(x, y, with, height, time, type,image);
        powerUpType= Type.ELEMENTAL;
    }

    public void applyEffect(Paddle paddle) {
        List<Ball> balls = paddle.getBalls();
        for(Ball ball1 : balls) {
            ball1.setElemental(Elemental.LEAF);
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