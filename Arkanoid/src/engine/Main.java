package engine;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 600;
    public void start (Stage primaryStage) {
        //
        Canvas canvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //
        GameManager gameManager = new GameManager(GAME_WIDTH, GAME_HEIGHT);
        gameManager.startGame();

        new AnimationTimer() {

            @Override
            public void handle(long l) {
                gc.clearRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
                gameManager.updateGame();
                gameManager.checkCollisions();
                gameManager.renderer(gc);
            }
        }.start();
        // Khoi tao scene bat su kien
        Scene scene = new Scene(new StackPane(canvas), GAME_WIDTH, GAME_HEIGHT);
        scene.setOnKeyPressed(e->gameManager.handleInput(e));
        scene.setOnKeyReleased(e->gameManager.handleInput(e));
        //
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
