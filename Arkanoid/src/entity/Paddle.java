package entity;

import base.MovableObject;

public class Paddle extends MovableObject {
    protected  int speed;
    protected  String currentPowerUp;

    public Paddle(int x, int y, int width, int height, int dx, int dy, int speed) {
        super(x, y, width, height, dx, dy);
        this.speed = speed;
        this.currentPowerUp = null;
    }

    public void moveLeft() {
        dx = -speed;
        move();
    }

    public void moveRight() {
        dx = speed;
        move();
    }

    public void applyPowerUp(String powerUp) {
        currentPowerUp = powerUp;
    }

    public void update() {
        //logic cap nhat paddle
    }

    public void render() {
        //ve paddle
    }
}
