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
    protected double directionX, directionY;
    protected double angle = 0;
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
     * @param directionX
     * @param directionY
     */
    public Ball(int x, int y, int width, int height, int speed, double directionX,double directionY) {
        super(x, y, width, height, directionX*speed, directionY*speed);
        this.speed = speed;
        this.directionX = directionX;
        this.directionY = directionY;
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
            if (varxL < varxR && varxL < varyT && varxL < varyB && dx > 0) {
                dx *= -1;
                x -= (int)varxL;
            } else if (varxR < varxL && varxR < varyT && varxR < varyB && dx < 0) {
                dx *= -1;
                x += (int)varxR;
            } else if (varyT < varyB && varyT < varxL && varyT < varxR && dy > 0) {
                dy *= -1;
                y -= (int)varyT;
            } else if (varyB < varyT && varyB < varxL && varyB < varxR && dy < 0) {
                dy *= -1;
                y += (int)varyB;
            }

        }
        if(other instanceof Paddle) {
            double maxAngle = 90;
            double minAngle = 30;
            if(varyT < varyB && varyT < varxL && varyT < varxR && dy > 0) {
                dy *= -1;
                y -= (int) varyT;
            }
            double midPaddleX = other.getX() + (double)other.getWidth() / 2;
            double xoPerMid = Math.abs(xo - midPaddleX) /( double)(other.getWidth()/2);
            angle = maxAngle - xoPerMid*(maxAngle - minAngle);
            dx/=directionX;
            dy/=directionY;
            directionX = Math.cos(Math.toRadians(angle));
            directionY = Math.sin(Math.toRadians(angle));
            dx*=directionX;
            dy*=directionY;
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
        double nearestX = Math.max(other.getX(),Math.min(other.getX()+other.getWidth(),xo));
        double nearestY = Math.max(other.getY(),Math.min(other.getY()+other.getHeight(),yo));

        double dx = xo - nearestX;
        double dy = yo - nearestY;
        return (dx*dx + dy*dy <= r*r);


    }


    @Override
    public void update() {

        move();
        if(x>= GAME_WIDTH-getWidth()) {
            dx*=-1;
            x=GAME_WIDTH-getWidth();
        }
        if(x<= 0) {
            dx*=-1;
            x=0;
        }
        if(y>GAME_HEIGHT-getHeight()) {
            dy*=-1;
            y=GAME_HEIGHT-getHeight();
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
}
