package engine.InGamePlay;

import brick.*;

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
import powerup.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class GamePlay extends GameState implements entity.BallProvider {
    private Paddle paddle;
    private List<Ball> balls;
    private List<Brick> bricks;
    private Brick[][] brickGrid;
    private List<Brick> brickToSpawn;
    private List<PowerUp> powerUps;
    private int score;
    private int lives;
    private String gameState;
    private int screenWidth;
    private int screenHeight;
    private GamePause gamePause;
    private NextLevel nextLevel;
    private int level;
    private GameButton startButton;
    private Angle aimAngle;
    private String curentMap;
    private User currentUser;
    private Map<PowerUp, Long> powerUpStartAt; 
    private int basePaddleWidth;               

    int brickRemoveCount;
    public GamePlay(GameManager gameManager,LoadImage loadImage,LoadSound loadSound,String curentMap,User currentUser,int level) {
        super(gameManager,loadImage,loadSound);
        this.curentMap = curentMap;
        this.currentUser=currentUser;
        screenHeight=gameManager.getHeight();
        screenWidth=gameManager.getWidth();
        this.level=level;
        gamePause = new GamePause(screenWidth, screenHeight, loadImage);
        nextLevel = new NextLevel(screenWidth, screenHeight,level);
        aimAngle = new Angle(0,0,150,19,loadImage.getAimArrow());
        brickToSpawn = new ArrayList<>();
        //khởi tạo danh sách power up và thời gian hiệu lực
        powerUps = new ArrayList<>();
        powerUpStartAt = new HashMap<>();

        startButton = new GameButton(screenWidth / 2 - 100, screenHeight / 2 - 40,
                                    80, 80, loadImage.getPlayNormal(), loadImage.getPlayHover());

        startGame();

         // khi nhấn Start thì tất cả bóng bắt đầu di chuyển
        startButton.setOnClick(() -> {
            for (Ball b : balls) {
                b.setIs_begin(true);
            }
        });
    }
    public void startGame() {
        // HIỆP xem cách Chiến tổ chức các hàm, thực hiện khởi tạo các Brick, nhớ lại bài vẽ map khi làm game cũ, xử dụng file text để truyền vào vị trí cx như loại brick
        // cần thiết có thể tạo thêm 1 method đọc danh sách gạch vào list
        brickRemoveCount =0;
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
                0, 0, 9,
                this
        );

        basePaddleWidth = paddleWidth;
        // Tạo Ball
        balls = new ArrayList<>();
        Ball firstBall = new Ball(
                paddle.getX() + paddle.getWidth() / 2 - 7,
                paddle.getY() - 15,
                20, 20, 6, 0.6, -0.8,
                loadImage.getBall()
        );
        balls.add(firstBall);

        try {
            bricks = BrickLoadMap.loadBricks(curentMap, screenWidth, loadImage);
            brickGrid = BrickLoadMap.getGrid();
            if(bricks!=null){
                System.out.println("ok");
            }
        } catch (Exception e) {
            System.out.println("Không thể đọc file map" + e.getMessage());
        }
    }

    public void updateGame() {
        if (!gamePause.Is_pause() ) {
            paddle.update();

            //update bong
            List<Ball> dead = new ArrayList<>();
            for (Ball b : balls) {
                if (b.Is_begin()) {
                    b.update();
                } else {
                    b.resetBegin(paddle);
                    aimAngle.update();
                }
                if (b.is_dead()) {
                    dead.add(b);
                }
            }

            //xu li bong roi
            if (!dead.isEmpty()) {
                balls.removeAll(dead);
                if (balls.isEmpty()) {
                    lives--;
                    //tạo bóng mới gắn vào paddle, chờ SPACE
                    Ball nb = new Ball(
                            paddle.getX() + paddle.getWidth()/2 - 7,
                            paddle.getY() - 15,
                            20, 20, 6, 0.6, -0.8,
                            loadImage.getBall()
                    );
                    nb.setIs_begin(false);
                    balls.add(nb);
                }
            }

            checkCollisions();
            gameOver();
            checkLevel();

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

            for (Brick brick : bricks) {
                brick.update();
            }
            // --- SAU khi update paddle, ball, brick xong ---
            List<Brick> newBricks = nextLevel.loadNextLevel(loadImage);
            level = nextLevel.getLevel();
            if(currentUser.getCurrentLevel()<nextLevel.getLevel()) currentUser.setCurrentLevel(nextLevel.getLevel());
            if (newBricks != null && !newBricks.isEmpty()) {
                Ball anyball = paddle.getAnyBall();
                anyball.resetBegin(paddle);
                balls.clear();
                if(balls.isEmpty()) balls.add(anyball);


                bricks.clear();
                brickRemoveCount = 0;
                powerUps.clear();
                bricks = newBricks;
                nextLevel.setContinue(false);

                //qua man moi thi xoa effect
                for (PowerUp p : new ArrayList<>(powerUps)) {
                    if (p.isActive()) {
                        p.removeEffect(paddle);
                    }
                }

                powerUps.clear();
                powerUpStartAt.clear();

                paddle.setWidth(basePaddleWidth);
            }
            if(!brickToSpawn.isEmpty()){
                for(Brick brick : brickToSpawn){
                    boolean colision = false;
                    for(Ball b : balls) {
                        if(rectOverlap(b.getX(),b.getY(),b.getWidth(),b.getHeight(),brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight())){
                            colision = true;
                            break;
                        }
                    }
                    if(!colision){
                        bricks.add(brick);
                        brickToSpawn.clear();
                    }
                }

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
                                b.setMoveBegin(aimAngle.getAngle());
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
                            gameManager.changeState(new GamePlay(gameManager,loadImage,loadSound,curentMap,currentUser,level));
                    }
                    case E-> {
                        if (gamePause.Is_pause())
                            gameManager.changeState(new MainMenu(gameManager,loadImage,loadSound,currentUser));
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
        for (Ball b : new ArrayList<>(balls)) {
            if (!b.Is_begin()) {
                continue;
            }
            for (int i = 0;i<bricks.size();i++) {
                if (b.checkCollision(bricks.get(i))) {
                    bricks.get(i).takeHit();
                    b.bounceOff(bricks.get(i));
                    ApplyPowerUp.LoadNewBricks(b,bricks.get(i),bricks.get(i).getGridX(),bricks.get(i).getGridY(),brickGrid,loadImage,bricks,brickToSpawn);
                    System.out.println(brickToSpawn.size());
                }
            }
            if (b.checkCollision(paddle)) {
                b.bounceOff(paddle);
            }

//            if (b.is_dead()) {
//                lives--;
//                System.out.println("Lives: " + lives);
//                b.setIs_begin(false);
//                b.resetBegin(paddle);
//            }
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
                    () -> gameManager.changeState(new MainMenu(gameManager, loadImage,loadSound,currentUser)), // onE
                    () -> gameManager.changeState(new GamePlay(gameManager, loadImage,loadSound,curentMap,currentUser,level)), // onR
                    () -> gamePause.setIs_pause(false));// onEsc
        }
    }

    /**
     * Ngọc anh
     *
     * @return
     */
    public void gameOver() {
        if(lives <= 0) {
            if(score>currentUser.getMaxScore()) {   currentUser.setMaxScore(score);}

            gameManager.changeState(new GameOver(gameManager,loadImage,loadSound,currentUser,curentMap,level));
        }
    }
    public void checkLevel() {
        if (!nextLevel.isFinished() && (bricks.isEmpty() || brickRemoveCount == BrickLoadMap.getBrickCount())) {
            nextLevel.setFinished(true);
            Ball mainBall = getAnyBall(); // Lấy 1 quả bóng (bất kỳ)
            mainBall.resetBegin(paddle);
            balls.clear(); // Xóa tất cả bóng
            mainBall.setIs_begin(false);
            mainBall.resetBegin(paddle);
            if(balls.isEmpty()) balls.add(mainBall);

            powerUps.clear();
            brickRemoveCount =0;
            if(nextLevel.getLevel()==5) gameManager.changeState(new GameVictory(gameManager,loadImage,loadSound));
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
                int px = brick.getX() + brick.getWidth()  / 2 - 12;
                int py = brick.getY() + brick.getHeight() / 2 - 12;

                if(brick instanceof FireBrick) powerUps.add(new FireBall(px,py,24,24,5000,"Fire"));
                if(brick instanceof LeafBrick) powerUps.add(new LeafBall(px,py,24,24,5000,"Leaf"));
                if(brick instanceof SoilBrick) powerUps.add(new SoilBall(px,py,24,24,5000,"Soil"));
                if(brick instanceof IceBrick) powerUps.add(new WaterBall(px,py,24,24,10000,"Ice"));

                // 12% rơi power up tại tâm viên gạch
                if (Math.random() < 0.9) {
                    powerup.PowerUp p = (Math.random() < 0.5)
                            ? new powerup.ExpandPaddle(px, py, 24, 24)
                            : new powerup.DoubleBall(px, py, 24, 24);
                    powerUps.add(p);
                }

                bricks.remove(brick);
                score +=10;
                if(!(brick instanceof UnbreakBrick)) brickRemoveCount ++;
            } else {
                brick.render(gc);
            }
        }
        //render tất cả bóng
        for (Ball b : balls) {
            b.render(gc);
        }
        paddle.render(gc);
        //render power-ups đang rơi
        for (PowerUp p : powerUps) {
            p.render(gc);
        }
        nextLevel.renderer(gc);
        gamePause.rendererPause(gc);
        if (!balls.isEmpty() && !balls.get(0).Is_begin()) {
            gc.drawImage(loadImage.getSpace(), screenWidth/ 2 - 250, screenHeight / 2 - 350, 500, 600);
            if(!nextLevel.isFinished()) {
                Ball targetBall = balls.get(0);
                aimAngle.render(gc, targetBall);
            }
        }
    }

        //kiểm tra va chạm hcn
    private boolean rectOverlap(double x1, double y1, double w1, double h1, double x2, double y2, double w2, double h2) {
        return x1 < x2 + w2 && x1 + w1 > x2 && y1 < y2 + h2 && y1 + h1 > y2;
    }

    @Override
        public List<Ball> getBalls() {
        return balls;
    }

    @Override
    public void addBall(Ball b) {
        if (b != null) balls.add(b);
    }

    @Override
    public entity.Ball getAnyBall() {
        //ưu tiên bóng còn sống
        for (Ball b : balls) {
            if (!b.is_dead()) return b;
        }
        //nếu tất cả chết, trả về 1 quả hoặc null
        return balls.isEmpty() ? null : balls.get(0);
    }

}