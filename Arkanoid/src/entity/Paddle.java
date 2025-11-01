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

    private final BallProvider ballProvider;

    public Paddle(int x, int y, int width, int height, int dx, int dy, int speed, BallProvider ballProvider) {
        super(x, y, width, height, dx, dy);
        this.speed = speed;
        this.currentPowerUp = null;
        isLeft = false;
        isRight = false;
        this.ballProvider = null;
    }

    // lấy chiều dài của paddle vì cần update khi dùng power up
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
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

    //trả về 1 quả bóng còn sống
    public Ball getAnyBall() {
        if (ballProvider == null) {
            return null;
        } else {
            return ballProvider.getAnyBall();
        }
    }

    //thêm bong mới
    public void addBall(Ball b) {
        if (ballProvider != null && b != null) {
            ballProvider.addBall(b);
        }
    }
}
