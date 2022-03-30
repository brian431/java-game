package scenes;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class Level2 extends Level {

    public Scene scene;
    public Pane pane;

    public Level2() {
        pane = new Pane();
        scene = new Scene(pane, WIDTH, HEIGHT);
    }
}
