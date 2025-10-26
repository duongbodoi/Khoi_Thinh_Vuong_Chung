package engine;

import engine.InGamePlay.GamePlay;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class GameVictory extends GameState {
    GameButton EButton;
    GameButton XButton;
    GameButton RButton;
    LoadImage loadImage;
    private int screenWidth;
    private int screenHeight;


    public GameVictory(GameManager gameManager) {
        super(gameManager);

        loadImage = new LoadImage();

        screenWidth = gameManager.getWidth();
        screenHeight = gameManager.getHeight();

        EButton = new GameButton(
                screenWidth / 2.0 - screenWidth * 0.25 - screenWidth * 0.05,
                screenHeight * 0.2,
                screenWidth * 0.13,
                screenHeight * 0.1,
                loadImage.getENormal(),
                loadImage.getEHover()
        );

        RButton = new GameButton(
                screenWidth / 2.0 - (screenWidth * 0.1) / 2,
                screenHeight * 0.2,
                screenWidth * 0.13,
                screenHeight * 0.1,
                loadImage.getRNormal(),
                loadImage.getRHover()
        );

        XButton = new GameButton(
                screenWidth / 2.0 + screenWidth * 0.25 + screenWidth * 0.05 - screenWidth * 0.1,
                screenHeight * 0.2,
                screenWidth * 0.13,
                screenHeight * 0.1,
                loadImage.getXNormal(),
                loadImage.getXHover()
        );

        EButton.setOnClick(() -> gameManager.changeState(new MainMenu(gameManager)));
        XButton.setOnClick(() -> System.exit(19));
        RButton.setOnClick(() -> gameManager.changeState(new GamePlay(gameManager)));
    }

    @Override
    public void handleInput(KeyEvent e) {
        switch (e.getCode()) {
            case E:
                gameManager.changeState(new MainMenu(gameManager));
                break;
            case X:
                System.exit(19);
            case R:
                gameManager.changeState(new GamePlay(gameManager));
                break;
        }
    }

    @Override
    public void updateGame() {

    }

    @Override
    public void handleMouseMoved(MouseEvent e) {
        EButton.checkHover(e.getX(), e.getY());
        XButton.checkHover(e.getX(), e.getY());
        RButton.checkHover(e.getX(), e.getY());
    }

    @Override
    public void handleMouseClicked(MouseEvent e) {
        EButton.checkHover(e.getX(), e.getY());
        XButton.checkHover(e.getX(), e.getY());
        RButton.checkHover(e.getX(), e.getY());

        EButton.checkClick(e);
        XButton.checkClick(e);
        RButton.checkClick(e);
    }

    @Override
    public void renderer(GraphicsContext gc) {
        gc.drawImage(loadImage.getBackgroundVictory(), 0, 0, screenWidth, screenHeight);
        EButton.draw(gc);
        XButton.draw(gc);
        RButton.draw(gc);
    }
}
