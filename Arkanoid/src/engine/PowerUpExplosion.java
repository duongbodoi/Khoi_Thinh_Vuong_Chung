package engine;

import javafx.scene.layout.Pane;

public class PowerUpExplosion extends Explosion {
    public PowerUpExplosion(double x, double y, Pane root, LoadImage loader) {
        super(x, y, root, loader.getStrongExplosion());
    }
}