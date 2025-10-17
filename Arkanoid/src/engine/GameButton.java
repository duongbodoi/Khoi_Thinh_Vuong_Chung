package engine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.effect.DropShadow;

public class GameButton {
    private double x, y, width, height;
    private String text;
    private boolean hovered;
    private Image normalImage;  // ảnh mặc định
    private Image hoverImage;
    private Runnable onClick;

    public void setOnClick(Runnable onClick) {
        this.onClick = onClick;
    }

    public boolean isHovered() {
        return hovered;
    }

    public GameButton(String text, double x, double y, double width, double height,
                      Image normalImage, Image hoverImage) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.normalImage = normalImage;
        this.hoverImage = hoverImage;
        this.hovered = false;
    }

    public void draw(GraphicsContext gc) {
        if (hovered && hoverImage != null) {
            gc.drawImage(hoverImage, x, y, width, height);
        } else {
            gc.drawImage(normalImage, x, y, width, height);
        }

    }

    public void checkHover(double mouseX, double mouseY) {
        hovered = mouseX >= x && mouseX <= x + width &&
                mouseY >= y && mouseY <= y + height;
    }

    public void checkClick(MouseEvent e) {
        if (hovered && onClick != null) {
            onClick.run();
        }
    }
}
