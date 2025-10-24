package entity;

import base.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Angle extends GameObject {
    int angle;
    boolean moveR;
    public static final int  SPEED =1;

    public Angle(int x, int y, int width, int height, Image image) {
        super(x, y, width, height, image);
        this.angle = 90;
        this.moveR = true;
    }

    public int getAngle() {
        return angle;
    }

    @Override
    public void update() {
        System.out.println(angle);
        if(moveR) {
            angle -= SPEED;
            if(angle <=20) {
                moveR = false;
            }
        }
        else {
            angle += SPEED;
            if(angle > 160) {
                moveR = true;
            }
        }
    }

    public void render(GraphicsContext gc,Ball ball) {
        gc.save();
        gc.translate(ball.getX() + ball.getWidth()/2, ball.getY() + ball.getHeight()/2);
        gc.rotate(-180 + angle);
        gc.drawImage(image, 0, -height/2, width,height);
        gc.restore();
    }
}
