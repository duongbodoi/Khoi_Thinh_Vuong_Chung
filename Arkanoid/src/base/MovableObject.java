package base;
public abstract class MovableObject extends GameObject{
    protected  double dx, dy;
    public MovableObject(){};

    public MovableObject(int x, int y, int width, int height, double dx, double dy) {
        super(x,y,width,height);
        this.dx = dx;
        this.dy = dy;
    }

    public void move() {
        x += dx;
        y += dy;
    }


}
