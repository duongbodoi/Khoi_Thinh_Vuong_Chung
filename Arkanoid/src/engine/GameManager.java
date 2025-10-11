package engine;

import brick.BrickLoadMap;

import java.util.ArrayList;

import brick.Brick;
import entity.Ball;
import entity.Paddle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
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
        int paddleWidth = screenWidth / 10;
        int paddleHeight = 10;
        paddle = new Paddle(
                screenWidth / 2 - paddleWidth / 2,
                screenHeight - 20,
                paddleWidth,
                paddleHeight,
                0, 0, 7
        );

        // Tạo Ball
        ball = new Ball(
                screenWidth / 2,
                screenHeight,
                15, 15, 6, 1, -1
        );

        // Load Bricks

        try {
            bricks = BrickLoadMap.loadBricks("assets/map1.txt", screenWidth);
        } catch (Exception e) {
            System.out.println("Không thể đọc file map, tạo map mặc định: " + e.getMessage());
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
        switch (e.getEventType().getName()) {
            case "KEY_PRESSED":
                switch (e.getCode()) {
                    case LEFT -> paddle.moveLeft(true);
                    case RIGHT -> paddle.moveRight(true);
                }
                break;
            case "KEY_RELEASED":
                switch (e.getCode()) {
                    case LEFT -> paddle.moveLeft(false);
                    case RIGHT -> paddle.moveRight(false);
                }
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
