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

}
