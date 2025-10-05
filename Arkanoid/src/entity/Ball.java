package entity;

import base.GameObject;
import base.MovableObject;
import javafx.scene.canvas.GraphicsContext;
// Dương làm
public class Ball extends MovableObject {
    protected int speed;
    protected int directionX, directionY;

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

    }

    /**
     * Kiểm Tra va chạm.
     * @param other đối tượng cản
     * @return true or false
     * Phục vụ cho hàm bounceOff ở trên.
     */
    public boolean checkCollision(GameObject other) {
        return false;
    }


    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext gc) {

    }
}
