package engine.InGamePlay;

import brick.Brick;
import brick.BrickLoadMap;
import engine.LoadImage;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class NextLevel {
    private boolean isVictory;
    private boolean isFinished;
    private int screenWidth;
    private int screenHeight;
    private boolean isContinue;
<<<<<<< HEAD
=======
    LoadImage loadImage;
>>>>>>> main
    int level;
    public NextLevel(int screen_width, int screen_height) {
        isVictory = false;
        isFinished = false;
<<<<<<< HEAD
=======
        loadImage = new LoadImage();
>>>>>>> main
        this.screenWidth = screen_width;
        this.screenHeight = screen_height;
        level = 1;
        isContinue = false;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public int getLevel() {
        return level;
    }
    public boolean IsVictory() {
        return isVictory;
    }

    public void setVictory(boolean victory) {
        isVictory = victory;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;

    }
    public void setContinue(boolean isContinue) {
        this.isContinue = isContinue;
    }

    public void renderer(GraphicsContext gc) {
        if (isFinished) {
<<<<<<< HEAD
            gc.setFill(Color.BLACK);
            gc.fillText("Press SPACE to continue", screenWidth / 2, screenHeight / 2);
=======
            gc.drawImage(loadImage.getSpace(), screenWidth/ 2 - 250, screenHeight / 2 - 350, 500, 600);
>>>>>>> main
        }
    }
    public List<Brick> loadNextLevel(LoadImage loadImage) {
        if (isFinished && isContinue) {
            level++;
            if(level >= 6) {
                isVictory = true;
            }
            isFinished = false;
            System.out.println("ok");
            return BrickLoadMap.loadBricks("assets/map" + level +".txt", screenWidth, loadImage);


        }
        return null;
    }

}
