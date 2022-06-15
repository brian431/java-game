package scenes;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.Main;

import java.io.File;

public class Sattlement {

    final int WIDTH = 1366;
    final int Height = 768;
    public Scene scene;
    public Pane pane;
    public VBox Vbox;
    public File file;
    public Media media;
    public MediaPlayer mediaPlayer;

    public Sattlement(){

        pane = new Pane();

        pane.setBackground(new Background(new BackgroundImage(new Image("MainMenu.png"), BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT)));

        Label title = new Label();

        if(Main.win){
            title.setText("Win");
        }else{
            title.setText("Lose");
        }

        long playTime =Main.startTime-Main.endTime;
        int minutes = Math.round(playTime/60);

        Label playerTimeText = new Label(Integer.toString(minutes) + "min. " + Integer.toString((int)playTime-60*minutes) + "sec.");



        scene = new Scene(pane, WIDTH, Height);
    }

     public void Animation(){

     }
}
