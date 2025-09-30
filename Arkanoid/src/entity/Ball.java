package entity;

import base.GameObject;
import base.MovableObject;

public class Ball extends MovableObject {
    protected int speed;
    protected int directionX, directionY;

    public Ball(int x, int y, int width, int height, int speed, int directionX,int directionY) {
        super(x, y, width, height, directionX*speed, directionY*speed);
        this.speed = speed;
        this.directionX = directionX;
        this.directionY = directionY;
    }

    public void bounceOff(GameObject other) {

    }

    public void checkCollision(GameObject other) {

    }

    public void update() {

    }

    public void render() {

    }
}
