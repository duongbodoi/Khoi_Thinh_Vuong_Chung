package engine.InGamePlay;

import brick.*;
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
        public static void LoadNewBricks(Ball ball, Brick brick, int posx, int posy, Brick[][] bricks, LoadImage loadImage,List<Brick> bricklist,List<Brick> brickToSpawn) {
            // Lưu ý khi làm nên là đặt brick setdead(), ý là cài là nó đã chết, ko nên remove, vì ở ngoài đã thực hiện remove khi dead r)
            // Bằng posx posy chính là vị trí i,j trong mảng 2 chiều ấy
            // vi 2 mang List<Brick> va Brick[][] cung chua tham chieu den doi tuong cua cac brick nen doi 1 se la doi ca 2
            // bóng lửa đốt hết bóng lá
            if(ball.getElemental() == Elemental.FIRE && (brick instanceof LeafBrick)) {
                RemoveLeaf(posx,posy, bricks);
            }
            // bóng lá làm nổ bóng lửa( nổ xung quanh 1 ô )
            if(ball.getElemental() == Elemental.LEAF && (brick instanceof FireBrick)) {
                RemoveNear(posx,posy, bricks);
            }
            // thêm gạch lá khi bóng nước chạm gạch lá
            if(ball.getElemental() == Elemental.WATER && (brick instanceof LeafBrick)) {
                SpawnLeaf(brick,posx,posy, bricks,brickToSpawn,loadImage);
            }

        }

        public static void RemoveLeaf(int posx,int posy, Brick[][] bricks) {

        }
        public static void RemoveNear(int posx,int posy, Brick[][] bricks){

        }
        public static void SpawnLeaf(Brick originalBrick,int posx,int posy, Brick[][] bricks, List<Brick> brickToSpawn, LoadImage loadImage) {
            // Kiểm tra 4 ô xung quanh: trên, dưới, trái, phải
            originalBrick.setHitPoints(1);
            int[] dx = {0, 0, 1, -1};
            int[] dy = {1, -1, 0, 0};

            int numRows = BrickLoadMap.getGridRows();
            int numCols = BrickLoadMap.getGridCols();

            // Lấy thông số của gạch gốc để tạo gạch mới
            int brickWidth = originalBrick.getWidth();
            int brickHeight = originalBrick.getHeight();
            int dbrick = 1; // Khoảng cách giữa các viên gạch (lấy từ BrickLoadMap.java)

            for (int i = 0; i < 4; i++) {
                int newGridX = posx + dx[i];
                int newGridY = posy + dy[i];
                if (newGridX >= 0 && newGridX < numCols && newGridY >= 0 && newGridY < numRows) {
                    if (bricks[newGridY][newGridX] == null) {
                        int newPixelX = originalBrick.getX() + (dx[i] * (brickWidth + dbrick));
                        int newPixelY = originalBrick.getY() + (dy[i] * (brickHeight + dbrick));
                        // Tạo viên gạch lá mới
                        Brick newBrick = new LeafBrick(
                                newPixelX, newPixelY,
                                brickWidth, brickHeight,
                                1, // value (loại 1 là LeafBrick)
                                "type1", // type
                                loadImage.getNormalBrick() // ảnh
                        );
                        // Đặt vị trí lưới cho viên gạch mới
                        newBrick.setGridPosition(newGridX, newGridY);
                        bricks[newGridY][newGridX] = newBrick;
                        brickToSpawn.add(newBrick);
                        // Tăng tổng số gạch để tính điều kiện qua màn
                        BrickLoadMap.incrementBrickCount();
                    }
                }
            }
        }
    }


