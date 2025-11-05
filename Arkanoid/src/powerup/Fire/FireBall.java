package powerup.Fire;

import entity.Ball;
import entity.Elemental;
import entity.Paddle;
import javafx.scene.image.Image;
import powerup.PowerUp;
import powerup.Type;

import java.util.List;

public class FireBall extends PowerUp {
    public FireBall(int x, int y, int width, int height, int time, String type, Image image) {
        super(x, y, width, height, time, type,image);
        powerUpType= Type.ELEMENTAL;
    }

    @Override
    public void applyEffect(Paddle paddle) {
        List<Ball> balls = paddle.getBalls();
        for(Ball ball1 : balls) {
            ball1.setElemental(Elemental.FIRE);
        }
        consumed = true;
        active = true;
    }

    @Override
    public void removeEffect(Paddle paddle) {
        List<Ball> balls = paddle.getBalls();
        for(Ball ball1 : balls) {
            ball1.setElemental(Elemental.NONE);
        }
        active = false;
    }

}
