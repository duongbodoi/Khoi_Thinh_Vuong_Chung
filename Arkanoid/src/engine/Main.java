package engine;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {

    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 800;

    @Override
    public void start(Stage primaryStage) {
        // Tạo canvas
        Canvas canvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //
        UserManager userManager = new UserManager();
        userManager.LoadUsers();
        List<User> users = userManager.getUsers();
        LoadImage loadImage = new LoadImage();
        // Tạo GameManager
        GameManager gameManager = new GameManager(canvas, GAME_WIDTH, GAME_HEIGHT,loadImage,userManager);

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
        scene.setOnKeyPressed(e -> gameManager.handleInput(e));
        scene.setOnKeyReleased(e -> gameManager.handleInput(e));
        scene.setOnMouseMoved(e -> gameManager.handleMouseMoved(e));
        scene.setOnMouseClicked(e -> gameManager.handleMouseClicked(e));

        scene.setOnMouseMoved(e -> {
            System.out.println("Mouse at: (" + (int)e.getX() + ", " + (int)e.getY() + ")");
        });

        // Gán scene cho stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Brick Breaker");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}