package powerup.Fire;

import entity.Paddle;
import powerup.PowerUp;

public class FastBall extends PowerUp {
    public FastBall(int x, int y, int width, int height, int time, String type) {
        super(x, y, width, height, time, type);
    }
    // Tăng tốc độ bóng và biến bóng thành bóng nhanh(đã cài trong Elemental), để phân biệt vs bóng đỏ
    @Override
    public void applyEffect(Paddle paddle) {

    }

    @Override
    public void removeEffect(Paddle paddle) {

    }

}
