package engine;

import engine.InGamePlay.GamePlay;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class GameOver extends GameState {
    GameButton EButton;
    GameButton XButton;
    GameButton RButton;
    private int screenWidth;
    private int screenHeight;
    private User currentUser;
    private UserManager userManager = new UserManager();
    private String currentMap;
    public GameOver(GameManager gameManager,LoadImage loadImage, LoadSound loadSound,User currentUser,String currentMap) {
        super(gameManager,loadImage,loadSound);
        this.currentUser=currentUser;
        this.currentMap=currentMap;
        screenWidth = gameManager.getWidth();
        screenHeight = gameManager.getHeight();
        userManager.LoadUsers();
        EButton = new GameButton(
                screenWidth / 2.0 - screenWidth * 0.18 - screenWidth * 0.05,
                screenHeight * 0.1 + 5,
                screenWidth * 0.13,
                screenHeight * 0.11,
                loadImage.getENormal(),
                loadImage.getEHover()
        );

        RButton = new GameButton(
                screenWidth / 2.0 - (screenWidth * 0.1) / 2,
                screenHeight * 0.1,
                screenWidth * 0.13,
                screenHeight * 0.125,
                loadImage.getRNormal(),
                loadImage.getRHover()
        );

        XButton = new GameButton(
                screenWidth / 2.0 + screenWidth * 0.18 + screenWidth * 0.05 - screenWidth * 0.1,
                screenHeight * 0.1 + 10,
                screenWidth * 0.13,
                screenHeight * 0.1,
                loadImage.getXNormal(),
                loadImage.getXHover()
        );

        EButton.setOnClick(() -> gameManager.changeState(new MainMenu(gameManager,loadImage,loadSound,currentUser)));
        XButton.setOnClick(() -> System.exit(19));
        RButton.setOnClick(() -> gameManager.changeState(new GamePlay(gameManager,loadImage,loadSound,currentMap,currentUser)));
    }

    @Override
    public void handleInput(KeyEvent e) {
        switch (e.getCode()) {
            case E:
                gameManager.changeState(new MainMenu(gameManager,loadImage,loadSound,currentUser));
                break;
            case X:
                System.exit(19);
            case R:
                //gameManager.changeState(new GamePlay(gameManager,loadImage));
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
        gc.drawImage(loadImage.getBackgroundOver(), 0, 0, screenWidth, screenHeight);
        EButton.draw(gc);
        XButton.draw(gc);
        RButton.draw(gc);
        //gc.drawImage(loadImage.getBgrlogin(), 275, 30, screenWidth/3, screenHeight/3);
        int y =320;
        for(User user : userManager.getUsers()) {
            gc.fillText(user.toString(),230, y+=50);
        }
    }
}
