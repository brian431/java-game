package model;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Level1Boss {
    public Image defaultImage = new Image("/Level1BossDefaultImage.png");
    public ImageView bossImageView;



    public Level1Boss() {
        bossImageView = new ImageView(defaultImage);
        bossImageView.setFitHeight(300);
        bossImageView.setFitWidth(200);

        fireBullet();
    }

    public void fireBullet() {

    }

    public void startMoving() {
        //while(true) {
            TranslateTransition tt = new TranslateTransition();
            tt.setDuration(Duration.millis(1000));
            tt.setNode(bossImageView);
            tt.setByX(-100);
            tt.setInterpolator(Interpolator.LINEAR);
            tt.setCycleCount(TranslateTransition.INDEFINITE);
            tt.setAutoReverse(true);
            tt.play();

        //}
    }
}
