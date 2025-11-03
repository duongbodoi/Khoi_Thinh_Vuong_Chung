package powerup.Plant;
import entity.Paddle;
import powerup.PowerUp;

public class StunPaddle extends PowerUp {
    public StunPaddle(int x, int y, int width, int height, int time, String type) {
        super(x, y, width, height, time, type);
    }

    // làm paddle ko thể di chuyển, kiểu như bị dây leo trói lại ko đi được v
    @Override
    public void applyEffect(Paddle paddle) {

    }

    @Override
    public void removeEffect(Paddle paddle) {

    }

}
