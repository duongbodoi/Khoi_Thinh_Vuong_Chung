package engine;

import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LoadImage {
    private Image normalBrick;
    private Image strongBrick;
    private Image powerUpBrick;
    private Image unbreakBrick;

    private Image backgroundMain;
    private Image backgroundPlay;

    private Image scoreFrame;
    private Image lifeFrame;

    private Image startNormal;
    private Image startHover;

    public LoadImage() {
        normalBrick = checkLoadImage("assets/IMAGE/green.png");
        strongBrick = checkLoadImage("assets/IMAGE/red.png");
        powerUpBrick = checkLoadImage("assets/IMAGE/blue.png");
        unbreakBrick = checkLoadImage("assets/IMAGE/black.png");

        backgroundMain = checkLoadImage("assets/IMAGE/backGround_main.png");
        backgroundPlay = checkLoadImage("assets/IMAGE/backGround_play.png");

        scoreFrame = checkLoadImage("assets/IMAGE/score.png");
        lifeFrame  = checkLoadImage("assets/IMAGE/life.png");

        startNormal = new Image("file:assets/IMAGE/start_normal.png");
        startHover  = new Image("file:assets/IMAGE/start_hover.png");
    }

    private Image checkLoadImage(String imagePath) {
        try {
            return new Image(new FileInputStream(imagePath));
        } catch (FileNotFoundException e) {
            System.err.println("Không thể tải ảnh: " + imagePath);
            System.out.println("Đang tìm ảnh ở: " + getClass().getResource(imagePath));
            return null;
        }
    }


    public Image getNormalBrick() {
        return normalBrick;
    }

    public Image getStrongBrick() {
        return strongBrick;
    }

    public Image getPowerUpBrick() {
        return powerUpBrick;
    }

    public Image getUnbreakBrick() {
        return unbreakBrick;
    }

    public Image getBackgroundMain(){
        return backgroundMain;
    }
    public Image getBackgroundPlay(){
        return backgroundPlay;
    }

    public Image getScoreFrame() {
        return scoreFrame;
    }
    public Image getLifeFrame() {
        return lifeFrame;
    }
    public Image getStartNormal() {
        return startNormal;
    }
    public Image getStartHover() {
        return startHover;
    }

}
