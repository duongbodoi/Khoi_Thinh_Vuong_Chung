package engine;

import engine.InGamePlay.GamePlay;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public class MainMenu extends GameState {
    private int screenWidth;
    private int screenHeight;
    GameButton enterButton;
    GameButton escButton;
    LoadImage loadImage;

    public MainMenu(GameManager gm) {
        super(gm);

        screenHeight=gameManager.getHeight();
        screenWidth=gameManager.getWidth();
        loadImage = new LoadImage();

        enterButton = new GameButton(screenWidth / 2 - 90, screenHeight / 2 - 140,
                                    180, 90, loadImage.getPlayNormal(),
                                    loadImage.getPlayHover());

        escButton = new GameButton(screenWidth / 2 - 100, screenHeight / 2 - 70,
                                    180, 90, loadImage.getEscNormal(),
                                    loadImage.getEscHover());

        enterButton.setOnClick(() -> gameManager.changeState(new GamePlay(gameManager)));
        escButton.setOnClick(() -> System.exit(0));
    }

    @Override
    public void handleInput(KeyEvent e) {
            switch (e.getCode()) {
                case ENTER -> gameManager.changeState(new GamePlay(gameManager));
                case ESCAPE -> System.exit(3);
            }
    }

    @Override
    public void updateGame() {}

    @Override
    public void handleMouseMoved(MouseEvent e) {
        enterButton.checkHover(e.getX(), e.getY());
        escButton.checkHover(e.getX(), e.getY());
    }

    @Override
    public void handleMouseClicked(MouseEvent e) {
        enterButton.checkHover(e.getX(), e.getY());
        escButton.checkHover(e.getX(), e.getY());
        escButton.checkClick(e);
        enterButton.checkClick(e);
    }

    @Override
    public void renderer(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.drawImage(loadImage.getBackgroundMain(),0, 0, screenWidth, screenHeight);
        enterButton.draw(gc);
        escButton.draw(gc);
        //gc.fillRect(0, 0, 800, 600);
        gc.setFill(Color.BLACK);
        gc.drawImage(loadImage.getMenu(), 150, -10, 500, 200);
    }
}

