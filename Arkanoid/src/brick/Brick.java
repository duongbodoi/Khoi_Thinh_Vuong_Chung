package brick;

import base.GameObject;

public class Brick extends GameObject {
    protected int hitPoints;
    protected  String type;

    public Brick(){}
    public Brick(int x, int y, int width, int height, int hitPoints, String type) {
        super(x,y,width,height);
        this.hitPoints = hitPoints;
        this.type = type;
    }

    public void takeHit(){

    }

    public void isDestroyed() {

    }

    public void update() {

    }

    public void render() {

    }

}
