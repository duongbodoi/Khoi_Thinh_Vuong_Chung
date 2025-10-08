package engine;

import brick.BrickLoadMap;

import java.util.ArrayList;

import brick.Brick;
import entity.Ball;
import entity.Paddle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import powerup.PowerUp;

import java.util.List;

public class GameManager {
    private Paddle paddle;
    private Ball ball;
    private List<Brick> bricks;
    private List<PowerUp> powerUps;
    private int score;
    private int lives;
    private String gameState;
    private int screenWidth;
    private int screenHeight;

    public GameManager(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void removebrick(Brick brick) {
        bricks.remove(brick);
    }

    public void startGame() {
        // HIỆP xem cách Chiến tổ chức các hàm, thực hiện khởi tạo các Brick, nhớ lại bài vẽ map khi làm game cũ, xử dụng file text để truyền vào vị trí cx như loại brick
        // cần thiết có thể tạo thêm 1 method đọc danh sách gạch vào list
        score = 0;
        lives = 3;
        gameState = "PLAYING";

        // Tạo Paddle
        int paddleWidth = screenWidth / 8;
        int paddleHeight = screenHeight / 40;
        paddle = new Paddle(
                screenWidth / 2 - paddleWidth / 2,
                screenHeight - 50,
                paddleWidth,
                paddleHeight,
                0, 0, 10
        );

        // Tạo Ball
        ball = new Ball(
                screenWidth / 2,
                screenHeight,
                10, 10, 5, 1, -1
        );

        // Load Bricks
        int brickCols = 10;
        int brickRows = 5;
        int brickWidth = screenWidth / brickCols;
        int brickHeight = 25;

        try {
            bricks = BrickLoadMap.loadBricks("assets/map1.txt", brickWidth, brickHeight);
        } catch (Exception e) {
            System.out.println("Không thể đọc file map, tạo map mặc định");
            bricks = new ArrayList<>();
            for (int row = 0; row < brickRows; row++) {
                for (int col = 0; col < brickCols; col++) {
                    int x = col * brickWidth;
                    int y = row * brickHeight + 60;
                    int hitPoints = (row % 3) + 1;
                    String type = "type" + hitPoints;

                    Brick brick = new Brick(x, y, brickWidth, brickHeight, hitPoints, type);
                    bricks.add(brick);
                }
            }
        }
        powerUps = new ArrayList<>();
        System.out.println("Game khởi tạo xong");
    }

    public void updateGame() {
        paddle.update();
        ball.update();
        //checkCollisions();

        for (Brick brick : bricks) {
            brick.update();
        }
    }

    public void handleInput(KeyEvent e) {
        // Hiệp
        //tuỳ vào input gọi các hàm thay đổi hướng di chuyển của paddle
        switch (e.getCode()) {
            case LEFT:
            case A:
                paddle.moveLeft();
                break;

            case RIGHT:
            case D:
                paddle.moveRight();
                break;

            default:
                paddle.stop();
                break;
        }
    }

    //Dương
    public void checkCollisions() {
        // kiểm tra ball với paddle, bricks
        // xử lý điểm, gạch bị phá
        for (Brick brick : bricks) {
            if (ball.checkCollision(brick)) {
                brick.takeHit();
                ball.bounceOff(brick);
            }
        }
        if(ball.checkCollision(paddle)) {
            ball.bounceOff(paddle);
        }



    }

    /**
     * Ngọc anh
     *
     * @return
     */
    public boolean gameOver() {
        return false;
    }

    /**
     * Vẽ ra toàn bỗ những thằng nói trên.
     * <p>Kiểm tra xem nó có tồn tại nữa ko để in ra</p>
     */
    public void renderer(GraphicsContext gc) {
        for (int i = bricks.size() - 1; i >= 0; i--) {
            Brick brick = bricks.get(i);
            if (brick.isDestroyed()) {
                bricks.remove(brick);
            } else {
                brick.render(gc);
            }
        }
        ball.render(gc);
        paddle.render(gc);
    }

}
