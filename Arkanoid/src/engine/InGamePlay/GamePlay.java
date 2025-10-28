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
    private GamePause gamePause;
    private NextLevel nextLevel;
    private GameButton startButton;
    private Angle aimAngle;
    private String curentMap;
    public GamePlay(GameManager gameManager,LoadImage loadImage,String curentMap) {
        super(gameManager,loadImage);
        this.curentMap = curentMap;
        screenHeight=gameManager.getHeight();
        screenWidth=gameManager.getWidth();

        gamePause = new GamePause(screenWidth, screenHeight, loadImage);
        nextLevel = new NextLevel(screenWidth, screenHeight);
        aimAngle = new Angle(0,0,150,19,loadImage.getAimArrow());

        startGame();
    }
    public void startGame() {
        // HIỆP xem cách Chiến tổ chức các hàm, thực hiện khởi tạo các Brick, nhớ lại bài vẽ map khi làm game cũ, xử dụng file text để truyền vào vị trí cx như loại brick
        // cần thiết có thể tạo thêm 1 method đọc danh sách gạch vào list

        score = 0;
        lives = 3;
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
                20, 20, 6, 0.6, -0.8,
                loadImage.getBall()
        );

        try {
            bricks = BrickLoadMap.loadBricks(curentMap, screenWidth, loadImage);
        } catch (Exception e) {
            System.out.println("Không thể đọc file map" + e.getMessage());
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
                            gameManager.changeState(new GamePlay(gameManager,loadImage,curentMap));
                    }
                    case E-> {
                        if (gamePause.Is_pause())
                            gameManager.changeState(new MainMenu(gameManager,loadImage));
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
            gamePause.handleMouseMoved(e.getX(), e.getY());
    }

    @Override
    public void handleMouseClicked(MouseEvent e) {
        if (gamePause.Is_pause()) {
            gamePause.handleMouseClicked(
                    e.getX(),
                    e.getY(),
                    () -> gameManager.changeState(new MainMenu(gameManager,loadImage)), // onE
                    () -> gameManager.changeState(new GamePlay(gameManager,loadImage,curentMap)), // onR
                    () -> gamePause.setIs_pause(false));// onEsc
    }

    /**
     * Ngọc anh
     *
     * @return
     */
    public void gameOver() {
        if(lives <= 0) {
            gameManager.changeState(new GameOver(gameManager,loadImage));
        }
    }
    public void checkLevel() {
        if (!nextLevel.isFinished() && bricks.isEmpty()) {
            nextLevel.setFinished(true);
            ball.setIs_begin(false);
            ball.resetBegin(paddle);
            if(nextLevel.getLevel()==5) gameManager.changeState(new GameVictory(gameManager,loadImage));
        }
    }

    /**
     * Vẽ ra toàn bỗ những thằng nói trên.
     * <p>Kiểm tra xem nó có tồn tại nữa ko để in ra</p>
     */
    public void renderer(GraphicsContext gc) {
        gc.drawImage(loadImage.getBackgroundPlay(), 0, 0, screenWidth, screenHeight);
        gc.drawImage(loadImage.getScoreFrame(),0, 0,100, 80);
        gc.drawImage(loadImage.getLifeFrame(),screenWidth - 130, 0,100, 80);

        gc.setFont(Font.font("Bradley Hand", FontWeight.BOLD, 28));
        gc.setFill(Color.web("#00AA00"));
        gc.fillText("" + score, 90, 50);

        gc.setFill(Color.web("#8B0000"));
        gc.fillText("" + lives, screenWidth - 40, 50);

        for (int i = bricks.size() - 1; i >= 0; i--) {
            Brick brick = bricks.get(i);
            if (brick.isDestroyed()) {
                brick.createExplosion(gameManager.getEffectLayer(), loadImage);
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
            gc.drawImage(loadImage.getSpace(), screenWidth/ 2 - 250, screenHeight / 2 - 350, 500, 600);
            if(!nextLevel.isFinished()) {
                aimAngle.render(gc, ball);
            }
        }

    }
}
