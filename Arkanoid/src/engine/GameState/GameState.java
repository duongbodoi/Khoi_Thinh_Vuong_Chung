package engine.GameState;

import engine.GameManager;
import engine.LoadImage;
import engine.LoadSound;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public abstract class GameState {
    protected GameManager gameManager;
    protected LoadImage loadImage;
    protected LoadSound loadSound;
    public GameState(GameManager gameManager,LoadImage loadImage,LoadSound loadSound) {
        this.gameManager = gameManager;
        this.loadImage = loadImage;
        this.loadSound = loadSound;
    }

    public abstract void handleInput(KeyEvent e);
    public abstract void updateGame();
    public abstract void renderer(GraphicsContext gc);
    public void handleMouseMoved(MouseEvent e) { }
    public void handleMouseClicked(MouseEvent e) { }

}