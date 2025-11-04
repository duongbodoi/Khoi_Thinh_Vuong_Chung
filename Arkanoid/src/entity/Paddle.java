package entity;
import static engine.Main.GAME_WIDTH;

import base.MovableObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.List;

// NGỌC ANH LÀM PHẦN NÀY
public class Paddle extends MovableObject implements BallProvider{
    protected  int speed;
    protected  String currentPowerUp;
    boolean isLeft;
    boolean isRight;
    private final BallProvider ballProvider;
    private final int oldWidth;
    private final int oldSpeed;
    protected Image[] images;
    private Effect effect = Effect.NONE;
    private boolean isMove = true;
    public Paddle(int x, int y, int width, int height, int dx, int dy, int speed, BallProvider ballProvider, Image[] images) {
        super(x, y, width, height, dx, dy);
        this.speed = speed;
        this.currentPowerUp = null;
        this.images = images;
        isLeft = false;
        isRight = false;
        oldSpeed = speed;
        this.ballProvider = ballProvider;
        oldWidth = width;
    }

    public int getOldWidth() {
        return oldWidth;
    }

    // lấy chiều dài của paddle vì cần update khi dùng power up
    public int getWidth() {
        return width;
    }

    public int getOldSpeed() {
        return oldSpeed;
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

    public boolean isMove() {
        return isMove;
    }

    public void setMove(boolean move) {
        isMove = move;
    }

    public void applyPowerUp(String powerUp) {
        currentPowerUp = powerUp;
    }

    @Override
    public void move() {
        if (!isMove) {
            return;
        } else {
            if (isLeft) {
                x -= speed;
            }
            if (isRight) {
                x += speed;
            }
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


    //trả về 1 quả bóng còn sống
    public Ball getAnyBall() {
        if (ballProvider == null) {
            return null;
        } else {
            return ballProvider.getAnyBall();
        }
    }

    @Override
    public List<Ball> getBalls() {
        if (ballProvider == null) {
            return null;
        } else {
            return ballProvider.getBalls();
        }
    }
    @Override
    public void render(GraphicsContext gc) {
        image=images[effect.ordinal()];
        super.render(gc);
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    //thêm bong mới
    public void addBall(Ball b) {
        if (ballProvider != null && b != null) {
            ballProvider.addBall(b);
        }
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
