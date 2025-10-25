package engine.InGamePlay;

import brick.BrickLoadMap;

import brick.Brick;
import brick.StrongBrick;
import engine.*;
import entity.Angle;
import entity.Ball;
import entity.Paddle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
    //boolean is_paused = false;
    private GamePause gamePause;
    //boolean is_victory = false;
    private NextLevel nextLevel;LoadImage loadImage;
    private GameButton startButton;
    private Angle aimAngle;
    public GamePlay(GameManager gameManager) {
        super(gameManager);
        screenHeight=gameManager.getHeight();
        screenWidth=gameManager.getWidth();
        loadImage = new LoadImage();
        gamePause = new GamePause(screenWidth, screenHeight, loadImage);
        nextLevel = new NextLevel(screenWidth, screenHeight);
        aimAngle = new Angle(0,0,150,19,loadImage.getAimArrow());
        startButton = new GameButton(screenWidth / 2 - 100, screenHeight / 2 - 40,
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
                paddle.getX() + paddle.getWidth() / 2 - 7,
                paddle.getY() - 15,
                14, 14, 6, 0.6, -0.8
        );

        try {
            bricks = BrickLoadMap.loadBricks("assets/map1.txt", screenWidth, loadImage);
        } catch (Exception e) {
            System.out.println("Không thể đọc file map, tạo map mặc định: " + e.getMessage());
        }
    }

    public void updateGame() {
        if (!gamePause.Is_pause())
        {
            paddle.update();
            if (ball.Is_begin()) {
                ball.update();
            } else {
                ball.resetBegin(paddle);
                aimAngle.update();
            }
            checkCollisions();
            gameOver();
            checkLevel();
            for (Brick brick : bricks) {
                brick.update();
            }
            // --- SAU khi update paddle, ball, brick xong ---
            List<Brick> newBricks = nextLevel.loadNextLevel(loadImage);
            if (newBricks != null && !newBricks.isEmpty()) {
                bricks = newBricks;
                nextLevel.setContinue(false);
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
                    case SPACE -> {
                        if(nextLevel.isFinished()) nextLevel.setContinue(true);
                        else {
                            ball.setMoveBegin(aimAngle.getAngle());
                            ball.setIs_begin(true);
                        }
                    }
                    case ESCAPE -> {
                        if(gamePause.Is_pause()) gamePause.setIs_pause(false);
                        else gamePause.setIs_pause(true);
                    }
                    case R -> {
                        if (gamePause.Is_pause())
                            gameManager.changeState(new GamePlay(gameManager));
                    }
                    case E-> {
                        if (gamePause.Is_pause())
                            gameManager.changeState(new MainMenu(gameManager));
                    }
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
        if (gamePause.Is_pause()) {
            gamePause.handleMouseMoved(e.getX(), e.getY());
        } else {
            startButton.checkHover(e.getX(), e.getY());
        }
    }

    @Override
    public void handleMouseClicked(MouseEvent e) {
        if (gamePause.Is_pause()) {
            gamePause.handleMouseClicked(
                    e.getX(),
                    e.getY(),
                    () -> gameManager.changeState(new MainMenu(gameManager)), // onE
                    () -> gameManager.changeState(new GamePlay(gameManager)), // onR
                    () -> gamePause.setIs_pause(false));// onEsc
        } else {
            startButton.checkClick(e);
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
    public void checkLevel() {
        if (!nextLevel.isFinished() && bricks.isEmpty()) {
            nextLevel.setFinished(true);
            ball.setIs_begin(false);
            ball.resetBegin(paddle);
            if(nextLevel.getLevel()==5) gameManager.changeState(new GameVictory(gameManager));
        }
    }

    /**
     * Vẽ ra toàn bỗ những thằng nói trên.
     * <p>Kiểm tra xem nó có tồn tại nữa ko để in ra</p>
     */
    public void renderer(GraphicsContext gc) {
        gc.drawImage(loadImage.getBackgroundPlay(), 0, 0, screenWidth, screenHeight);
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
                new Explosion(
                        brick.getX() + brick.getWidth() / 2,
                        brick.getY() + brick.getHeight() / 2,
                        gameManager.getEffectLayer(),
                        loadImage
                );
                bricks.remove(brick);
                score +=10;
            } else {
                brick.render(gc);
            }
        }
        ball.render(gc);
        paddle.render(gc);
        nextLevel.renderer(gc);
        gamePause.rendererPause(gc);
        if (!ball.Is_begin()) {
            startButton.draw(gc);
            aimAngle.render(gc,ball);
        }

    }

}
