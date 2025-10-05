package base;

import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject {
    protected int x , y;
    protected int width, height;

    public GameObject() {};

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
      * @param gc
     */
    public abstract void render(GraphicsContext gc); // vẽ ra
}
