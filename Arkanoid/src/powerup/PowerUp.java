package powerup;

import base.GameObject;
import entity.Paddle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PowerUp extends GameObject {
    protected String type;
    protected int time;
    protected boolean active; // xét đang được kích hoạt
    protected boolean consumed; // xét đã va chạm và kích hoạt

    public PowerUp(int x, int y, int width, int height, int time, String type) {
        super(x, y, width, height);
        this.time = time;
        this.type = type;
        this.active = false; //mặc định chưa kích hoạt
        this.consumed = false; //mặc định chưa va chạm
    }

    public String getType() {
        return type;
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
            this.y += 2; //rơi
        }
    }

    /**
     * vẽ power-up đơn giản (hình tròn)
     * lớp con có thể override để vẽ texture riêng.
     */
    @Override
    public void render(GraphicsContext gc) {
        if (!consumed) {
            gc.setFill(Color.LIGHTGREEN);
            gc.fillOval(x, y, width, height);
            gc.setFill(Color.BLACK);
            gc.fillText(type.substring(0, 1).toUpperCase(), x + width / 3, y + height / 1.5);
        }
    }

    public void applyEffect(Paddle paddle) {}

    public void removeEffect(Paddle paddle) {}
}
