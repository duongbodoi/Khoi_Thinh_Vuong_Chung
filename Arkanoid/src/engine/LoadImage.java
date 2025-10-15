package engine;

import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LoadImage {
    private Image normalBrick;
    private Image strongBrick;
    private Image powerUpBrick;
    private Image unbreakBrick;
    public LoadImage() {
        normalBrick = checkLoadImage("assets/IMAGE/green.png");
        strongBrick = checkLoadImage("assets/IMAGE/red.png");
        powerUpBrick = checkLoadImage("assets/IMAGE/blue.png");
        unbreakBrick = checkLoadImage("assets/IMAGE/black.png");
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

}
