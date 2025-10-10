package entity;
import static engine.Main.GAME_WIDTH;

import base.MovableObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
// NGỌC ANH LÀM PHẦN NÀY
public class Paddle extends MovableObject {
    protected  int speed;
    protected  String currentPowerUp;
    boolean isLeft;
    boolean isRight;
    public Paddle(int x, int y, int width, int height, int dx, int dy, int speed) {
        super(x, y, width, height, dx, dy);
        this.speed = speed;
        this.currentPowerUp = null;
        isLeft = false;
        isRight = false;
    }


    public void moveLeft(boolean isMove) {
        isLeft = isMove;
    }

    public void moveRight(boolean isMove) {
        isRight = isMove;
    }
    public void applyPowerUp(String powerUp) {
        currentPowerUp = powerUp;
    }
    @Override
    public void move() {
        if(isLeft) {
            x-=speed;
        }
        if(isRight) {
            x+=speed;
        }
    }
    @Override
    public void update() {
        move();
        // giới hạn paddle
        if (x <= 0) {
            x = 0;
        }
        if (x + width > GAME_WIDTH) {
            x = GAME_WIDTH - width;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
       gc.setFill(Color.RED);
       gc.fillRect(x, y, width, height);
    }
}
