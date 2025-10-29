package engine.Explosion;

import engine.LoadImage;
import javafx.scene.layout.Pane;

public class StrongExplosion extends Explosion {
    public StrongExplosion(double x, double y, Pane root, LoadImage loader) {
        super(x, y, root, loader.getPowerUpExplosion());
    }
}