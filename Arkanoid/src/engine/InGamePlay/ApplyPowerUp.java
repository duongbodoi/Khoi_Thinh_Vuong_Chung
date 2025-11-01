package engine.InGamePlay;

import brick.Brick;
import brick.NormalBrick;
import brick.PowerupBrick;
import engine.LoadImage;
import entity.Ball;
import entity.Elemental;

import java.util.List;

public class ApplyPowerUp {
    /**
     * Thực hiện power up.
     * @param posx vị trí của viên gạch bị va chạm
     * @param posy vị trí viên gạch bị va chạm
     * @param bricks mảng gạch
     * @return mảng mới sau khi áp dụng powerUp
     * <p>Tuỳ vào tương tác nguyên tố sẽ gọi hàm tương ứng
     *  pos chính là vị trí của
     * </>
     */
        public static void LoadNewBricks(Ball ball, Brick brick, int posx, int posy, Brick[][] bricks, LoadImage loadImage,List<Brick> bricklist) {
            // Lưu ý khi làm nên là đặt brick setdead(), ý là cài là nó đã chết, ko nên remove, vì ở ngoài đã thực hiện remove khi dead r)
            // Bằng posx posy chính là vị trí i,j trong mảng 2 chiều ấy
            // vi 2 mang List<Brick> va Brick[][] cung chua tham chieu den doi tuong cua cac brick nen doi 1 se la doi ca 2
            // bóng lửa đốt hết bóng lá
            if(ball.getElemental() == Elemental.FIRE && (brick instanceof NormalBrick)) {
                RemoveLeaf(posx,posy, bricks);
            }
            // bóng lá làm nổ bóng lửa( nổ xung quanh 1 ô )
            if(ball.getElemental() == Elemental.LEAF && (brick instanceof PowerupBrick)) {
                RemoveNear(posx,posy, bricks);
            }
            // thêm gạch lá khi bóng nước chạm gạch lá
            if(ball.getElemental() == Elemental.WATER && (brick instanceof NormalBrick)) {
                SpawnLeaf(posx,posy, bricks);
            }

        }

        public static void RemoveLeaf(int posx,int posy, Brick[][] bricks) {

        }
        public static void RemoveNear(int posx,int posy, Brick[][] bricks){

        }
        public static void SpawnLeaf(int posx,int posy, Brick[][] bricks) {

        }
}
