package engine;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

public class LoadSound {
    // Sound effects (ngắn)
    private AudioClip hitBrick;
    private AudioClip explosion;
    private AudioClip paddleHit;
//    private AudioClip powerUp;
    private AudioClip loseLife;
    private AudioClip victory;
//    private AudioClip gameOver;

    // Background music (dài)
    private MediaPlayer bgmMenu;
    private MediaPlayer bgmPlay;
    private MediaPlayer loginPlay;

    public LoadSound() {
        // Hiệu ứng
        hitBrick = checkLoadSound("assets/SOUND/brick.mp3");
        explosion = checkLoadSound("assets/SOUND/explosion.mp3");
        paddleHit = checkLoadSound("assets/SOUND/paddle.mp3");
        victory = checkLoadSound("assets/SOUND/victory.mp3");
//        powerUp = loadClip("assets/SOUND/powerup.wav");
        loseLife = checkLoadSound("assets/SOUND/lose.mp3");
//        gameOver = loadClip("assets/SOUND/game_over.wav");

        bgmMenu = loadMusic("assets/SOUND/menu.mp3");
        bgmPlay = loadMusic("assets/SOUND/play.mp3");
        loginPlay = loadMusic("assets/SOUND/login.mp3");
    }

    private AudioClip checkLoadSound(String soundPath) {
        try {
            return new AudioClip(new File(soundPath).toURI().toString());
        } catch (Exception e) {
            System.err.println("Không thể tải âm thanh: " + soundPath);
            System.out.println("Đang tìm âm thanh ở: " + getClass().getResource(soundPath));
            return null;
        }
    }

//    private AudioClip loadClip(String path) {
//        try {
//            return new AudioClip(new File(path).toURI().toString());
//        } catch (Exception e) {
//            System.err.println("Không thể tải hiệu ứng âm thanh: " + path);
//            return null;
//        }
//    }

    private MediaPlayer loadMusic(String path) {
        try {
            MediaPlayer player = new MediaPlayer(new Media(new File(path).toURI().toString()));
            player.setCycleCount(MediaPlayer.INDEFINITE);
            return player;
        } catch (Exception e) {
            System.err.println("Không thể tải nhạc nền: " + path);
            return null;
        }
    }

    // ===== Getter (dùng để gọi ở bất kỳ đâu) =====
    public AudioClip getHitBrick() { return hitBrick; }
    public AudioClip getExplosion() { return explosion; }
    public AudioClip getPaddleHit() { return paddleHit; }
//    public AudioClip getPowerUp() { return powerUp; }
    public AudioClip getLoseLife() { return loseLife; }
//    public AudioClip getGameOver() { return gameOver; }
    public AudioClip getVictory() {
        return victory;
    }

    public MediaPlayer getBgmMenu() { return bgmMenu; }
    public MediaPlayer getBgmPlay() { return bgmPlay; }
    public MediaPlayer getLoginPlay() {
        return loginPlay;
    }

    // ===== Một số hàm tiện ích =====
    public void playBgm(MediaPlayer bgm) {
        if (bgm != null) bgm.play();
    }

    public void stopBgm(MediaPlayer bgm) {
        if (bgm != null) bgm.stop();
    }

    public void stopAllBgm() {
        if (bgmMenu != null) bgmMenu.stop();
        if (bgmPlay != null) bgmPlay.stop();
    }
}