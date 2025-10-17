package engine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;


public class MainMenu extends GameState {
    private int screenWidth;
    private int screenHeight;
    LoadImage loadImage;

    public MainMenu(GameManager gm) {
        super(gm);

        screenHeight=gameManager.getHeight();
        screenWidth=gameManager.getWidth();
        loadImage = new LoadImage();
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
    public void renderer(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.drawImage(loadImage.getBackgroundMain(),0, 0, screenWidth, screenHeight);
        //gc.fillRect(0, 0, 800, 600);
        gc.setFill(Color.BLACK);
        gc.fillText("MAIN MENU", 350, 250);
        gc.fillText("Press ENTER to Play", 330, 280);
        gc.fillText("Press ESC to Quit", 340, 310);
    }
}

