package powerup;

import base.GameObject;
import entity.Paddle;

public class PowerUp extends GameObject {
    protected String type;
    protected int duration;

    public PowerUp(){}

    @Override
    public void update() {

    }

    @Override
    public void render() {

    }

    public PowerUp(int x, int y, int width, int height, int duration, String type) {
        super(x,y,width,height);
        this.duration = duration;
        this.type = type;
    }

    public void applyEffect(Paddle paddle) {

    }

    public void removeEffect(Paddle paddle) {

    }
}
