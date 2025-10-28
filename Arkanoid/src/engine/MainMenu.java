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
    User CurrentUser;
    public MainMenu(GameManager gm,LoadImage loadImage,User currentUser) {
        super(gm,loadImage);
        this.CurrentUser=currentUser;
        screenHeight=gameManager.getHeight();
        screenWidth=gameManager.getWidth();

        enterButton = new GameButton(screenWidth / 2 - 90, screenHeight / 2,
                                    180, 90, loadImage.getPlayNormal(),
                                    loadImage.getPlayHover());

        escButton = new GameButton(screenWidth / 2 - 100, screenHeight / 2 + 60,
                                    180, 90, loadImage.getEscNormal(),
                                    loadImage.getEscHover());

        enterButton.setOnClick(() -> gameManager.changeState(new SelectMap(gameManager,loadImage,currentUser)));
        escButton.setOnClick(() -> System.exit(0));
    }

    @Override
    public void handleInput(KeyEvent e) {
            switch (e.getCode()) {
                //case ENTER -> gameManager.changeState(new SelectMap(gameManager,loadImage,CurrentUser));
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
        gc.drawImage(loadImage.getBackgroundMain(),0, 0, screenWidth, screenHeight);
        enterButton.draw(gc);
        escButton.draw(gc);
        //gc.fillRect(0, 0, 800, 600);
        gc.drawImage(loadImage.getNameGame(), 50, -80, 700, 400);
        gc.drawImage(loadImage.getMenu(), 150, 220, 500, 150);
    }
}

