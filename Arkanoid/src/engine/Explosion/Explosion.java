package engine.Explosion;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public abstract class Explosion {
    private static final int FRAME_COLS = 3;
    private static final int FRAME_ROWS = 3;
    private static final double FRAME_DURATION = 0.06;

    protected Image explosionSheet;
    protected double scale = 0.5;

    public Explosion(double x, double y, Pane root, Image explosionSheet) {
        this.explosionSheet = explosionSheet;
        if (explosionSheet == null) {
            System.err.println("Ảnh explosionSheet bị null — kiểm tra đường dẫn!");
            return;
        }

        double frameWidth = explosionSheet.getWidth() / FRAME_COLS;
        double frameHeight = explosionSheet.getHeight() / FRAME_ROWS;


        ImageView view = new ImageView(explosionSheet);
        view.setViewport(new Rectangle2D(0, 0, frameWidth, frameHeight));

// Đặt kích thước nhỏ hơn
        view.setFitWidth(frameWidth * scale);
        view.setFitHeight(frameHeight * scale);

// Căn giữa vị trí nổ
        view.setX(x - (frameWidth * scale) / 2);
        view.setY(y - (frameHeight * scale) / 2);

// Thêm vào màn hình
        root.getChildren().add(view);

        Timeline timeline = new Timeline();
        int frameCount = FRAME_COLS * FRAME_ROWS;
        for (int i = 0; i < frameCount; i++) {
            int col = i % FRAME_COLS;
            int row = i / FRAME_COLS;
            Rectangle2D frame = new Rectangle2D(col * frameWidth, row * frameHeight, frameWidth, frameHeight);
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(i * FRAME_DURATION), e -> view.setViewport(frame))
            );
        }

        timeline.setOnFinished(e -> root.getChildren().remove(view));
        timeline.play();

        System.out.println("Explosion started at (" + x + ", " + y + ")");
    }
}