package engine.InGamePlay;

import brick.Brick;
import brick.BrickLoadMap;
import engine.*;
import entity.Ball;
import entity.BallProvider;
import entity.Paddle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import powerup.PowerUp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamePlay extends GameState implements entity.BallProvider {
    private Paddle paddle;
    private int basePaddleWidth;

    // private Ball ball; // bỏ
    private List<Ball> balls;                
    private List<Brick> bricks;
    private List<PowerUp> powerUps;    
    private Map<PowerUp, Long> powerUpStartAt;  
    private int score;
    private int lives;
    private String gameState;
    private int screenWidth;
    private int screenHeight;
    //boolean is_paused = false;
    GamePause gamePause;
    //boolean is_victory = false;
    NextLevel nextLevel;
    LoadImage loadImage;
    GameButton startButton;

    public GamePlay(GameManager gameManager) {
        super(gameManager);
        screenHeight=gameManager.getHeight();
        screenWidth=gameManager.getWidth();
        loadImage = new LoadImage();
        powerUps = new ArrayList<>();
        powerUpStartAt = new HashMap<>();

        gamePause = new GamePause(screenWidth, screenHeight);
        nextLevel = new NextLevel(screenWidth, screenHeight);

        //khởi tạo danh sách power up và thời gian hiệu lực
        powerUps = new java.util.ArrayList<>();
        powerUpStartAt = new java.util.HashMap<>();

        startButton = new GameButton(screenWidth / 2 - 100, screenHeight / 2 - 40,
                                    80, 80, loadImage.getStartNormal(), loadImage.getStartHover());

        startGame();
         // khi nhấn Start thì tất cả bóng bắt đầu di chuyển
        startButton.setOnClick(() -> {
            for (entity.Ball b : balls) {
            b.setIs_begin(true);
            }
        });
    }
    public void startGame() {
        // HIỆP xem cách Chiến tổ chức các hàm, thực hiện khởi tạo các Brick, nhớ lại bài vẽ map khi làm game cũ, xử dụng file text để truyền vào vị trí cx như loại brick
        // cần thiết có thể tạo thêm 1 method đọc danh sách gạch vào list

        score = 0;
        lives = 3;
        gameState = "PLAYING";
        // Tạo Paddle
        int paddleWidth = screenWidth / 8;
        basePaddleWidth = paddleWidth;
        int paddleHeight = screenHeight / 40;
        paddle = new Paddle(
                screenWidth / 2 - paddleWidth / 2,
                screenHeight - 50,
                paddleWidth,
                paddleHeight,
                0, 0, 6,
                this               // <-- hợp lệ khi class implements BallProvider
        );

        // Danh sách bóng
        balls = new ArrayList<>();
        Ball firstBall = new Ball(
                paddle.getX() + paddle.getWidth() / 2 - 7,
                paddle.getY() - 15,
                14, 14, 4, 0.6, -0.8
        );
        balls.add(firstBall);


        try {
            bricks = BrickLoadMap.loadBricks("assets/map1.txt", screenWidth, loadImage);
        } catch (Exception e) {
            System.out.println("Không thể đọc file map, tạo map mặc định: " + e.getMessage());
        }
    }

    public void updateGame() {
        if (!gamePause.Is_pause()) {
            paddle.update();

            // update bóng
            java.util.List<Ball> dead = new java.util.ArrayList<>();
            for (Ball b : balls) {
                if (b.Is_begin()) {
                    b.update();
                } else {
                    //dính theo paddle khi chưa bắn
                    b.resetBegin(paddle);
                }
                if (b.is_dead()) {
                    dead.add(b);
                }
            }

            //xử lý bóng rơi
            if (!dead.isEmpty()) {
                balls.removeAll(dead);
                if (balls.isEmpty()) {
                    lives--;
                    //tạo bóng mới gắn vào paddle, chờ SPACE
                    Ball nb = new Ball(
                            paddle.getX() + paddle.getWidth()/2 - 7,
                            paddle.getY() - 15,
                            14, 14, 4, 0.6, -0.8
                    );
                    nb.setIs_begin(false);
                    balls.add(nb);
                }
            }

            //va chạm bóng với gạch/paddle
            checkCollisions();

            //update power ups (rơi)
            for (int i = powerUps.size()-1; i >= 0; i--) {
                PowerUp p = powerUps.get(i);
                if (!p.isConsumed()) {
                    p.update();
                    //va chạm với paddle
                    if (rectOverlap(p.getX(), p.getY(), p.getWidth(), p.getHeight(),
                                    paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight())) {
                        //kích hoạt
                        p.applyEffect(paddle);
                        powerUpStartAt.put(p, System.currentTimeMillis());
                    } else if (p.getY() > screenHeight) {
                        //rơi khỏi màn hình -> bỏ
                        powerUps.remove(i);
                    }
                } else {
                    //đã nhặt: nếu có time > 0 thì duy trì đến hết hạn; nếu time <= 0 thì bỏ
                    if (p.getTime() <= 0) {
                        powerUps.remove(i);
                    }
                }
            }

            // hết hạn power up dạng thời gian (ExpandPaddle)
            long now = System.currentTimeMillis();
            for (int i = powerUps.size()-1; i >= 0; i--) {
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

            //update trạng thái gạch
            for (Brick brick : bricks) {
                brick.update();
            }

            //chuyển level nếu cần
            java.util.List<Brick> newBricks = nextLevel.loadNextLevel(loadImage);
            if (newBricks != null && !newBricks.isEmpty()) {
                bricks = newBricks;
                nextLevel.setContinue(false);

                for (PowerUp p : new ArrayList<>(powerUps)) {
                    if (p.isActive()) {
                        p.removeEffect(paddle);
                    }
                }

                powerUps.clear();
                powerUpStartAt.clear();

                paddle.setWidth(basePaddleWidth);
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
                            for (Ball b : balls) {
                                b.setIs_begin(true);
                            }
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
        for (Ball b : new java.util.ArrayList<>(balls)) {
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

    @Override
    public void handleMouseMoved(MouseEvent e) {
        startButton.checkHover(e.getX(), e.getY());
    }

    @Override
    public void handleMouseClicked(MouseEvent e) {
        startButton.checkHover(e.getX(), e.getY());
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
    public void checkLevel() {
    if (!nextLevel.isFinished() && bricks.isEmpty()) {
        nextLevel.setFinished(true);
        //đưa tất cả bóng vào paddle và dừng lại
        for (Ball b : balls) {
            b.setIs_begin(false);
            b.resetBegin(paddle);
        }
        if (nextLevel.getLevel() == 5) {
            gameManager.changeState(new GameVictory(gameManager));
        }
    }
}

    /**
     * Vẽ ra toàn bỗ những thằng nói trên.
     * <p>Kiểm tra xem nó có tồn tại nữa ko để in ra</p>
     */
    public void renderer(GraphicsContext gc) {

        gc.drawImage(loadImage.getBackgroundPlay(), 0, 0, screenWidth, screenHeight);
        gc.drawImage(loadImage.getScoreFrame(), 10, 0, 60, 60);
        gc.drawImage(loadImage.getLifeFrame(), screenWidth - 100, 0, 60, 60);

        gc.setFont(Font.font("Impact", FontWeight.BOLD, 28));
        gc.setFill(Color.web("#FFD700"));
        gc.fillText("" + score, 80, 40);

        gc.setFill(Color.web("#FF4040"));
        gc.fillText("" + lives, screenWidth - 30, 40);

        //render bricks và tạo power up khi brick vỡ
        for (int i = bricks.size() - 1; i >= 0; i--) {
            Brick brick = bricks.get(i);
            if (brick.isDestroyed()) {
                // hiệu ứng nổ
                new Explosion(
                        brick.getX() + brick.getWidth() / 2,
                        brick.getY() + brick.getHeight() / 2,
                        gameManager.getEffectLayer(),
                        loadImage
                );

                //spawn power up (ngẫu nhiên hoặc theo loại)
                boolean drop = (brick instanceof brick.PowerupBrick) || (Math.random() < 0.1);
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

        //render tất cả bóng
        for (Ball b : balls) {
            b.render(gc);
        }
        //render paddle
        paddle.render(gc);

        //render power-ups đang rơi
        for (PowerUp p : powerUps) {
            p.render(gc);
        }

        //render overlay khác 
        nextLevel.renderer(gc);
        gamePause.rendererPause(gc);

        if (!balls.isEmpty() && !balls.get(0).Is_begin()) {
            startButton.draw(gc);
        }

    }

    //kiểm tra va chạm hcn
    private boolean rectOverlap(double x1, double y1, double w1, double h1, double x2, double y2, double w2, double h2) {
        return x1 < x2 + w2 && x1 + w1 > x2 && y1 < y2 + h2 && y1 + h1 > y2;
    }

    @Override
        public java.util.List<entity.Ball> getBalls() {
        return balls;
    }

    @Override
    public void addBall(entity.Ball b) {
        if (b != null) balls.add(b);
    }

    @Override
    public entity.Ball getAnyBall() {
        //ưu tiên bóng còn sống
        for (entity.Ball b : balls) {
            if (!b.is_dead()) return b;
        }
        //nếu tất cả chết, trả về 1 quả hoặc null
        return balls.isEmpty() ? null : balls.get(0);
    }

}