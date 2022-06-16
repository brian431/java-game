package scenes;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import model.Main;

import java.io.File;

public class Settlement {

    final int WIDTH = 1366;
    final int Height = 768;
    public Scene scene;
    public Pane pane;
    public VBox Vbox;
    public File file;
    public Media media;
    public MediaPlayer mediaPlayer;

    public Settlement(){

        pane = new Pane();

        pane.setBackground(new Background(new BackgroundImage(new Image("MainMenu.png"), BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT)));

        ImageView title = new ImageView();


//        Main.startTime = 123456;
//        Main.endTime = 123356;
//        Main.win = true;
//        Main.remainHealth = 3;
        System.out.println(Main.startTime);
        System.out.println(Main.endTime);
        if(Main.win){
            title.setImage(new Image("win.png"));
            title.relocate(389,205);
        }else{
            title.setImage(new Image("lose.png"));
            title.relocate(389,234);
        }
        pane.getChildren().add(title);

        int playTime =(int)(Main.endTime-Main.startTime)/1000;
        int minutes = Math.round(playTime/60);

        Label playTimeText = new Label("playTime: " + Integer.toString(minutes) + "min. " + Integer.toString((int)(playTime-60*minutes)) + "sec.");
        playTimeText.setFont(new Font("Arial", 30));
        playTimeText.setTextFill(Paint.valueOf("FFFFFF"));
        playTimeText.setTranslateX(389);
        playTimeText.setTranslateY(254);
        pane.getChildren().add(playTimeText);

        if(Main.win){
            if(Main.remainHealth > 0){
                ImageView star1 = new ImageView(new Image("star.png"));
                star1.setPreserveRatio(true);
                star1.setFitHeight(30);
                star1.relocate(100,100);
                pane.getChildren().add(star1);
                Main.remainHealth--;
            }
            if(Main.remainHealth > 0){
                ImageView star2 = new ImageView(new Image("star.png"));
                star2.setPreserveRatio(true);
                star2.setFitHeight(30);
                star2.relocate(200,100);
                pane.getChildren().add(star2);
                Main.remainHealth--;
            }
            if(Main.remainHealth > 0){
                ImageView star3 = new ImageView(new Image("star.png"));
                star3.setPreserveRatio(true);
                star3.setFitHeight(30);
                star3.relocate(300,100);
                pane.getChildren().add(star3);
            }
        }

        scene = new Scene(pane, WIDTH, Height);
    }

     public void Animation(){

     }
}
