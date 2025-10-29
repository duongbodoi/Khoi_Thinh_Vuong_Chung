package engine.InGamePlay;

import engine.GameButton;
import javafx.scene.input.MouseEvent;
import engine.GameState;
import engine.LoadImage;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GamePause {
    private boolean is_paused;
    private int screenWidth;
    private int screenHeight;
    private LoadImage loadImage;

    private GameButton e1Button;
    private GameButton r1Button;
    private GameButton esc1Button;

    public GamePause(int screen_width, int screen_height, LoadImage loadImage) {
        is_paused = false;
        this.screenWidth = screen_width;
        this.screenHeight = screen_height;
        this.loadImage = loadImage;

        double baseX = screenWidth / 2.0 - 70;
        double baseY = screenHeight / 2.0 - 20;

        e1Button = new GameButton(baseX - 50, baseY + 40, 80, 80,
                loadImage.getE1Normal(), loadImage.getE1Hover());
        r1Button = new GameButton(baseX + 50, baseY + 40, 80, 80,
                loadImage.getR1Normal(), loadImage.getR1Hover());
        esc1Button = new GameButton(baseX + 140, baseY + 40, 80, 80,
                loadImage.getEsc1Normal(), loadImage.getEsc1Hover());
    }

    public boolean Is_pause() {
        return is_paused;
    }

    public void setIs_pause(boolean is_pause) {
        this.is_paused = is_pause;
    }
    public void rendererPause(GraphicsContext gc) {
        if (is_paused) {
                gc.drawImage(
                        loadImage.getBackgroundPause(),
                        screenWidth / 2.0 - screenWidth / 4.0,
                        screenHeight / 2.0 - screenHeight / 4.0,
                        screenWidth / 2.0,
                        screenHeight / 2.0);

            e1Button.draw(gc);
            r1Button.draw(gc);
            esc1Button.draw(gc);
        }
    }

    public void handleMouseMoved(double x, double y) {
        if (is_paused) {
            e1Button.checkHover(x, y);
            r1Button.checkHover(x, y);
            esc1Button.checkHover(x, y);
        }
    }

    public void handleMouseClicked(double x, double y, Runnable onE, Runnable onR, Runnable onEsc) {
        if (is_paused) {
            e1Button.checkHover(x, y);
            r1Button.checkHover(x, y);
            esc1Button.checkHover(x, y);

            if (e1Button.isHovered()) onE.run();
            if (r1Button.isHovered()) onR.run();
            if (esc1Button.isHovered()) onEsc.run();
        }
    }
}
