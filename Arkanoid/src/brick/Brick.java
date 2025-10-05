package brick;

import base.GameObject;

public class Brick extends GameObject {
    protected int hitPoints;
    protected  String type;

    public Brick(){}
    public Brick(int x, int y, int width, int height, int hitPoints, String type) {
        super(x,y,width,height);
        this.hitPoints = hitPoints;
        this.type = type;
    }

    /**
     * Thực hiện thao tác giảm hitPoints.
     * Thực hiện khi kiểm tra method Checkcolision ở GameManager
     */
    public void takeHit(){

    }

    /**
     * Kiểm tra xem hitPoint đã về 0 hay chưa để set trạng thái huỷ.
     * @return true or false
     */
    public boolean isDestroyed() {

        return true;
    }

    public void update() {

    }

    public void render() {

    }

}
