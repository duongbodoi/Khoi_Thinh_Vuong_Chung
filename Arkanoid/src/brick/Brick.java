package brick;

import base.GameObject;
import engine.LoadImage;
import engine.ExplosionControl.NormalExplosion;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javafx.scene.layout.Pane;

// CHIẾN
public abstract class Brick extends GameObject {
    protected int hitPoints;
    protected boolean destroy;
    protected String type;
    protected Image[] images;
    protected Pane root;
    protected LoadImage loader;
    private int gridX;
    private int gridY;
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
    public void setGridPosition(int gridX, int gridY) {
        this.gridX = gridX;
        this.gridY = gridY;
    }

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public void createExplosion(Pane root, LoadImage loader) {
        new NormalExplosion(x + width / 2.0, y + height / 2.0, root, loader);
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
    public void setDestroyed() {
        destroy = true;
    }
}