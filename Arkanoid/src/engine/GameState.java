package engine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public abstract class GameState {
    protected GameManager gameManager;

    public GameState(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public abstract void handleInput(KeyEvent e);
    public abstract void updateGame();
    public abstract void renderer(GraphicsContext gc);

    public void handleMouseMoved(MouseEvent e) { }
    public void handleMouseClicked(MouseEvent e) { }

}