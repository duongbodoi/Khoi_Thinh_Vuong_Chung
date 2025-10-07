package entity;

import base.GameObject;
import base.MovableObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

import java.util.Random;

// Dương làm
public class Ball extends MovableObject {
    protected int speed;
    protected int directionX, directionY;
    protected double angle = 0;
    public Ball(int x, int y, int width, int height, int speed, int directionX,int directionY) {
        super(x, y, width, height, directionX*speed, directionY*speed);
        this.speed = speed;
        this.directionX = directionX;
        this.directionY = directionY;
    }

    /**
     * Thao tác phản lại khi va chạm vào vật thể.
     * @param other vật thể truyền vào có thể là brick hoặc Paddle
     * <p>dựa vào vật thể truyền vào để thay đổi góc chiều bật ra hợp lý</p>
     */
    public void bounceOff(GameObject other) {
        if (other instanceof Paddle) {
            if(angle == 0) {
                angle = new Random().nextInt(20,75);
            }
            dx=speed*Math.cos(angle);
            dy=-speed*Math.sin(angle);
        }
    }

    /**
     * Kiểm Tra va chạm.
     * @param other đối tượng cản
     * @return true or false
     * Phục vụ cho hàm bounceOff ở trên.
     */
    public boolean checkCollision(GameObject other) {
        Rectangle rect = new Rectangle(x, y, width, height);
        Rectangle rectother = new Rectangle(other.getX(), other.getY(), other.getWidth(), other.getHeight());
        return (rect.getBoundsInParent().intersects(rectother.getBoundsInParent())) ;


    }


    @Override
    public void update() {
        move();
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.fillRect(x, y, width, height);
    }
}
