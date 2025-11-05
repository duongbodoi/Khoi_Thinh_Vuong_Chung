package powerup.Water;

import entity.Ball;
import entity.Elemental;
import entity.Paddle;
import javafx.scene.image.Image;
import powerup.PowerUp;
import powerup.Type;

import java.util.List;

public class IceBall extends PowerUp {
    public IceBall(int x, int y, int width, int height, int time, String type, Image image) {
        super(x, y, width, height, time, type,image);
        powerUpType= Type.BUFF;
    }
    // Tăng tốc độ bóng và biến bóng thành bóng nhanh(đã cài trong Elemental), để phân biệt vs bóng đỏ
    @Override
    public void applyEffect(Paddle paddle) {
        List<Ball> balls = paddle.getBalls();
        for(Ball ball : balls) {
            ball.setSpeed(ball.getSpeed() - 4);
            paddle.applyPowerUp(type);
        }
        active = true;
        consumed = true;
    }

    @Override
    public void removeEffect(Paddle paddle) {
        List<Ball> balls = paddle.getBalls();
        for(Ball ball : balls) {
            ball.setSpeed(ball.getOldSpeed());
            paddle.applyPowerUp(type);
        }
    }
}
