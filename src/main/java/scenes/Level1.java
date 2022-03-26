package scenes;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class Level1 extends Level {
    final int WIDTH = 1366;
    final int Height = 768;
    public Scene scene;
    public Pane pane;

    public Level1() {
        pane = new Pane();
        scene = new Scene(pane, WIDTH, Height);
    }
}
