package entity;

import static engine.Main.GAME_WIDTH;

import base.MovableObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.sence.paint.Color;
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
        x = -speed;
    }

    public void moveRight() {
        x = speed;
    }
    // Để lại
    public void applyPowerUp(String powerUp) {
        currentPowerUp = powerUp;
    }


    @Override
    public void update() {
        x += dx;
        // giới hạn paddle
        if (x < 0) {
            x = 0;
        }
        if (x + width > GAME_WIDTH) {
            x = GAME_WIDTH - width;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.GRAY;
        gc.fillRect(x, y, width, height);
    }
}
