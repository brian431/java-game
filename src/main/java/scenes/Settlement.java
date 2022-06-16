package scenes;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import model.Main;

import java.io.File;
import java.io.IOException;

public class Settlement {

    final int WIDTH = 1366;
    final int Height = 768;
    public Scene scene;
    public Pane pane;
    public VBox Vbox;
    public File file;
    public Media media;
    public MediaPlayer mediaPlayer;

    public Settlement() {

        pane = new Pane();

        ImageView title = new ImageView();


//        Main.startTime = 123456;
//        Main.endTime = 123356;
//        Main.win = true;
//        Main.remainHealth = 3;
//        System.out.println(Main.startTime);
//        System.out.println(Main.endTime);
        if (Main.win) {
            file = new File("src\\main\\resources\\win_bgm.mp3");
            media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.4);
            mediaPlayer.play();
            pane.setBackground(new Background(new BackgroundImage(new Image("stage_clear.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
            title.setImage(new Image("win.png"));
            title.setPreserveRatio(true);
            title.setFitHeight(260);
            title.relocate(389, 230);
        } else {
            file = new File("src\\main\\resources\\lose_bgm.mp3");
            media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.4);
            mediaPlayer.play();
            pane.setBackground(new Background(new BackgroundImage(new Image("stage_lose.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
//            title.setImage(new Image("lose.png"));
//            title.relocate(389,234);
        }
        pane.getChildren().add(title);

        int playTime = (int) (Main.endTime - Main.startTime) / 1000;
        int minutes = Math.round(playTime / 60);

        Label playTimeText = new Label("playTime: " + Integer.toString(minutes) + "min. " + Integer.toString((int) (playTime - 60 * minutes)) + "sec.");
        playTimeText.setFont(new Font("Verdana", 30));
        //playTimeText.setTextFill(Paint.valueOf("FFFFFF"));
        if (Main.win)
            playTimeText.relocate(500, 550);
        else
            playTimeText.relocate(500, 350);

        pane.getChildren().add(playTimeText);

        if (Main.win) {
            if (Main.remainHealth > 0) {
                ImageView star1 = new ImageView(new Image("star.png"));
                star1.setPreserveRatio(true);
                star1.setFitHeight(70);
                star1.relocate(400, 150);
                pane.getChildren().add(star1);
                Main.remainHealth--;
            }
            if (Main.remainHealth > 0) {
                ImageView star2 = new ImageView(new Image("star.png"));
                star2.setPreserveRatio(true);
                star2.setFitHeight(100);
                star2.relocate(631, 60);
                pane.getChildren().add(star2);
                Main.remainHealth--;
            }
            if (Main.remainHealth > 0) {
                ImageView star3 = new ImageView(new Image("star.png"));
                star3.setPreserveRatio(true);
                star3.setFitHeight(70);
                star3.relocate(862, 150);
                pane.getChildren().add(star3);
            }
        }

        Button menu = new Button();
        menu.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.stop();
                Main.settlement = null;
                try {
                    Main.Mainmenu = new MainMenu();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Main.stage.setScene(Main.Mainmenu.scene);
            }
        });
        ImageView menuPicture = new ImageView(new Image("menu.png"));
        menuPicture.setPreserveRatio(true);
        menuPicture.setFitHeight(48);
        menu.setGraphic(menuPicture);
        menu.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        menu.setStyle("-fx-background-color: transparent");
        menu.relocate(1268, 650);

        Button replay = new Button();
        replay.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.stop();
                Main.settlement = null;
                Main.level1 = new Level1();
                Main.stage.setScene(Main.level1.scene);
            }
        });
        ImageView replayPicture = new ImageView(new Image("replay.png"));
        replayPicture.setPreserveRatio(true);
        replayPicture.setFitHeight(48);
        replay.setGraphic(replayPicture);
        replay.setStyle("-fx-background-color: transparent");
        replay.relocate(0, 650);
        pane.getChildren().addAll(menu, replay);

        scene = new Scene(pane, WIDTH, Height);
    }

     public void Animation(){

     }
}
