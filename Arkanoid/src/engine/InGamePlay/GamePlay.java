package engine.InGamePlay;

import brick.Brick;
import brick.BrickLoadMap;
import engine.*;
import engine.ExplosionControl.Explosion;
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

import java.util.*;

public class GamePlay extends GameState implements entity.BallProvider {

    // --- Core gameplay ---
    private Paddle paddle;
    private int basePaddleWidth;

    private List<Ball> balls = new ArrayList<>();
    private List<Brick> bricks = new ArrayList<>();
    private List<PowerUp> powerUps = new ArrayList<>();
    private Map<PowerUp, Long> powerUpStartAt = new HashMap<>();

    // --- UI/State ---
    private int score;
    private int lives;
    private String gameState;
    private int screenWidth;
    private int screenHeight;

    private GamePause gamePause;
    private NextLevel nextLevel;
    private LoadImage loadImage;
    private GameButton startButton;

    // --- Aim khi bóng chưa bắn (từ bản 2) ---
    private Angle aimAngle;

    // --- Tuỳ chọn mở rộng (từ bản 2) ---
    private String curentMap;    // có thể null -> dùng default
    private User currentUser;    // có thể null

    // ===== Constructors =====

    // Bản 1: không User, tự tạo LoadImage, map default
    public GamePlay(GameManager gameManager) {
        super(gameManager);
        this.loadImage = new LoadImage();
        this.curentMap = "assets/map1.txt";
        screenHeight = gameManager.getHeight();
        screenWidth  = gameManager.getWidth();

        powerUps = new ArrayList<>();
        powerUpStartAt = new HashMap<>();

        this.gamePause = new GamePause(screenWidth, screenHeight, loadImage);
        nextLevel = new NextLevel(screenWidth, screenHeight);

        startButton = new GameButton(
                screenWidth / 2 - 100,
                screenHeight / 2 - 40,
                80, 80,
                loadImage.getPlayNormal(),   // dùng Play thay cho Start
                loadImage.getPlayHover()
        );

        // Có asset mũi tên thì bật ngắm
        if (loadImage.getAimArrow() != null) {
            aimAngle = new Angle(0, 0, 150, 19, loadImage.getAimArrow());
        }

        startGame();

        // Khi nhấn Start: tất cả bóng bắt đầu di chuyển
        startButton.setOnClick(() -> {
            for (Ball b : balls) b.setIs_begin(true);
            if (!balls.isEmpty() && aimAngle != null) {
                balls.get(0).setMoveBegin(aimAngle.getAngle());
            }
        });
    }

    // Bản 2: truyền sẵn LoadImage + map + user
    public GamePlay(GameManager gameManager, LoadImage loadImage, String curentMap, User currentUser) {
        super(gameManager, loadImage);
        this.loadImage = (loadImage != null) ? loadImage : new LoadImage();
        this.curentMap = (curentMap != null && !curentMap.isEmpty()) ? curentMap : "assets/map1.txt";
        this.currentUser = currentUser;

        screenHeight = gameManager.getHeight();
        screenWidth  = gameManager.getWidth();

        this.gamePause = new GamePause(screenWidth, screenHeight, loadImage);
        nextLevel = new NextLevel(screenWidth, screenHeight);

        startButton = new GameButton(
                screenWidth / 2 - 100,
                screenHeight / 2 - 40,
                80, 80,
                loadImage.getPlayNormal(),
                loadImage.getPlayHover()
        );

        if (loadImage.getAimArrow() != null) {
            aimAngle = new Angle(0, 0, 150, 19, loadImage.getAimArrow());
        }

        startGame();

        startButton.setOnClick(() -> {
            for (Ball b : balls) b.setIs_begin(true);
            if (!balls.isEmpty() && aimAngle != null) {
                balls.get(0).setMoveBegin(aimAngle.getAngle());
            }
        });
    }

    // ===== Game Setup =====
    public void startGame() {
        score = 0;
        lives = 3;
        gameState = "PLAYING";

        // Paddle
        int paddleWidth  = screenWidth / 8;
        basePaddleWidth = paddleWidth;
        int paddleHeight = screenHeight / 40;
        paddle = new Paddle(
                screenWidth / 2 - paddleWidth / 2,
                screenHeight - 50,
                paddleWidth,
                paddleHeight,
                0, 0, 6,
                this               // BallProvider
        );

        // Bóng đầu tiên (đang chờ bắn)
        balls.clear();
        Ball firstBall = new Ball(
                paddle.getX() + paddle.getWidth() / 2 - 7,
                paddle.getY() - 15,
                14, 14, 4, 0.6, -0.8
        );
        firstBall.setIs_begin(false);
        balls.add(firstBall);

        // Tải map
        try {
            bricks = BrickLoadMap.loadBricks(curentMap != null ? curentMap : "assets/map1.txt", screenWidth, loadImage);
        } catch (Exception e) {
            System.out.println("Không thể đọc file map, tạo map mặc định: " + e.getMessage());
            try {
                bricks = BrickLoadMap.loadBricks("assets/map1.txt", screenWidth, loadImage);
            } catch (Exception ignored) {}
        }

        // reset powerups
        powerUps.clear();
        powerUpStartAt.clear();
    }

    // ===== Update Loop =====
    @Override
    public void updateGame() {
        if (gamePause.Is_pause()) return;

        // paddle
        paddle.update();

        // balls
        List<Ball> dead = new ArrayList<>();
        for (Ball b : balls) {
            if (b.Is_begin()) {
                b.update();
            } else {
                // dính theo paddle khi chưa bắn
                b.resetBegin(paddle);
                if (aimAngle != null) aimAngle.update();
            }
            if (b.is_dead()) {
                dead.add(b);
            }
        }

        // xử lý bóng rơi
        if (!dead.isEmpty()) {
            balls.removeAll(dead);
            if (balls.isEmpty()) {
                lives--;
                Ball nb = new Ball(
                        paddle.getX() + paddle.getWidth() / 2 - 7,
                        paddle.getY() - 15,
                        14, 14, 4, 0.6, -0.8
                );
                nb.setIs_begin(false);
                balls.add(nb);
            }
        }

        // va chạm bóng với gạch/paddle
        checkCollisions();

        // update power ups (rơi & kích hoạt)
        for (int i = powerUps.size() - 1; i >= 0; i--) {
            PowerUp p = powerUps.get(i);
            if (!p.isConsumed()) {
                p.update();
                if (rectOverlap(p.getX(), p.getY(), p.getWidth(), p.getHeight(),
                        paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight())) {
                    p.applyEffect(paddle);
                    powerUpStartAt.put(p, System.currentTimeMillis());
                } else if (p.getY() > screenHeight) {
                    powerUps.remove(i);
                }
            } else {
                if (p.getTime() <= 0) {
                    powerUps.remove(i);
                }
            }
        }

        // hết hạn power up dạng thời gian
        long now = System.currentTimeMillis();
        for (int i = powerUps.size() - 1; i >= 0; i--) {
            PowerUp p = powerUps.get(i);
            if (p.isActive() && p.getTime() > 0) {
                Long startAt = powerUpStartAt.get(p);
                if (startAt != null && now - startAt >= p.getTime()) {
                    p.removeEffect(paddle);
                    powerUps.remove(i);
                    powerUpStartAt.remove(p);
                }
            }
        }

        gameOver();
        checkLevel();

        // update bricks
        for (Brick brick : bricks) {
            brick.update();
        }

        // chuyển level nếu cần
        List<Brick> newBricks = nextLevel.loadNextLevel(loadImage);
        if (newBricks != null && !newBricks.isEmpty()) {
            bricks = newBricks;
            nextLevel.setContinue(false);

            // hủy hiệu ứng còn active
            for (PowerUp p : new ArrayList<>(powerUps)) {
                if (p.isActive()) {
                    p.removeEffect(paddle);
                }
            }
            powerUps.clear();
            powerUpStartAt.clear();

            // reset kích thước paddle
            paddle.setWidth(basePaddleWidth);
        }
    }

    // ===== Input =====
    @Override
    public void handleInput(KeyEvent e) {
        switch (e.getEventType().getName()) {
            case "KEY_PRESSED":
                switch (e.getCode()) {
                    case LEFT -> paddle.moveLeft(true);
                    case RIGHT -> paddle.moveRight(true);
                    case SPACE -> {
                        if (nextLevel.isFinished()) nextLevel.setContinue(true);
                        else {
                            for (Ball b : balls) {
                                b.setIs_begin(true);
                            }
                            if (!balls.isEmpty() && aimAngle != null) {
                                balls.get(0).setMoveBegin(aimAngle.getAngle());
                            }
                        }
                    }
                    case ESCAPE -> {
                        if (gamePause.Is_pause()) gamePause.setIs_pause(false);
                        else gamePause.setIs_pause(true);
                    }
                    case R -> {
                        if (gamePause.Is_pause()) {
                            if (currentUser != null)
                                gameManager.changeState(new GamePlay(gameManager, loadImage, curentMap, currentUser));
                            else
                                gameManager.changeState(new GamePlay(gameManager));
                        }
                    }
                    case E -> {
                        if (gamePause.Is_pause()) {
                            if (currentUser != null)
                                gameManager.changeState(new MainMenu(gameManager, loadImage, currentUser));
                            else
                                gameManager.changeState(new MainMenu(gameManager));
                        }
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

    @Override
    public void handleMouseMoved(MouseEvent e) {
        startButton.checkHover(e.getX(), e.getY());
        // cập nhật aim khi đang chờ bắn
        if (!balls.isEmpty() && !balls.get(0).Is_begin() && aimAngle != null) {
            aimAngle.update();
        }
    }

    @Override
    public void handleMouseClicked(MouseEvent e) {
        startButton.checkHover(e.getX(), e.getY());
        startButton.checkClick(e);
    }

    // ===== Collisions =====
    public void checkCollisions() {
        for (Ball b : new ArrayList<>(balls)) {
            for (Brick brick : bricks) {
                if (b.checkCollision(brick)) {
                    brick.takeHit();
                    b.bounceOff(brick);
                }
            }
            if (b.checkCollision(paddle)) {
                b.bounceOff(paddle);
            }
        }
    }

    /**
     * Ngọc Anh
     */
    public void gameOver() {
        if (lives <= 0) {
            gameManager.changeState(new GameOver(gameManager,loadImage, currentUser, curentMap));
        }
    }

    public void checkLevel() {
        if (!nextLevel.isFinished() && bricks.isEmpty()) {
            nextLevel.setFinished(true);
            // đưa tất cả bóng vào paddle và dừng lại
            for (Ball b : balls) {
                b.setIs_begin(false);
                b.resetBegin(paddle);
            }
            if (nextLevel.getLevel() == 5) {
                gameManager.changeState(new GameVictory(gameManager,loadImage));
            }
        }
    }

    // ===== Render =====
    @Override
    public void renderer(GraphicsContext gc) {

        gc.drawImage(loadImage.getBackgroundPlay(), 0, 0, screenWidth, screenHeight);
        gc.drawImage(loadImage.getScoreFrame(), 10, 0, 60, 60);
        gc.drawImage(loadImage.getLifeFrame(), screenWidth - 100, 0, 60, 60);

        gc.setFont(Font.font("Impact", FontWeight.BOLD, 28));
        gc.setFill(Color.web("#FFD700"));
        gc.fillText("" + score, 80, 40);

        gc.setFill(Color.web("#FF4040"));
        gc.fillText("" + lives, screenWidth - 30, 40);

        // render bricks và tạo power up khi brick vỡ
        for (int i = bricks.size() - 1; i >= 0; i--) {
            Brick brick = bricks.get(i);
            if (brick.isDestroyed()) {
                // hiệu ứng nổ
                brick.createExplosion(gameManager.getEffectLayer(), loadImage);

                // spawn power-up
                boolean drop = (brick instanceof brick.PowerupBrick) || (Math.random() < 0.2);
                if (drop) {
                    int px = brick.getX() + brick.getWidth() / 2 - 12;
                    int py = brick.getY() + brick.getHeight() / 2 - 12;

                    powerup.PowerUp p;
                    if (Math.random() < 0.5)
                        p = new powerup.ExpandPaddle(px, py, 24, 24);
                    else
                        p = new powerup.DoubleBall(px, py, 24, 24);

                    powerUps.add(p);
                }

                bricks.remove(brick);
                score += 10;
            } else {
                brick.render(gc);
            }
        }

        // render tất cả bóng
        for (Ball b : balls) {
            b.render(gc);
        }

        // render paddle
        paddle.render(gc);

        // render power-ups đang rơi
        for (PowerUp p : powerUps) {
            p.render(gc);
        }

        // overlay
        nextLevel.renderer(gc);
        gamePause.rendererPause(gc);

        // nếu đang chờ bắn: nút Start + ngắm
        if (!balls.isEmpty() && !balls.get(0).Is_begin()) {
            startButton.draw(gc);
            if (aimAngle != null && !nextLevel.isFinished()) {
                aimAngle.render(gc, balls.get(0));
            }
        }
    }

    // kiểm tra va chạm hcn
    private boolean rectOverlap(double x1, double y1, double w1, double h1, double x2, double y2, double w2, double h2) {
        return x1 < x2 + w2 && x1 + w1 > x2 && y1 < y2 + h2 && y1 + h1 > y2;
    }

    // ===== BallProvider =====
    @Override
    public List<Ball> getBalls() { return balls; }

    @Override
    public void addBall(Ball b) {
        if (b != null) balls.add(b);
    }

    @Override
    public Ball getAnyBall() {
        for (Ball b : balls) {
            if (!b.is_dead()) return b;
        }
        return balls.isEmpty() ? null : balls.get(0);
    }
}
