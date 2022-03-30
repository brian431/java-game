package scenes;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class Level3 extends Level {

    public Scene scene;
    public Pane pane;

    public Level3() {
        pane = new Pane();
        scene = new Scene(pane, WIDTH, HEIGHT);
    }
}
