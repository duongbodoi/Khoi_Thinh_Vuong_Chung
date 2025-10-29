package engine;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {

    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 800;

    @Override
    public void start(Stage primaryStage) {
        // Tạo canvas
        Canvas canvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);

        // User & Asset (từ bản 2)
        UserManager userManager = new UserManager();
        userManager.LoadUsers();
        List<User> users = userManager.getUsers(); // nếu cần dùng ở nơi khác
        LoadImage loadImage = new LoadImage();

        // Tạo GameManager (ưu tiên constructor mở rộng)
        GameManager gameManager = new GameManager(canvas, GAME_WIDTH, GAME_HEIGHT, loadImage, userManager);
        // Nếu project của bạn chỉ có constructor đơn giản, dùng dòng dưới thay cho dòng trên:
        // GameManager gameManager = new GameManager(canvas, GAME_WIDTH, GAME_HEIGHT);

        // Scene chỉ dùng root từ GameManager
        Scene scene = new Scene(gameManager.getRoot(), GAME_WIDTH, GAME_HEIGHT);

        // Animation loop
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                gameManager.updateGame();
                gameManager.renderer();
            }
        }.start();

        // Gán sự kiện bàn phím + chuột
        scene.setOnKeyPressed(gameManager::handleInput);
        scene.setOnKeyReleased(gameManager::handleInput);
        scene.setOnMouseMoved(gameManager::handleMouseMoved);
        scene.setOnMouseClicked(gameManager::handleMouseClicked);

        // Debug vị trí chuột (tuỳ chọn)
        // scene.setOnMouseMoved(e -> System.out.println("Mouse at: (" + (int)e.getX() + ", " + (int)e.getY() + ")"));

        // Gán scene cho stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Brick Breaker");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
