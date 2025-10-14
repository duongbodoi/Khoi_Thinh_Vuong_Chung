package engine;

import brick.BrickLoadMap;

import java.util.ArrayList;

import brick.Brick;
import entity.Ball;
import entity.Paddle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import powerup.PowerUp;

import java.util.List;

public class GamePlay extends GameState {

    private Paddle paddle;
    private Ball ball;
    private List<Brick> bricks;
    private List<PowerUp> powerUps;
    private int score;
    private int lives;
    private String gameState;
    private int screenWidth;
    private int screenHeight;
    public GamePlay(GameManager gameManager) {
        super(gameManager);
        screenHeight=gameManager.getHeight();
        screenWidth=gameManager.getWidth();
        startGame();
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
                0, 0, 9
        );

        // Tạo Ball
        ball = new Ball(
                paddle.getX()+paddle.getWidth()/2-7,
                paddle.getY()-14,
                14, 14, 6, 0.6, -0.8
        );

        // Load Bricks

        try {
            bricks = BrickLoadMap.loadBricks("assets/map2.txt", screenWidth);
        } catch (Exception e) {
            System.out.println("Không thể đọc file map, tạo map mặc định: " + e.getMessage());

//            // Tạo map mặc định
//            bricks = new ArrayList<>();
//            int brickCols = 10;
//            int brickRows = 5;
//            int gap = 5;
//            int brickWidth = (screenWidth - (brickCols + 1) * gap) / brickCols;
//            int brickHeight = 25;
//            int offsetX = gap;
//            int offsetY = 40;
//
//            for (int row = 0; row < brickRows; row++) {
//                for (int col = 0; col < brickCols; col++) {
//                    int hitPoints = (row % 3) + 1;
//                    String type = "type" + hitPoints;
//                    int x = offsetX + col * (brickWidth + gap);
//                    int y = offsetY + row * (brickHeight + gap);
//                    bricks.add(new Brick(x, y, brickWidth, brickHeight, hitPoints, type));
//                }
//            }
        }
        powerUps = new ArrayList<>();
        System.out.println("Game khởi tạo xong");
    }

    public void updateGame() {
        paddle.update();
        if(ball.Is_begin()) {
            ball.update();
        }
        else{
            ball.resetBegin(paddle);
        }
        checkCollisions();
        gameOver();
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
                    case SPACE -> ball.setIs_begin(true);
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
        if(ball.is_dead()) {
            lives--;
            System.out.println("Lives: " + lives);
            ball.setIs_begin(false);
            ball.resetBegin(paddle);
        }


    }

    /**
     * Ngọc anh
     *
     * @return
     */
    public void gameOver() {
        if(lives <= 0) {
            gameManager.changeState(new GameOver(gameManager));
        }
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
        gc.setFill(Color.CHOCOLATE);
        gc.fillText("Score: " + score, 100, 100);
    }

}
