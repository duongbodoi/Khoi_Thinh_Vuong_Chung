package powerup.Basic;

import entity.Ball;
import entity.Paddle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import powerup.PowerUp;

public class DoubleBall extends PowerUp {
    public DoubleBall(int x, int y, int width, int height, Image image) {
        super(x, y, width, height, 15, "D",image);
    }   

    @Override
    public void applyEffect(Paddle paddle) {
        //đánh giấu đã nhặt double ball
        this.consumed = true;
        this.active = false;

        //tạo thêm bóng thứ 2
        Ball originalBall = paddle.getAnyBall();
        if (originalBall != null) {
            Ball newBall = originalBall.shallowCopy(); //bản sao bóng

            //điều chỉnh bóng mới tại nơi khác tránh trùng nhau
            newBall.nudge(8, 0);

            //cập nhật hướng di chuyển cho bóng mới
            newBall.setVelocity(originalBall.getDx() + 2, originalBall.getDy());

             // cho bóng mới bắt đầu ngay (nếu đang chơi)
            if (originalBall.Is_begin()) {
                newBall.setIs_begin(true);
            }

            //thêm bóng mới vào game thông qua BallProvider của paddle
            paddle.addBall(newBall);
        }
    }

    @Override
    public void removeEffect(Paddle paddle) {

    }

}