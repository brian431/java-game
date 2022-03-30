package scenes;

import javafx.animation.AnimationTimer;
import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    final public static int HEIGHT = 768;
    public Scene scene;
    public Pane rootPane;
    public Label label1 = new Label();

    public Rectangle container;
    public Rectangle mainFloor;
    public Rectangle healthBar;
    //public ImageView health1 = new Image();
    //public ImageView health2 = new Image();
    //public ImageView health3 = new Image();


    public Player player;
    public Level1Boss boss;



    public Level1() {

        rootPane = new Pane();
        scene = new Scene(rootPane, WIDTH, HEIGHT);

        scene.setOnKeyPressed(keyEvent -> Main.KeyCodes.put(keyEvent.getCode(), true));
        scene.setOnKeyReleased(keyEvent -> Main.KeyCodes.put(keyEvent.getCode(), false));
        scene.setOnMousePressed(e -> Main.KeyCodes.put(KeyCode.J, true));
        scene.setOnMouseReleased(e -> Main.KeyCodes.put(KeyCode.J, false));

        setScene();

        boss.startPhase1Cycle();

        AnimationTimer mainTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };
        mainTimer.start();

    }

    void setScene() {

        player = new Player();
        boss = new Level1Boss();
        boss.bossImageView.setTranslateX(WIDTH - boss.bossWidth);
        boss.bossImageView.setTranslateY(360);

        rootPane.getChildren().add(player.playerImageView);
        rootPane.getChildren().add(boss.bossImageView);

        Node mainFloor = new Rectangle(0, 0, 1367, 168);
        rootPane.getChildren().add(mainFloor);
        mainFloor.setTranslateY(668);
        Main.standables.add(mainFloor);

        rootPane.getChildren().add(label1);

        container = new Rectangle(-200, -200, 2000, 1000);
        container.setFill(Color.TRANSPARENT);
        rootPane.getChildren().add(container);

        healthBar = new Rectangle(160, 40, 1000, 10);
        healthBar.setFill(Color.RED);
        rootPane.getChildren().add(healthBar);

    }

    public void update() {

        player.movePlayer();
        boss.moveY();
        boss.fireBullet();
        boss.detectBullet();
        label1.setText("" + boss.bossImageView.getBoundsInParent());

        for (int i = 0; i < projectiles.size(); ++i) {
            projectiles.get(i).move();
            if (!rootPane.getChildren().contains(projectiles.get(i).projectileImage)) {
                rootPane.getChildren().add(projectiles.get(i).projectileImage);
            }
            if (!container.contains(projectiles.get(i).projectileImage.getTranslateX(), projectiles.get(i).projectileImage.getTranslateY())) {
                projectiles.remove(i);
            }
        }

        healthBar.setWidth(boss.hp);
    }

}
