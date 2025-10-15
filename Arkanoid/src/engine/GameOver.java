package engine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class GameOver extends GameState{
    public GameOver(GameManager gameManager) {
        super(gameManager);
    }

    @Override
    public void handleInput(KeyEvent e) {
        switch (e.getCode()) {
            case E: gameManager.changeState(new MainMenu(gameManager)); break;
            case X: System.exit(19);
            case R : gameManager.changeState(new GamePlay(gameManager)); break;
        }
    }

    @Override
    public void updateGame() {

    }

    @Override
    public void renderer(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 800, 600);
        gc.setFill(Color.BLACK);
        gc.fillText("Game Over", 350, 250);
        gc.fillText("Press E to back to Mainmenu", 330, 280);
        gc.fillText("Press X to quit", 340, 310);
        gc.fillText("Press R to restart", 340, 340);

    }


}
