package engine;

import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LoadImage {
    private Image normalBrick;
    private Image[] strongBrick;
    private Image[] powerUpBrick;
    private Image[] soilBrick;
    private Image unbreakBrick;

    private Image normalExplosion;
    private Image strongExplosion;
    private Image powerUpExplosion;
    private Image soilExplosion;

    private Image backgroundMain;
    private Image backgroundPlay;
    private Image backgroundOver;
    private Image backgroundVictory;

    private Image scoreFrame;
    private Image lifeFrame;

    private Image startNormal;
    private Image startHover;

    private Image nameGame;
    private Image Menu;
    private Image playNormal;
    private Image playHover;
    private Image escNormal;
    private Image escHover;

    private Image eNormal;
    private Image eHover;
    private Image rNormal;
    private Image rHover;
    private Image xNormal;
    private Image xHover;

    private Image backgroundPause;
    private Image e1Normal;
    private Image e1Hover;
    private Image r1Normal;
    private Image r1Hover;
    private Image esc1Normal;
    private Image esc1Hover;
    private Image pauseImage;

    private  Image space;

    private Image aimArrow;

    private Image[] ball;

    public LoadImage() {
        ball =new Image[] {
                checkLoadImage("assets/IMAGE/iceBall.png"),
                checkLoadImage("assets/IMAGE/fireBall.png")

        };

        normalBrick = checkLoadImage("assets/IMAGE/Bgreen1.jpg");
        strongBrick = new Image[] {
                checkLoadImage("assets/IMAGE/iceB1.png"),
                checkLoadImage("assets/IMAGE/iceB2.png")
        };

        powerUpBrick = new Image[] {
                checkLoadImage("assets/IMAGE/firebirck1.png"),
                checkLoadImage("assets/IMAGE/firebirck2.png"),
                checkLoadImage("assets/IMAGE/firebirck3.png")
        };
        soilBrick = new Image[] {
                checkLoadImage("assets/IMAGE/soidB1.png"),
                checkLoadImage("assets/IMAGE/soidB2.png"),
                checkLoadImage("assets/IMAGE/soidB3.png"),
                checkLoadImage("assets/IMAGE/soidB4.png")
        };
        normalExplosion = checkLoadImage("assets/IMAGE/explosion1.png");
        strongExplosion = checkLoadImage("assets/IMAGE/explosion4.png");
        powerUpExplosion = checkLoadImage("assets/IMAGE/explosion2.png");
        soilExplosion = checkLoadImage("assets/IMAGE/explosion3.png");

        unbreakBrick = checkLoadImage("assets/IMAGE/Bblack.png");

        backgroundMain = checkLoadImage("assets/IMAGE/background_main.png");
        backgroundPlay = checkLoadImage("assets/IMAGE/background_play.png");

        scoreFrame = checkLoadImage("assets/IMAGE/score.png");
        lifeFrame = checkLoadImage("assets/IMAGE/life.png");

        space = checkLoadImage("assets/IMAGE/space.png");

        startNormal = checkLoadImage("assets/IMAGE/start_normal.png");
        startHover = checkLoadImage("assets/IMAGE/start_hover.png");

        Menu = checkLoadImage("assets/IMAGE/menu.png");
        nameGame = checkLoadImage("assets/IMAGE/nameGame.png");

        playNormal = checkLoadImage("assets/IMAGE/play_normal.png");
        playHover = checkLoadImage("assets/IMAGE/play_hover.png");

        escNormal = checkLoadImage("assets/IMAGE/esc_normal.png");
        escHover = checkLoadImage("assets/IMAGE/esc_hover.png");

        //win
        backgroundVictory = checkLoadImage("assets/IMAGE/background_win.png");

        //over
        backgroundOver = checkLoadImage("assets/IMAGE/background_over.png");

        eNormal = checkLoadImage("assets/IMAGE/e_normal.png");
        eHover  = checkLoadImage("assets/IMAGE/e_hover.png");

        rNormal = checkLoadImage("assets/IMAGE/r_normal.png");
        rHover  = checkLoadImage("assets/IMAGE/r_hover.png");

        xNormal = checkLoadImage("assets/IMAGE/x_normal.png");
        xHover  = checkLoadImage("assets/IMAGE/x_hover.png");

        //pause
        e1Normal = checkLoadImage("assets/IMAGE/e1_normal.png");
        e1Hover  = checkLoadImage("assets/IMAGE/e1_hover.png");

        r1Normal = checkLoadImage("assets/IMAGE/r1_normal.png");
        r1Hover  = checkLoadImage("assets/IMAGE/r1_hover.png");

        esc1Normal = checkLoadImage("assets/IMAGE/esc1_normal.png");
        esc1Hover  = checkLoadImage("assets/IMAGE/esc1_hover.png");

        pauseImage = checkLoadImage("assets/IMAGE/pause.png");
        backgroundPause = checkLoadImage("assets/IMAGE/background_pause.png");
        //
        aimArrow = checkLoadImage("assets/IMAGE/aim_arrow.png");
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
    public Image[] getSoilBrick() {
        return  soilBrick;
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
    public Image getBackgroundOver() { return backgroundOver; }
    public Image getBackgroundVictory() {
        return backgroundVictory;
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

    public Image getENormal() { return eNormal; }
    public Image getEHover()  { return eHover; }
    public Image getRNormal() { return rNormal; }
    public Image getRHover()  { return rHover; }
    public Image getXNormal() { return xNormal; }
    public Image getXHover()  { return xHover; }

    public Image getNormalExplosion() {
        return normalExplosion;
    }

    public Image getPowerUpExplosion() {
        return powerUpExplosion;
    }

    public Image getSoilExplosion() {
        return soilExplosion;
    }

    public Image getStrongExplosion() {
        return strongExplosion;
    }

    public Image getE1Normal() {
        return e1Normal;
    }

    public Image getE1Hover() {
        return e1Hover;
    }

    public Image getR1Normal() {
        return r1Normal;
    }

    public Image getR1Hover() {
        return r1Hover;
    }

    public Image getEsc1Normal() {
        return esc1Normal;
    }

    public Image getEsc1Hover() {
        return esc1Hover;
    }

    public Image getPauseImage() {
        return pauseImage;
    }

    public Image getBackgroundPause() {
        return backgroundPause;
    }


    public Image getNameGame() {
        return nameGame;
    }

    public Image getAimArrow() {
        return aimArrow;
    }
    public Image[] getBall() {
        return ball;
    }
    public Image getSpace() {
        return space;
    }
}
