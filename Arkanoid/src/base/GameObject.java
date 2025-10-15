package base;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class GameObject {
    protected int x, y;
    protected int width, height;
    protected Image image;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public GameObject() {
    }

    ;

    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void update(); // cập nhật vị trí của đối tượng

    /**
     * Từ bút vẽ gc in ra các đối tượng, tạm thời dùng retangle
     *
     * @param gc
     */
    public void render(GraphicsContext gc) {
        if (image != null) {
            gc.drawImage(image, x, y, width, height);
        } else {
            // fallback: nếu chưa có ảnh thì vẽ khung
            gc.strokeRect(x, y, width, height);
        }
    }// vẽ ra

    public void setImage(String imagePath) {
        this.image = new Image(getClass().getResourceAsStream(imagePath));
    }
}

