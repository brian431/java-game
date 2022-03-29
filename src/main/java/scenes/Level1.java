package scenes;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import model.Level1Boss;
import model.Main;
import model.Player;

public class Level1 extends Level {
    final public static int WIDTH = 1366;
    final public static int Height = 768;
    public Scene scene;
    public Pane rootPane;
    public Label label1 = new Label();


    Player player;
    Level1Boss boss;
    Rectangle mainFloor;


    public Level1() {
        rootPane = new Pane();
        scene = new Scene(rootPane, WIDTH, Height);
        scene.setOnKeyPressed(keyEvent -> Main.KeyCodes.put(keyEvent.getCode(), true));
        scene.setOnKeyReleased(keyEvent -> Main.KeyCodes.put(keyEvent.getCode(), false));

        player = new Player();
        boss = new Level1Boss();
        setScene();

        boss.startMoving();
        AnimationTimer mainTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };
        mainTimer.start();

    }

    void setScene() {

        Node mainFloor = new Rectangle(0, 0, 1367, 168);
        rootPane.getChildren().add(mainFloor);
        mainFloor.setTranslateY(668);

        rootPane.getChildren().add(player.playerImageView);
        rootPane.getChildren().add(boss.bossImageView);

        boss.bossImageView.setTranslateX(1170);
        boss.bossImageView.setTranslateY(360);

        Main.standables.add(mainFloor);
        rootPane.getChildren().add(label1);
    }

    public void update() {
        player.movePlayer();
        boss.moveY();
        boss.fireBullet();
    }
}
