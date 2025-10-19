package engine;

import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LoadImage {
    private Image normalBrick;
    private Image[] strongBrick;
    private Image[] powerUpBrick;
    private Image unbreakBrick;

    private Image backgroundMain;
    private Image backgroundPlay;

    private Image scoreFrame;
    private Image lifeFrame;

    private Image startNormal;
    private Image startHover;

    private Image Menu;
    private Image playNormal;
    private Image playHover;
    private Image escNormal;
    private Image escHover;

    public LoadImage() {

        normalBrick = checkLoadImage("assets/IMAGE/Bgreen1.png");
        strongBrick = new Image[] {
                checkLoadImage("assets/IMAGE/Bred1.png"),
                checkLoadImage("assets/IMAGE/Bred2.png")
        };

        powerUpBrick = new Image[] {
                checkLoadImage("assets/IMAGE/Bblue1.png"),
                checkLoadImage("assets/IMAGE/Bblue2.png"),
                checkLoadImage("assets/IMAGE/Bblue3.png")
        };

        unbreakBrick = checkLoadImage("assets/IMAGE/Bblack.png");

        backgroundMain = checkLoadImage("assets/IMAGE/background_main.png");
        backgroundPlay = checkLoadImage("assets/IMAGE/background_play.png");

        scoreFrame = checkLoadImage("assets/IMAGE/score.png");
        lifeFrame = checkLoadImage("assets/IMAGE/life.png");

        startNormal = checkLoadImage("assets/IMAGE/start_normal.png");
        startHover = checkLoadImage("assets/IMAGE/start_hover.png");

        Menu = checkLoadImage("assets/IMAGE/menu.png");

        playNormal = checkLoadImage("assets/IMAGE/play_normal.png");
        playHover = checkLoadImage("assets/IMAGE/play_hover.png");

        escNormal = checkLoadImage("assets/IMAGE/esc_normal.png");
        escHover = checkLoadImage("assets/IMAGE/esc_hover.png");
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

    public Image[] getStrongBrick() {
        return strongBrick;
    }

    public Image[] getPowerUpBrick() {
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

    public Image getMenu() {
        return Menu;
    }

    public Image getPlayNormal() {
        return playNormal;
    }

    public Image getPlayHover() {
        return playHover;
    }

    public Image getEscNormal() {
        return escNormal;
    }

    public Image getEscHover() {
        return escHover;
    }
}
