package engine;

import javafx.scene.layout.Pane;

public class SoilExplosion extends Explosion {
    public SoilExplosion(double x, double y, Pane root, LoadImage loader) {
        super(x, y, root, loader.getSoilExplosion());
    }
}