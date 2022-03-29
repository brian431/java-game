package scenes;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Level1Boss;
import model.Main;
import model.Player;

import static model.Main.projectiles;

public class Level1 extends Level {
    final public static int WIDTH = 1366;
    final public static int Height = 768;
    public Scene scene;
    public Pane rootPane;
    public Label label1 = new Label();
    public Rectangle container;


    Player player;
    Level1Boss boss;
    Rectangle mainFloor;


    public Level1() {
        rootPane = new Pane();
        scene = new Scene(rootPane, WIDTH, Height);
        scene.setOnKeyPressed(keyEvent -> Main.KeyCodes.put(keyEvent.getCode(), true));
        scene.setOnKeyReleased(keyEvent -> Main.KeyCodes.put(keyEvent.getCode(), false));

        scene.setOnMousePressed(e -> Main.KeyCodes.put(KeyCode.J, true));
        scene.setOnMouseReleased(e -> Main.KeyCodes.put(KeyCode.J, false));

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

        container = new Rectangle(-200, -200, 2000, 1000);
        container.setFill(Color.TRANSPARENT);
        rootPane.getChildren().add(container);
    }

    public void update() {
        player.movePlayer();
        boss.moveY();
        boss.fireBullet();
        label1.setText("" + projectiles.size());
        for (int i = 0; i < projectiles.size(); ++i) {
            projectiles.get(i).move();
            if (!rootPane.getChildren().contains(projectiles.get(i).projectileImage)) {
                rootPane.getChildren().add(projectiles.get(i).projectileImage);
            }
            if (!container.contains(projectiles.get(i).projectileImage.getTranslateX(), projectiles.get(i).projectileImage.getTranslateY())) {
                projectiles.remove(i);
            }
        }
    }

}
