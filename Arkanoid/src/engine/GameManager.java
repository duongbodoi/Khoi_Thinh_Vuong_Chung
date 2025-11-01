package engine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;

public class GameManager {
    private StackPane root;
    private GameState currentState;
    private Pane effectLayer;
    private GraphicsContext gc;
    private Canvas canvas;
    private int width, height;
    private LoadImage loadImage;
    private UserManager userManager;
    LoadSound loadSound;
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public GameManager(Canvas canvas, int width, int height, LoadImage loadImage,UserManager userManager,LoadSound loadSound) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.width = width;
        this.height = height;
        this.loadImage = loadImage;
        this.userManager = userManager;
        this.loadSound = loadSound;
        effectLayer = new Pane();
        effectLayer.setPickOnBounds(false);

        root = new StackPane();
        root.getChildren().addAll(canvas, effectLayer);

        changeState(new GameLogin(this,loadImage,loadSound,userManager)); // Bắt đầu ở menu
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

    public Pane getEffectLayer() {   // ✅ getter cho layer hiệu ứng
        return effectLayer;
    }

    public StackPane getRoot() {
        return root;
    }

    public void renderer() {
        gc.clearRect(0, 0, width, height);
        if (currentState != null)
            currentState.renderer(gc);
    }

    public void handleMouseMoved(MouseEvent e) {
        if (currentState != null) {
            currentState.handleMouseMoved(e);
        }
    }

    public void handleMouseClicked(MouseEvent e) {
        if (currentState != null) {
            currentState.handleMouseClicked(e);
        }
    }
}