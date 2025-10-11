package engine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;

public class GameManager {
    private GameState currentState;
    private GraphicsContext gc;
    private int width, height;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public GameManager(GraphicsContext gc, int width, int height) {
        this.gc = gc;
        this.width = width;
        this.height = height;
        changeState(new MainMenu(this)); // Bắt đầu ở menu
    }

    public void changeState(GameState newState) {
        this.currentState = newState;
    }
    public void handleInput(KeyEvent e) {
        if (currentState != null)
            currentState.handleInput(e);
    }

    public void updateGame() {
        if (currentState != null)
            currentState.updateGame();
    }

    public void renderer() {
        gc.clearRect(0, 0, width, height);
        if (currentState != null)
            currentState.renderer(gc);
    }
}
