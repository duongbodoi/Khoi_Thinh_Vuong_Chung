package brick;

import base.GameObject;
import javafx.scene.canvas.GraphicsContext;
import java.io.FileInputStream;
import javafx.scene.image.Image;
import java.io.FileNotFoundException;
import javafx.scene.paint.Color;

import java.util.Objects;

// CHIẾN
public class Brick extends GameObject {
    protected int hitPoints;
    protected boolean destroy;
    protected String type;
    protected Image[] images;
    public Brick(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.destroy = false;
    }
    public Brick(int x, int y, int width, int height, int hitPoints, String type, Image image) {
        super(x, y, width, height, image);
        this.hitPoints = hitPoints;
        this.type = type;
        this.destroy = false;
    }

    public Brick(int x, int y, int width, int height, int hitPoints, String type, Image[] images) {
        super(x, y, width, height);
        this.images = images;
        this.type = type;
        this.destroy = false;
        this.hitPoints = images != null ? images.length : hitPoints;
        if (images != null && images.length > 0) {
            this.image = images[0];
        }
    }
    public Brick() {
    }

    /**
     * Thực hiện thao tác giảm hitPoints.
     * Thực hiện khi kiểm tra method Checkcolision ở GameManager
     */
    public void takeHit() {
        if (hitPoints > 0) {
            hitPoints--;

            if (images != null && hitPoints > 0) {
                int index = Math.min(images.length - hitPoints, images.length - 1);
                this.image = images[index];
            }
        } else {
            destroy = true;
        }
    }

    /**
     * Kiểm tra xem hitPoint đã về 0 hay chưa để set trạng thái huỷ.
     *
     * @return true or false
     */
    public boolean isDestroyed() {
        if( hitPoints <= 0) {
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        if(hitPoints <= 0) {
            destroy = true;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (!destroy) {
            if (image != null) {
                gc.drawImage(image, x, y, width, height);
            } else {
                // fallback — nếu chưa có ảnh, vẽ tạm bằng màu để debug
                gc.setStroke(javafx.scene.paint.Color.GRAY);
                gc.strokeRect(x, y, width, height);
            }
        }
    }
}