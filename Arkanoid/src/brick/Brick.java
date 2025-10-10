package brick;

import base.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

// CHIẾN
public class Brick extends GameObject {
    protected int hitPoints;
    protected boolean destroy;
    protected String type;
    public Brick(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
    public Brick(int x, int y, int width, int height, int hitPoints, String type) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
        this.type = type;
        this.destroy = false;
    }

    /**
     * Thực hiện thao tác giảm hitPoints.
     * Thực hiện khi kiểm tra method Checkcolision ở GameManager
     */
    public void takeHit() {
        hitPoints--;
        if(hitPoints > 0) {
            hitPoints--;
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
        if(hitPoints <= 0 && !isDestroyed()) {
            destroy = true;
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        // dùng bút vẽ gc, vị trí x y để vẽ ra brick
        // tạm thời dùng retangle để vẽ, về sau sẽ đổi sang dùng hình ảnh\
//        if(!destroy) {
//            if (hitPoints >= 100) {
//                gc.setFill(Color.BLACK);
//            } else if (hitPoints == 2) {
//                gc.setFill(Color.RED);
//            } else if (hitPoints == 1) {
//                gc.setFill(Color.GREEN);
//            }
//        }
//
//        gc.fillRect(x, y, width, height);
    }
}
