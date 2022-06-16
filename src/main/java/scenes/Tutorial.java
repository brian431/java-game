package scenes;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
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

public class Tutorial extends Level{

    public Scene scene;
    public Pane pane;

    public Player player;
    public Rectangle container;
    public Rectangle mainFloor;
    public File file;
    public Media media;
    public MediaPlayer level1BgmPlayer;
    public Label label;
    AnimationTimer mainTimer;
    public int sum = 0;

    public Tutorial(){

        pane = new Pane();
        scene = new Scene(pane,WIDTH,HEIGHT);

        scene.setOnKeyPressed(keyEvent -> Main.KeyCodes.put(keyEvent.getCode(), true));
        scene.setOnKeyReleased(keyEvent -> Main.KeyCodes.put(keyEvent.getCode(), false));
        scene.setOnMousePressed(e -> Main.KeyCodes.put(KeyCode.J, true));
        scene.setOnMouseReleased(e -> Main.KeyCodes.put(KeyCode.J, false));

        file = new File("src\\main\\resources\\bgmlevel1.wav");
        media = new Media(file.toURI().toString());
        level1BgmPlayer = new MediaPlayer(media);
        level1BgmPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        level1BgmPlayer.play();

        label = new Label();
        label.setText("");
        label.setFont(new Font("Verdana", 100));
        label.relocate(500,60);
        pane.getChildren().add(label);

        mainTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };

        mainTimer.start();
        init();

    }
    public void init(){
        pane.setBackground(new Background(new BackgroundImage(new Image("room.png"), BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT)));
        player = new Player();
        player.onTutorial = true;

        pane.getChildren().add(player.playerImageView);

        mainFloor = new Rectangle(0, 0, WIDTH, 123);
        mainFloor.setFill(Color.TRANSPARENT);
        pane.getChildren().add(mainFloor);
        mainFloor.setTranslateY(703);
        Main.standables.add(mainFloor);

        container = new Rectangle(-200, -200, 2000, 1600);
        container.setFill(Color.TRANSPARENT);
        pane.getChildren().add(container);
    }
    public void update(){
        player.update();
        for (int i = 0; i < projectiles.size(); ++i) {
            projectiles.get(i).move();
            if (!pane.getChildren().contains(projectiles.get(i).projectileImage)) {
                pane.getChildren().add(projectiles.get(i).projectileImage);
            }
            if (!container.contains(projectiles.get(i).projectileImage.getTranslateX(), projectiles.get(i).projectileImage.getTranslateY())) {
                pane.getChildren().remove(projectiles.get(i).projectileImage);
                projectiles.remove(i);
            }
        }
        start();
    }
    public void start(){
        if(sum == 0){
            label.setText("D 向右");
            if(Main.KeyCodes.getOrDefault(KeyCode.D, false)){
                label.setVisible(false);
                label.setText("");
                sum++;
            }
        }
        if(sum == 1){
            label.setText("A 向左");
            label.setVisible(true);
            if(Main.KeyCodes.getOrDefault(KeyCode.A, false)){
                label.setVisible(false);
                label.setText("");
                sum++;
            }
        }
        if(sum == 2){
            label.relocate(400,60);
            label.setText("SHIFT 衝刺");
            label.setVisible(true);
            if(Main.KeyCodes.getOrDefault(KeyCode.SHIFT, false)){
                label.setVisible(false);
                label.setText("");
                sum++;
            }
        }
        if(sum == 3){
            label.setText("SPACE 跳躍");
            label.setVisible(true);
            if(Main.KeyCodes.getOrDefault(KeyCode.SPACE, false)){
                label.setVisible(false);
                label.setText("");
                sum++;
            }
        }
        if(sum == 4){
            label.relocate(350,60);
            label.setText("J或滑鼠左鍵 射擊");
            label.setVisible(true);
            if(Main.KeyCodes.getOrDefault(KeyCode.J,false)){
                label.setVisible(false);
                label.setText("");
                sum++;
            }
        }
        if (sum == 5) {
            label.setText("W+J 向上射擊");
            label.setVisible(true);
            if(Main.KeyCodes.getOrDefault(KeyCode.W,false) && Main.KeyCodes.getOrDefault(KeyCode.J,false)){
                label.setVisible(false);
                label.setText("");
                sum++;
            }
        }
        if(sum == 6){
            label.relocate(300,60);
            label.setText("D+W+J 向右上射擊");
            label.setVisible(true);
            if(Main.KeyCodes.getOrDefault(KeyCode.W,false) && Main.KeyCodes.getOrDefault(KeyCode.J,false) && Main.KeyCodes.getOrDefault(KeyCode.D,false)){
                label.setVisible(false);
                label.setText("");
                sum++;
            }
        }
        if(sum == 7){
            label.setText("A+W+J 向左上射擊");
            label.setVisible(true);
            if(Main.KeyCodes.getOrDefault(KeyCode.W,false) && Main.KeyCodes.getOrDefault(KeyCode.J,false) && Main.KeyCodes.getOrDefault(KeyCode.A,false)){
                label.setVisible(false);
                label.setText("");
                sum++;
            }
        }
        if(sum == 8) {
            label.setFont(new Font("Verdana", 75));
            label.relocate(75,60);
            label.setText("R 切槍(三種型態:一般，散彈，追蹤)");
            label.setVisible(true);
            if (Main.KeyCodes.getOrDefault(KeyCode.R, false)) {
                label.setFont(new Font("Verdana", 100));
                label.relocate(200,60);
                label.setText("完成教學!!!(按Esc退出)");
                sum++;
            }
        }
        if(sum == 9){
            if (Main.KeyCodes.getOrDefault(KeyCode.ESCAPE, false)) {
                level1BgmPlayer.stop();
                Main.standables.remove(mainFloor);
                Main.KeyCodes = new HashMap<>();
                Main.projectiles = new ArrayList<>();
                Main.tutorial = null;
                Main.stage.setScene(Main.Mainmenu.scene);
                sum = 0;
            }
        }
    }
}

