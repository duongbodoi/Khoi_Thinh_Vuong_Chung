//
//import brick.Brick;
//import brick.NormalBrick;
//import engine.LoadImage;
//import engine.LoadSound;
//import entity.Ball;
//import entity.Paddle;
//import javafx.embed.swing.JFXPanel;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.testng.Assert.assertFalse;
//import static org.testng.Assert.assertTrue;
//
//
///**
// * Unit test cho lớp GameButton.
// * Kiểm tra tính năng phát hiện hover của chuột.
// */
//public class TestBall {
//    private Ball ball;
//    private Paddle paddle;
//    private Brick brick;
//    LoadImage loadImage;
//    LoadSound loadSound;
//    @BeforeEach
//    void setUp() {
//        new JFXPanel();
//        loadImage = new LoadImage();
//        loadSound= new LoadSound();
//        ball = new Ball(100, 100, 10, 10, 9, 1, -1, loadImage.getBall());
//        paddle = new Paddle(90, 200, 60, 10,0,0,10);
//        brick = new NormalBrick(100, 80, 40, 10,1,"",loadImage.getNormalBrick(),loadImage);
//    }
//
//
//    @Test
//    void testNoCollisionWhenFar() {
//        ball.setX(500);
//        ball.setY(500);
//        assertFalse(ball.checkCollision(brick),
//                "Ball không nên va chạm khi ở xa");
//    }
//
//    @Test
//    void testBounceOffBrickHorizontal() {
//        ball.setX(117);
//        ball.setY(85);
//        double oldDx = ball.getDx();
//        ball.bounceOff(brick);
//
//        assertEquals(-oldDx, ball.getDx(),
//                "Ball phải đổi hướng khi đập vào brick ngang");
//    }
//
//    @Test
//    void testBounceOffPaddle() {
//        // Đặt bóng lên paddle để va chạm
//        ball.setX(110);
//        ball.setY(200);
//        ball.setDx(5); // Bóng đang rơi xuống
//
//        ball.bounceOff(paddle);
//        assertTrue(ball.getDy() < 0,
//                "Ball phải nảy ngược lên khi chạm paddle");
//    }
//
//    @Test
//    void testUpdateHitsWall() {
//        // Đặt bóng gần mép phải màn hình
//        ball.setX(800);
//        ball.setY(100);
//        ball.setDx(5);
//        ball.update();
//
//        assertTrue(ball.getDx() < 0, "Ball phải bật ngược lại khi chạm tường phải");
//    }
//
//    @Test
//    void testResetBegin() {
//        paddle.setX(100);
//        paddle.setY(201);
//        ball.resetBegin(paddle);
//
//        assertEquals(paddle.getY() - ball.getHeight() - 1, ball.getY());
//        assertFalse(ball.is_dead());
//    }
//
//
//}