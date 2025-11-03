package powerup;

import entity.Paddle;

public class ExpandPaddle extends PowerUp {

    private int oldWidth; //độ dài cũ paddle
    private final int expandFactor = 2; //mở rộng 2 lần (hệ số)

    public ExpandPaddle(int x, int y, int width, int height) {
        super(x, y, width, height, 5000, "E"); // thời gian 30s, loại E
    }

    @Override
    public void applyEffect(Paddle paddle) {
//        if (!active) {
//            oldWidth = paddle.getWidth();
//            paddle.setWidth(oldWidth * expandFactor);
//            active  = true;
//            consumed = true;
//        }
        active  = false;
        consumed = true;
        oldWidth = paddle.getWidth();
        paddle.setWidth(oldWidth * expandFactor);
        if(paddle.getWidth() > 3*paddle.getOldWidth())
            paddle.setWidth((int)3*paddle.getOldWidth());
        }

    @Override
    public void removeEffect(Paddle paddle) {

    }

}