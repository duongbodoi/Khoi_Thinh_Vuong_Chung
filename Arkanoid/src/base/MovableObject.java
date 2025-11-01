package base;
public abstract class MovableObject extends GameObject{
    protected  double dx, dy;
    public MovableObject(){};

    public MovableObject(int x, int y, int width, int height, double dx, double dy) {
        super(x,y,width,height);
        this.dx = dx;
        this.dy = dy;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public void move() {
        x += dx;
        y += dy;
    }


}
