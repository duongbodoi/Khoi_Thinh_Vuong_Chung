package base;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Lớp cơ sở cho mọi đối tượng trong game.
 * Cung cấp vị trí, kích thước, hình ảnh và hàm render/update cơ bản.
 */
public abstract class GameObject {
    protected int x, y;
    protected int width, height;
    protected Image image;

    // ===== Getter =====
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    // ===== Constructors =====
    public GameObject() {}

    public GameObject(Image image) {
        this.image = image;
    }

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

    public GameObject(int x, int y, int width, int height, Image image) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = image;
    }

    // ===== Abstract & Core Methods =====
    /** Cập nhật logic đối tượng mỗi frame. */
    public abstract void update();

    /** Vẽ đối tượng lên màn hình. */
    public void render(GraphicsContext gc) {
        if (image != null) {
            gc.drawImage(image, x, y, width, height);
        } else {
            // fallback: nếu chưa có ảnh thì vẽ khung
            gc.strokeRect(x, y, width, height);
        }
    }

    // ===== Utilities =====
    /** Gán hình ảnh cho đối tượng từ đường dẫn nội bộ. */
    public void setImage(String imagePath) {
        this.image = new Image(getClass().getResourceAsStream(imagePath));
    }
}