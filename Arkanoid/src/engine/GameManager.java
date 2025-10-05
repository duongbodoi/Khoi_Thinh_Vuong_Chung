package engine;
import brick.Brick;
import entity.Ball;
import entity.Paddle;
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

    public void startGame() {

    }

    public void updateGame() {

    }

    public void handleInput() {

    }

    public void checkCollisions() {
        // kiểm tra ball với paddle, bricks
        // xử lý điểm, gạch bị phá
    }

    public boolean gameOver() {
        return  false;
    }
}
