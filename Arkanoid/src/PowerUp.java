package src;

public class PowerUp extends GameObject {
    protected String type;
    protected int duration;

    public PowerUp(){}
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
