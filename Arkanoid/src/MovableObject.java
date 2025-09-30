package src;
public abstract class MovableObject extends GameObject{
    protected  int dx, dy;

    public MovableObject(){};

    public MovableObject(int x, int y, int width, int height, int dx, int dy) {
        super(x,y,width,height);
        this.dx = dx;
        this.dy = dy;
    }

    public void move() {
        x += dx;
        y += dy;
    }


}
