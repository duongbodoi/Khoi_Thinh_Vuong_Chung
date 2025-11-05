package powerup;

import base.GameObject;
import entity.Ball;
import entity.Paddle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public abstract class PowerUp extends GameObject {
    protected String type;
    protected int time;
    protected boolean active; // xét đang được kích hoạt
    protected boolean consumed; // xét đã va chạm và kích hoạt
    protected Type powerUpType;
    public PowerUp(int x, int y, int width, int height, int time, String type, Image image) {
        super(x, y, width, height,image);
        this.time = time;
        this.type = type;
        this.active = false; //mặc định chưa kích hoạt
        this.consumed = false; //mặc định chưa va chạm
    }

    public String getType() {
        return type;
    }

    public Type getPowerUpType() {
        return powerUpType;
    }
    public int getTime() {
        return time;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isConsumed() {
        return consumed;
    }

    @Override
    public void update() {
        if (!consumed) {
            this.y += 4; //rơi
        }
    }

    /**
     * vẽ power-up đơn giản (hình tròn)
     * lớp con có thể override để vẽ texture riêng.
     */
    @Override
    public void render(GraphicsContext gc) {
        if (!consumed) {
            super.render(gc);
        }
    }

    public abstract void applyEffect(Paddle paddle) ;

    public abstract void removeEffect(Paddle paddle) ;
}