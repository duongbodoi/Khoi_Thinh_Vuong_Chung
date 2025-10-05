package brick;

import base.GameObject;
import javafx.scene.canvas.GraphicsContext;
// CHIẾN
public class Brick extends GameObject {
    protected int hitPoints;
    protected String type;
    public Brick() {
    }



    public Brick(int x, int y, int width, int height, int hitPoints, String type) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
        this.type = type;
    }

    /**
     * Thực hiện thao tác giảm hitPoints.
     * Thực hiện khi kiểm tra method Checkcolision ở GameManager
     */
    public void takeHit() {

    }

    /**
    * Kiểm tra xem hitPoint đã về 0 hay chưa để set trạng thái huỷ.
     *
     * @return true or false
     */
    public boolean isDestroyed() {
        return false;
    }

    @Override
    public void update() {
        // cập nhật vị trí
    }

    @Override
    public void render(GraphicsContext gc) {
        // dùng bút vẽ gc, vị trí x y để vẽ ra brick
        // tạm thời dùng retangle để vẽ, về sau sẽ đổi sang dùng hình ảnh
    }
}
