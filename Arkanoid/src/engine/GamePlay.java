package engine;

import brick.BrickLoadMap;

import java.util.ArrayList;

import brick.Brick;
import entity.Ball;
import entity.Paddle;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import powerup.PowerUp;
import engine.LoadImage;

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
    boolean is_paused = false;

    LoadImage loadImage;
    GameButton startButton;

    public GamePlay(GameManager gameManager) {
        super(gameManager);
        loadImage = new LoadImage();

        screenHeight=gameManager.getHeight();
        screenWidth=gameManager.getWidth();

        startButton = new GameButton("",
                screenWidth / 2 - 100, screenHeight / 2 - 40,
                80, 80, loadImage.getStartNormal(), loadImage.getStartHover());

        startGame();
        startButton.setOnClick(() -> ball.setIs_begin(true));
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
                paddle.getY()-15,
                14, 14, 6, 0.6, -0.8
        );

        try {
            bricks = BrickLoadMap.loadBricks("assets/map1.txt", screenWidth,loadImage);
        } catch (Exception e) {
            System.out.println("Không thể đọc file map, tạo map mặc định: " + e.getMessage());
        }
        powerUps = new ArrayList<>();
        System.out.println("Game khởi tạo xong");
    }

    public void updateGame() {
        if (!is_paused)
        {
            paddle.update();
            if (ball.Is_begin()) {
                ball.update();
            } else {
                ball.resetBegin(paddle);
            }
            checkCollisions();
            gameOver();
            for (Brick brick : bricks) {
                brick.update();
            }
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
                    case ESCAPE -> {
                        if(is_paused) is_paused = false;
                        else is_paused = true;
                    }
                    case R -> gameManager.changeState(new GamePlay(gameManager));
                    case E-> gameManager.changeState(new MainMenu(gameManager));
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

    public void handleMouseMoved(MouseEvent e) {
        double mouseX = e.getX();
        double mouseY = e.getY();
        startButton.checkHover(mouseX, mouseY);
    }

    public void handleMouseClicked(MouseEvent e) {
        double mouseX = e.getX();
        double mouseY = e.getY();
        startButton.checkHover(mouseX, mouseY); // luôn cập nhật hover trước click
        startButton.checkClick(e);
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
        gc.drawImage(loadImage.getBackgroundPlay(), 0, 60, screenWidth, screenHeight);
        gc.drawImage(loadImage.getScoreFrame(),10, 0,60, 60);
        gc.drawImage(loadImage.getLifeFrame(),screenWidth - 100, 0,60, 60);

        gc.setFont(Font.font("Impact", FontWeight.BOLD, 28));
        gc.setFill(Color.web("#FFD700"));
        gc.fillText("" + score, 80, 40);

        gc.setFill(Color.web("#FF4040"));
        gc.fillText("" + lives, screenWidth - 30, 40);

        for (int i = bricks.size() - 1; i >= 0; i--) {
            Brick brick = bricks.get(i);
            if (brick.isDestroyed()) {
                bricks.remove(brick);
                score +=10;
            } else {
                brick.render(gc);
            }
        }
        ball.render(gc);
        paddle.render(gc);
        //gc.setFill(Color.CHOCOLATE);
//        gc.fillText("Score: " + score, 20, 20);
//        gc.fillText("Lives: " + lives, 20+700, 20);
        if (is_paused) {
            gc.setFill(Color.LIGHTBLUE);
            gc.fillRect(screenWidth/2-screenWidth/4, screenHeight/2-screenHeight/8, screenWidth/2, screenHeight/4);
            gc.setFill(Color.BLACK);
            gc.fillText("Ấn E để trở về MainMenu",screenWidth/2-screenWidth/4+100, screenHeight/2-screenHeight/8+80);
            gc.fillText("Ấn Esc để tiếp tục",screenWidth/2-screenWidth/4+100, screenHeight/2-screenHeight/8+80+20);
            gc.fillText("Ấn R để trở về Restart",screenWidth/2-screenWidth/4+100, screenHeight/2-screenHeight/8+80+40);
        }
        if (!ball.Is_begin()) {
            startButton.draw(gc);
        }
    }

}
