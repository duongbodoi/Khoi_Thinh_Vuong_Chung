package entity;

import base.GameObject;
import base.MovableObject;
import brick.Brick;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static engine.Main.GAME_HEIGHT;
import static engine.Main.GAME_WIDTH;

// Dương làm
public class Ball extends MovableObject {
    //public static final double PI = Math.PI;
    protected int speed;
    public static final int SPEED = 9;

    protected double directionX, directionY;

    // trạng thái
    boolean is_dead = false;
    boolean is_begin = false;

    // rendering bằng sprite (tùy chọn)
    protected Image[] images;

    // Vì là khối vuông nên ta cần chuẩn hoá sang hình tròn để va chạm được mượt mà
    // tâm của hình tròn
    double xo = getX() + (double) getWidth() / 2;
    double yo = getY() + (double) getHeight() / 2;
    double r  = (double) getHeight() / 2;

    /**
     * Constructor không dùng ảnh.
     */
    public Ball(int x, int y, int width, int height, int speed, double directionX, double directionY) {
        super(x, y, width, height, directionX * speed, directionY * speed);
        this.speed = speed;
        this.directionX = directionX;
        this.directionY = directionY;
        this.images = null;
    }

    /**
     * Constructor có mảng ảnh để render.
     */
    public Ball(int x, int y, int width, int height, int speed, double directionX, double directionY, Image[] images) {
        super(x, y, width, height, directionX * speed, directionY * speed);
        this.speed = speed;
        this.directionX = directionX;
        this.directionY = directionY;
        this.images = images;
    }

    public boolean is_dead() {
        return is_dead;
    }

    public boolean Is_begin() { // giữ nguyên chữ ký cũ
        return is_begin;
    }

    public void setIs_begin(boolean is_begin) {
        this.is_begin = is_begin;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Thao tác phản lại khi va chạm vào vật thể.
     * @param other vật thể truyền vào có thể là Brick hoặc Paddle
     */
    public void bounceOff(GameObject other) {
        double varxL = xo + r - other.getX();                      // độ lấn trái
        double varxR = (other.getX() + other.getWidth()) - (xo - r); // độ lấn phải
        double varyT = yo + r - other.getY();                      // độ lấn trên
        double varyB = (other.getY() + other.getHeight()) - (yo - r); // độ lấn dưới

        if (other instanceof Brick) {
            // chọn hướng có độ lấn nhỏ nhất và đảo vận tốc tương ứng
            if (varxL < varxR && varxL < varyT && varxL < varyB && dx > 0) {
                dx *= -1;
                x -= (int) varxL;
            } else if (varxR < varxL && varxR < varyT && varxR < varyB && dx < 0) {
                dx *= -1;
                x += (int) varxR;
            } else if (varyT < varyB && varyT < varxL && varyT < varxR && dy > 0) {
                dy *= -1;
                y -= (int) varyT;
            } else if (varyB < varyT && varyB < varxL && varyB < varxR && dy < 0) {
                dy *= -1;
                y += (int) varyB;
            }
        }

        if (other instanceof Paddle) {
            double maxAngle = 89;
            double minAngle = 30;

            // đẩy ra khỏi cạnh trên của paddle nếu đang chạm
            if (varyT < varyB && varyT < varxL && varyT < varxR && dy > 0) {
                dy *= -1;
                y -= (int) varyT;
            }

            // tính góc bật dựa theo vị trí va chạm trên mặt paddle
            double midPaddleX = other.getX() + (double) other.getWidth() / 2;
            double xoPerMid = Math.abs(xo - midPaddleX) / (double) (other.getWidth() / 2);
            xoPerMid = Math.max(0.0, Math.min(1.0, xoPerMid));

            double angleDeg = maxAngle - xoPerMid * (maxAngle - minAngle);
            double angleRad = Math.toRadians(angleDeg);

            // hướng trái/phải dựa vào vị trí tương đối
            double side = (xo > midPaddleX) ? +1.0 : -1.0;

            // lấy tốc độ hiện tại (giữ nguyên độ lớn)
            double spd = Math.hypot(dx, dy);
            if (spd <= 0) spd = this.speed;
            if (spd <= 0) spd = 4; // fallback an toàn

            // thiết lập vận tốc mới theo góc
            dx = side * spd * Math.cos(angleRad);
            dy = -Math.abs(spd * Math.sin(angleRad)); // luôn bật lên trên

            // đảm bảo có vận tốc ngang tối thiểu để khỏi dính tường
            double minAbsDx = 1.0;
            if (Math.abs(dx) < minAbsDx) {
                dx = side * minAbsDx;
                double remain = Math.max(0.5, Math.sqrt(Math.max(0, spd * spd - dx * dx)));
                dy = -Math.abs(remain);
            }
        }
    }

    /**
     * Kiểm tra va chạm hình tròn - hình chữ nhật.
     */
    public boolean checkCollision(GameObject other) {
        double nearestX = Math.max(other.getX(), Math.min(other.getX() + other.getWidth(), xo));
        double nearestY = Math.max(other.getY(), Math.min(other.getY() + other.getHeight(), yo));

        double dx = xo - nearestX;
        double dy = yo - nearestY;
        return (dx * dx + dy * dy <= r * r);
    }

    public void resetBegin(Paddle paddle) {
        x = paddle.getX() + paddle.getWidth() / 2 - width / 2;
        y = paddle.getY() - height - 1;
        is_dead = false;
    }

    /**
     * Bắt đầu chuyển động theo góc ngắm (giữ từ phiên bản 1).
     */
    public void setMoveBegin(int aimAngle) {
        double rad = Math.toRadians(180 - aimAngle);
        directionX = Math.cos(rad);
        directionY = -Math.sin(rad);
        dx = directionX * speed;
        dy = directionY * speed;
    }

    @Override
    public void update() {
        move();

        if (x >= GAME_WIDTH - getWidth()) {
            dx = -Math.abs(dx);
            x = GAME_WIDTH - getWidth() - 1; // lùi 1 pixel vào trong
        }
        if (x <= 0) {
            dx = Math.abs(dx);
            x = 1;
        }
        if (y >= GAME_HEIGHT - getHeight()) {
            is_dead = true;
            dy *= -1;
            y = GAME_HEIGHT - getHeight();
        } else {
            is_dead = false;
        }
        if (y <= 0) {
            dy *= -1;
            y = 0;
        }

        // cập nhật tâm hình tròn
        xo = getX() + (double) getWidth() / 2;
        yo = getY() + (double) getHeight() / 2;
        r  = (double) getHeight() / 2;
    }

    @Override
    public void render(GraphicsContext gc) {
        if (images != null && images.length > 1) {
            image = images[1];
            super.render(gc); // MovableObject sẽ vẽ image
        } else {
            gc.fillOval(x, y, width, height);
        }
    }

    // ====== Hỗ trợ double ball / tiện ích ======

    public int getSpeed() { return speed; }
    public double getDx() { return dx; }
    public double getDy() { return dy; }
    public double getDirX() { return directionX; }
    public double getDirY() { return directionY; }

    /**
     * Đặt vận tốc mới, cập nhật hướng chuẩn hoá và scale về đúng "speed".
     */
    public void setVelocity(double ndx, double ndy) {
        this.dx = ndx;
        this.dy = ndy;

        double len = Math.sqrt(ndx * ndx + ndy * ndy);
        if (len > 0) {
            this.directionX = ndx / len;
            this.directionY = ndy / len;

            double target = (this.speed > 0) ? this.speed : len;
            double s = target / len;
            this.dx *= s;
            this.dy *= s;
        }

        // bảo đảm có vận tốc ngang tối thiểu tránh dính tường
        double minAbsDx = 1.0;
        if (Math.abs(this.dx) < minAbsDx) {
            this.dx = (this.dx >= 0 ? +1 : -1) * minAbsDx;
        }
    }

    public Ball shallowCopy() {
        // Sao chép nông, không copy mảng images để an toàn mặc định
        return new Ball(this.x, this.y, this.width, this.height, this.speed, this.directionX, this.directionY);
    }

    // vị trí
    public void nudge(int nx, int ny) {
        this.x += nx;
        this.y += ny;
    }

    public void setPosition(int nx, int ny) {
        this.x = nx;
        this.y = ny;
    }
}
