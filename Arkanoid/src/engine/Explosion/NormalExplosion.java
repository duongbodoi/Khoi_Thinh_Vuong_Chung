package engine.Explosion;

import engine.LoadImage;
import javafx.scene.layout.Pane;

public class NormalExplosion extends Explosion {
    public NormalExplosion(double x, double y, Pane root, LoadImage loader) {
        super(x, y, root, loader.getNormalExplosion());
    }
}
