package scenes;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class Level {

    public static int WIDTH = 1366;
    public static int HEIGHT = 768;

    public Pane rootPane;
    public Rectangle healthBar;
    public ImageView health1 = new ImageView(new Image("health.png"));
    public ImageView health2 = new ImageView(new Image("health.png"));
    public ImageView health3 = new ImageView(new Image("health.png"));
    public VBox healthes = new VBox();

    public ImageView bossHitbox;

}
