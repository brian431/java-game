package scenes;

import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import model.Player;
public class Level1 extends Level {
    final int WIDTH = 1366;
    final int Height = 768;
    public Scene scene;
    public Pane rootPane;

    Player player;
    Rectangle mainFloor;



    public Level1() {
        rootPane = new Pane();
        scene = new Scene(rootPane, WIDTH, Height);

        player = new Player();
        setScene();
    }

    void setScene() {
        mainFloor = new Rectangle(0, 700, 1367, 68);
        rootPane.getChildren().add(mainFloor);
        rootPane.getChildren().add(player.hitbox);
    }
}
