package scenes;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import model.Level1Boss;
import model.Main;
import model.Player;

import java.util.ArrayList;
import java.util.HashMap;

import static model.Main.projectiles;

public class Level1 extends Level {

    public static int howManyBossToShow = 400;
    public Scene scene;
    public Label label1 = new Label();
    public Rectangle container;
    public Rectangle mainFloor;
    public Player player;
    public Level1Boss boss;
    public boolean playingLose = false;

    AnimationTimer mainTimer;
    Timeline phase1Cycle;


    public Level1() {

        rootPane = new Pane();
        scene = new Scene(rootPane, WIDTH, HEIGHT);

        scene.setOnKeyPressed(keyEvent -> Main.KeyCodes.put(keyEvent.getCode(), true));
        scene.setOnKeyReleased(keyEvent -> Main.KeyCodes.put(keyEvent.getCode(), false));
        scene.setOnMousePressed(e -> Main.KeyCodes.put(KeyCode.J, true));
        scene.setOnMouseReleased(e -> Main.KeyCodes.put(KeyCode.J, false));

        mainTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };

        setPreScene();
        startReadyAnimation();

    }

    public void setPreScene() {

        player = new Player();
        player.myLevel = this;

        boss = new Level1Boss();
        bossHitbox = boss.bossImageView;
        boss.bossImageView.setTranslateX(WIDTH - howManyBossToShow);
        boss.bossImageView.setTranslateY(0);

        rootPane.getChildren().add(player.playerImageView);
        rootPane.getChildren().add(boss.bossImageView);

        mainFloor = new Rectangle(0, 0, WIDTH, 168);
        rootPane.getChildren().add(mainFloor);
        mainFloor.setTranslateY(668);
        Main.standables.add(mainFloor);

        rootPane.getChildren().add(label1);

        container = new Rectangle(-200, -200, 2000, 1600);
        container.setFill(Color.TRANSPARENT);
        rootPane.getChildren().add(container);
    }

    public void startReadyAnimation() {
        /**
         *  play start animation and set the timer
         */

        Label ready = new Label("READY?");
        ready.setFont(new Font("Arial", 200));
        ready.setTranslateX(200);
        ready.setTranslateY(200);
        Label start = new Label("START");
        start.setFont(new Font("Arial", 200));
        start.setTranslateX(200);
        start.setTranslateY(200);

        rootPane.getChildren().add(ready);
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                rootPane.getChildren().remove(ready);
                rootPane.getChildren().add(start);
            }
        }));
        tl.play();
        Timeline tl2 = new Timeline(new KeyFrame(Duration.millis(2000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                rootPane.getChildren().remove(start);
                setScene();
                mainTimer.start();
            }
        }));
        tl2.play();
    }


    void setScene() {

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

        player.update();
        boss.update();
        label1.setFont(new Font("Times New Roman", 200));
        label1.setText("" + player.weaponMode);

        /** add and remove projectiles*/
        for (int i = 0; i < projectiles.size(); ++i) {
            if (projectiles.get(i).type == "trackBullet") {
                projectiles.get(i).renewTarget(new Point2D(boss.bossImageView.getTranslateX() + boss.bossWidth / 2, boss.bossImageView.getTranslateY() + boss.bossHeight / 2));
            }
            projectiles.get(i).move();
            if (!rootPane.getChildren().contains(projectiles.get(i).projectileImage)) {
                rootPane.getChildren().add(projectiles.get(i).projectileImage);
            }
            if (!container.contains(projectiles.get(i).projectileImage.getTranslateX(), projectiles.get(i).projectileImage.getTranslateY())) {
                rootPane.getChildren().remove(projectiles.get(i).projectileImage);
                projectiles.remove(i);
            }
        }

        healthBar.setWidth(boss.hp);


        /** player dead */
        if (healthes.getChildren().size() == 0 && !playingLose) {
            startLoseAnimation();
            playingLose = true;
            mainTimer.stop();
        }
    }

    public void startLoseAnimation() {
        Label lose = new Label("YOU LOSE");
        lose.setFont(new Font("Arial", 200));
        lose.setTranslateX(200);
        lose.setTranslateY(200);
        rootPane.getChildren().add(lose);
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(2000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                rootPane.getChildren().remove(lose);
                Main.standables.remove(mainFloor);
                Main.KeyCodes = new HashMap<>();
                Main.projectiles = new ArrayList<>();
                Main.level1 = null;
                Main.stage.setScene(Main.menu.scene);
            }
        }));
        tl.play();
    }
}
