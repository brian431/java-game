package scenes;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import model.Level1Boss;
import model.Main;
import model.Player;

import java.io.File;
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
    public boolean playingWin = false;
    public File file;
    public Media media;
    public MediaPlayer level1BgmPlayer;

    AnimationTimer mainTimer;
    Timeline phase1Cycle;


    public Level1() {

        healthBar = new Rectangle(160, 40, 1000, 10);
        rootPane = new Pane();
        scene = new Scene(rootPane, WIDTH, HEIGHT);

        scene.setOnKeyPressed(keyEvent -> Main.KeyCodes.put(keyEvent.getCode(), true));
        scene.setOnKeyReleased(keyEvent -> Main.KeyCodes.put(keyEvent.getCode(), false));
        scene.setOnMousePressed(e -> Main.KeyCodes.put(KeyCode.J, true));
        scene.setOnMouseReleased(e -> Main.KeyCodes.put(KeyCode.J, false));

        file = new File("src\\main\\resources\\bgmlevel1.wav");
        media = new Media(file.toURI().toString());
        level1BgmPlayer = new MediaPlayer(media);
        level1BgmPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        level1BgmPlayer.play();

        mainTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };

        Main.startTime = System.currentTimeMillis();
        mainTimer.start();
        setPreScene();
        startReadyAnimation();

    }

    public void setPreScene() {

        ImageView backgroundImage = new ImageView("level1Background.png");
        backgroundImage.setFitWidth(WIDTH);
        backgroundImage.setFitHeight(HEIGHT);
        rootPane.getChildren().add(backgroundImage);
        player = new Player();
        player.myLevel = this;

        boss = new Level1Boss();
        bossHitbox = boss.bossImageView;
        boss.bossImageView.setTranslateX(WIDTH - howManyBossToShow);
        boss.bossImageView.setTranslateY(0);

        rootPane.getChildren().add(player.playerImageView);
        rootPane.getChildren().add(boss.bossImageView);

        mainFloor = new Rectangle(0, 0, WIDTH, 123);
        mainFloor.setFill(Color.TRANSPARENT);
        rootPane.getChildren().add(mainFloor);
        mainFloor.setTranslateY(703);
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

        ImageView goImage = new ImageView(new Image("goImage.png"));
        ImageView readyImage = new ImageView(new Image("readyImage.png"));
        readyImage.setTranslateX(300);
        readyImage.setTranslateY(280);
        goImage.setTranslateX(380);
        goImage.setTranslateY(250);
        goImage.setFitHeight(200);
        goImage.setPreserveRatio(true);

        rootPane.getChildren().add(readyImage);
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(3000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                rootPane.getChildren().remove(readyImage);
                rootPane.getChildren().add(goImage);
            }
        }));
        tl.play();

        Timeline tl2 = new Timeline(new KeyFrame(Duration.millis(4000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                rootPane.getChildren().remove(goImage);
                setScene();
                Main.remainHealth = 3;
            }
        }));
        tl2.play();
    }


    void setScene() {


        healthBar.setFill(Color.RED);
        rootPane.getChildren().add(healthBar);

        health1.setFitHeight(60);
        health1.setFitWidth(60);
        health2.setFitHeight(60);
        health2.setFitWidth(60);
        health3.setFitHeight(60);
        health3.setFitWidth(60);
        healthes.getChildren().addAll(health1, health2, health3);
        rootPane.getChildren().add(healthes);

    }


    public void update() {

        player.update();
        boss.update();
        label1.setFont(new Font("Times New Roman", 200));
        label1.setTextFill(Paint.valueOf("FFFFFF"));
        label1.setText("" + boss.readySpriteNum);

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
        if ((boss.phase != -1 && healthes.getChildren().size() == 0) && !playingLose) {
            startLoseAnimation();
            playingLose = true;
            mainTimer.stop();
        }

        if (boss.hp <= 0 && !playingWin) {
            win();
            playingWin = true;
            mainTimer.stop();
        }

    }

    public void startLoseAnimation() {
        level1BgmPlayer.stop();
        Label lose = new Label("YOU LOSE");
        lose.setFont(new Font("Arial", 200));
        lose.setTextFill(Paint.valueOf("FFFFFF"));
        lose.setTranslateX(200);
        lose.setTranslateY(300);
        rootPane.getChildren().add(lose);
        Main.win = false;
        Main.endTime = System.currentTimeMillis();
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(2000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainTimer.stop();
                rootPane.getChildren().remove(lose);
                Main.standables.remove(mainFloor);
                Main.KeyCodes = new HashMap<>();
                Main.projectiles = new ArrayList<>();
                Main.level1 = null;
                Main.sattlement = new Settlement();
                Main.stage.setScene(Main.sattlement.scene);
            }
        }));
        tl.play();
    }

    public void win(){
        level1BgmPlayer.stop();
        Label lose = new Label("YOU WIN");
        lose.setFont(new Font("Arial", 200));
        lose.setTextFill(Paint.valueOf("FFFFFF"));
        lose.setTranslateX(200);
        lose.setTranslateY(300);
        rootPane.getChildren().add(lose);
        Main.win = true;
        Main.endTime = System.currentTimeMillis();

        Timeline tl = new Timeline(new KeyFrame(Duration.millis(2000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainTimer.stop();
                rootPane.getChildren().remove(lose);
                Main.standables.remove(mainFloor);
                Main.KeyCodes = new HashMap<>();
                Main.projectiles = new ArrayList<>();
                Main.level1 = null;
                Main.sattlement = new Settlement();
                Main.stage.setScene(Main.sattlement.scene);
            }
        }));
        tl.play();

    }
}
