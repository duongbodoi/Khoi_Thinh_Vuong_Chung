package entity;

import java.util.List;

public interface BallProvider {    
    List<Ball> getBalls();
    void addBall(Ball b);
    Ball getAnyBall();
}
// quản lý, cung cấp bóng cho double ball