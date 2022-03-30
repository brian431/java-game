package scenes;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Level1Boss;
import model.Main;
import model.Player;

import java.util.ArrayList;
import java.util.HashMap;

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
    public ImageView health1 = new ImageView(new Image("health.png"));
    public ImageView health2 = new ImageView(new Image("health.png"));
    public ImageView health3 = new ImageView(new Image("health.png"));
    public VBox healthes = new VBox();
    public Player player;
    public Level1Boss boss;
    AnimationTimer mainTimer;
    Timeline phase1Cycle;


    public Level1() {

        rootPane = new Pane();
        scene = new Scene(rootPane, WIDTH, HEIGHT);

        scene.setOnKeyPressed(keyEvent -> Main.KeyCodes.put(keyEvent.getCode(), true));
        scene.setOnKeyReleased(keyEvent -> Main.KeyCodes.put(keyEvent.getCode(), false));
        scene.setOnMousePressed(e -> Main.KeyCodes.put(KeyCode.J, true));
        scene.setOnMouseReleased(e -> Main.KeyCodes.put(KeyCode.J, false));

        setScene();

        phase1Cycle = boss.getPhase1Cycle();
        phase1Cycle.play();

        mainTimer = new AnimationTimer() {
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
        boss.bossImageView.setTranslateX(1100);
        boss.bossImageView.setTranslateY(0);

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

        health1.setFitHeight(30);
        health1.setFitWidth(30);
        health2.setFitHeight(30);
        health2.setFitWidth(30);
        health3.setFitHeight(30);
        health3.setFitWidth(30);
        healthes.getChildren().addAll(health1, health2, health3);
        rootPane.getChildren().add(healthes);

    }

    public void update() {

        player.movePlayer();
        boss.moveY();
        boss.detectBullet();
        player.detectBullet();
        boss.bossShooting();
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
        if (healthes.getChildren().size() == 0) {

            Main.KeyCodes = new HashMap<>();
            Main.stage.setScene(Main.menu.scene);
            mainTimer.stop();
            Main.projectiles = new ArrayList<>();
            phase1Cycle.stop();
            Main.level1 = null;
        }
    }

}
