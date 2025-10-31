package TestEngine;

import brick.*;
import engine.LoadImage;
import javafx.scene.image.Image;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TestBrickLoadMap {
    // fake LoadImage để không cần file ảnh thật
    static class FakeLoadImage extends LoadImage {

        @Override public Image getNormalBrick() { return null; }
        @Override public Image[] getStrongBrick() { return new Image[]{null, null}; }
        @Override public Image[] getPowerUpBrick() { return new Image[]{null, null, null}; }
        @Override public Image[] getSoilBrick() { return new Image[]{null, null, null, null}; }
        @Override public Image getUnbreakBrick() { return null; }
    }

    @Test
    void testLoadBricks() throws Exception {

        File temp = File.createTempFile("maptest", ".txt");
        temp.deleteOnExit();

        try (FileWriter fw = new FileWriter(temp)) {
            fw.write("1 0 2\n");
            fw.write("3 4 100\n");
        }

        int screenWidth = 600;
        LoadImage fakeImages = new FakeLoadImage();

        List<Brick> bricks = BrickLoadMap.loadBricks(temp.getAbsolutePath(), screenWidth, fakeImages);

        // map 2x3 nhung 1 ô = 0 thi co 5 brick
        assertEquals(5, bricks.size());

        assertTrue(bricks.get(0) instanceof NormalBrick);
        assertTrue(bricks.get(1) instanceof StrongBrick);
        assertTrue(bricks.get(2) instanceof PowerupBrick);
        assertTrue(bricks.get(3) instanceof SoilBrick);
        assertTrue(bricks.get(4) instanceof UnbreakBrick);
    }
}