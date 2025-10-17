package engine.InGamePlay;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GamePause {
    private boolean is_paused;
    private int screenWidth;
    private int screenHeight;
    public GamePause(int screen_width, int screen_height) {
        is_paused = false;
        this.screenWidth = screen_width;
        this.screenHeight = screen_height;
    }

    public boolean Is_pause() {
        return is_paused;
    }

    public void setIs_pause(boolean is_pause) {
        this.is_paused = is_pause;
    }
    public void rendererPause(GraphicsContext gc) {
        if (is_paused) {
            gc.setFill(Color.LIGHTBLUE);
            gc.fillRect(screenWidth/2-screenWidth/4, screenHeight/2-screenHeight/8, screenWidth/2, screenHeight/4);
            gc.setFill(Color.BLACK);
            gc.fillText("Ấn E để trở về MainMenu",screenWidth/2-screenWidth/4+100, screenHeight/2-screenHeight/8+80);
            gc.fillText("Ấn Esc để tiếp tục",screenWidth/2-screenWidth/4+100, screenHeight/2-screenHeight/8+80+20);
            gc.fillText("Ấn R để trở về Restart",screenWidth/2-screenWidth/4+100, screenHeight/2-screenHeight/8+80+40);
        }
    }
}
