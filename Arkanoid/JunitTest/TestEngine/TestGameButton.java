package TestEngine;
import engine.GameButton;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit test cho lớp GameButton.
 * Kiểm tra tính năng phát hiện hover của chuột.
 */
public class TestGameButton {

    @Test
    public void testCheckHover_Outside() {
        GameButton button = new GameButton(100, 100, 50, 50, null, null);
        button.checkHover(50, 50);
        assertFalse(button.isHovered(), "Chuột ở (50, 50) nằm ngoài vùng nút, nên hovered phải là false");
    }

    @Test
    public void testCheckHover_Inside() {
        GameButton button = new GameButton(100, 100, 50, 50, null, null);
        button.checkHover(110, 110);
        assertTrue(button.isHovered(), "Chuột ở (110, 110) nằm trong vùng nút, nên hovered phải là true");
    }
}