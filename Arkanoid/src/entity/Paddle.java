package entity;

import base.MovableObject;
import javafx.scene.canvas.GraphicsContext;
// NGỌC ANH LÀM PHẦN NÀY
public class Paddle extends MovableObject {
    protected  int speed;
    protected  String currentPowerUp;

    public Paddle(int x, int y, int width, int height, int dx, int dy, int speed) {
        super(x, y, width, height, dx, dy);
        this.speed = speed;
        this.currentPowerUp = null;
    }


    public void moveLeft() {

    }

    public void moveRight() {

    }
    // Để lại
    public void applyPowerUp(String powerUp) {
        currentPowerUp = powerUp;
    }


    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext gc) {

    }


}
