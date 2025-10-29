package entity;
import base.GameObject;
import base.MovableObject;
import brick.Brick;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

import java.util.Random;

import static engine.Main.GAME_HEIGHT;
import static engine.Main.GAME_WIDTH;

// Dương làm
public class Ball extends MovableObject {
    //public static final double PI = Math.PI;
    protected int speed;
    public static final int SPEED = 9;
    protected double directionX, directionY;
    protected double angle = 90;
    boolean angleR= true;
    boolean is_dead = false;
    boolean is_begin =false;
    // Vì là khối vuông nên ta cần chuẩn hoá sang hình tròn để va chạm được mượt mà
    // tâm của hình tròn
    double xo = getX() + (double)getWidth() / 2;
    double yo = getY() + (double) getHeight() / 2;
    double r = (double) getHeight() / 2;

    /**
     * Contructor 1.
     * @param x toa độ x bóng
     * @param y toạ độ y bóng
     * @param width độ rộng
     * @param height độ cao
     * @param speed tốc độ
     * @param directionX goc x
     * @param directionY goc y
     */
    public Ball(int x, int y, int width, int height, int speed, double directionX,double directionY) {
        super(x, y, width, height, directionX*speed, directionY*speed);
        this.speed = speed;
        this.directionX = directionX;
        this.directionY = directionY;
    }
    public boolean is_dead() {
        return is_dead;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    /**
     * Thao tác phản lại khi va chạm vào vật thể.
     * @param other vật thể truyền vào có thể là brick hoặc Paddle
     * <p>dựa vào vật thể truyền vào để thay đổi góc chiều bật ra hợp lý</p>
     */
    public void bounceOff(GameObject other) {
        double varxL = xo + r - other.getX(); // độ lấn trái
        double varxR = (other.getX() + other.getWidth()) - (xo - r); // độ lấn nếu vật ở phải
        double varyT = yo + r - other.getY(); // độ lấn trên
        double varyB = (other.getY() + other.getHeight()) - (yo - r); // độ lấn dưới
        if(other instanceof Brick)
        {
//            double varX = Math.min(varxR, varxL); // từ min sẽ biết được là nó đang là trái hay phải để lấy đúng độ lấn
//            double varY = Math.min(varyT, varyB); // tương tự
            if (varxL < varxR
                    && varxL < varyT
                    && varxL < varyB
                    && dx > 0) {
                dx *= -1;
                x -= (int)varxL;
            } else if (varxR < varxL
                    && varxR < varyT
                    && varxR < varyB
                    && dx < 0) {
                dx *= -1;
                x += (int)varxR;
            } else if (varyT < varyB
                    && varyT < varxL
                    && varyT < varxR
                    && dy > 0) {
                dy *= -1;
                y -= (int)varyT;
            } else if (varyB < varyT
                    && varyB < varxL
                    && varyB < varxR
                    && dy < 0) {
                dy *= -1;
                y += (int)varyB;
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
            if (spd <= 0) spd = this.speed;       // fallback
            if (spd <= 0) spd = 4;                // thêm fallback an toàn

            // thiết lập vận tốc mới theo góc
            dx = side * spd * Math.cos(angleRad);
            dy = -Math.abs(spd * Math.sin(angleRad));  // luôn bật lên trên

            // đảm bảo có vận tốc ngang tối thiểu để khỏi dính tường
            double minAbsDx = 1.0;
            if (Math.abs(dx) < minAbsDx) {
                dx = side * minAbsDx;
                // giữ tổng tốc độ gần spd
                double remain = Math.max(0.5, Math.sqrt(Math.max(0, spd * spd - dx * dx)));
                dy = -Math.abs(remain);
            }
        }       
    }

    /**
     * Kiểm Tra va chạm.
     * @param other đối tượng cản
     * @return true or false
     * Phục vụ cho hàm bounceOff ở trên.
     * <p> Ý tưởng ta sẽ tính khoảng cách ngắn nhất từ đối tượng va chạm đến tâm và kiểm
     * tra xem nếu nó nhỏ hơn bán kính thì là va chạm</p>
     *
     */
    public boolean checkCollision(GameObject other) {
        /* Vì ta cần tìm vị trí gần tâm nhất thì ta sẽ tìm theo
        chiều ngang hoặc dọc
        thay vì tính các điểm ta chỉ cần tìm cái vị trí có toạ đọ x to nhất là đc
        vì khi tính khoảng cách trừ đi sẽ ra khoảng cách ngắn nhất
        và cũng chỉ có 3 vị trí đặc biệt đó làm nằm trong trên/ dưới và trái/phải
        */
        double nearestX = Math.max(other.getX(),
                Math.min(other.getX()+other.getWidth(),xo));
        double nearestY = Math.max(other.getY(),
                Math.min(other.getY()+other.getHeight(),yo));

        double dx = xo - nearestX;
        double dy = yo - nearestY;
        return (dx*dx + dy*dy <= r*r);


    }
    public boolean Is_begin() {
        return is_begin;
    }
    public void setIs_begin(boolean is_begin) {
        this.is_begin = is_begin;

    }
    public void resetBegin (Paddle paddle) {
        x=paddle.getX() + paddle.getWidth()/2 -width/2 ;
        y=paddle.getY() - height -1;
        is_dead = false;
    }
    @Override
    public void update() {

        move();
        if(x >= GAME_WIDTH - getWidth()) {
            dx = -Math.abs(dx);
            x = GAME_WIDTH - getWidth() -1; //lùi 1 pixel vào trong
        }
        if(x <= 0) {
            dx = Math.abs(dx);
            x = 1;
        }
        if(y>=GAME_HEIGHT-getHeight()) {
            is_dead = true;
            dy*=-1;
            y=GAME_HEIGHT-getHeight();

        }
        else {
            is_dead = false;
        }
        if(y<=0) {
            dy*=-1;
            y=0;
        }
        xo = getX() + (double)getWidth() / 2;
        yo = getY() + (double) getHeight() / 2;

    }

    @Override
    public void render(GraphicsContext gc) {
        gc.fillOval(x, y, width, height);


    }

    //Ngọc Anh làm đoạn này để dùng cho double ball
    public int getSpeed() { return speed; }
    public double getDx() { return dx; }
    public double getDy() { return dy; }
    public double getDirX() { return directionX; }
    public double getDirY() { return directionY; }

    public void setVelocity(double ndx, double ndy) {
        this.dx = ndx;
        this.dy = ndy;

        double len = Math.sqrt(ndx * ndx + ndy * ndy);
        if (len > 0) {
            // cập nhật hướng chuẩn hoá
            this.directionX = ndx / len;
            this.directionY = ndy / len;

            // scale về đúng "speed" của bóng
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
        return new Ball(this.x, this.y, this.width, this.height, this.speed, this.directionX, this.directionY);
    }

    //vị trí
    public void nudge(int nx, int ny) {
        this.x += nx;
        this.y += ny;   
    }

    public void setPosition(int nx, int ny) {
        this.x = nx;
        this.y = ny;
    }
}
