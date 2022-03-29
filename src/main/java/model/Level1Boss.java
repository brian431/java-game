package model;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Level1Boss {
    public Image defaultImage = new Image("/Level1BossDefaultImage.png");
    public ImageView bossImageView;
    public int verticalSpeed = 13;


    public Level1Boss() {
        bossImageView = new ImageView(defaultImage);
        bossImageView.setFitHeight(200);
        bossImageView.setFitWidth(400);

        fireBullet();
    }

    public void fireBullet() {

    }

    public void startMoving() {
        //while(true) {
        TranslateTransition tt = new TranslateTransition();
        tt.setDuration(Duration.millis(2000));
        tt.setNode(bossImageView);
        tt.setByX(-1100);
        tt.setInterpolator(Interpolator.LINEAR);
        tt.setCycleCount(TranslateTransition.INDEFINITE);
        tt.setAutoReverse(true);
        tt.play();

        //}
    }

    public void moveY() {
        for (int i = 0; i < Math.abs(verticalSpeed); ++i) {
            for (Node standable : Main.standables) {
                if (bossImageView.getTranslateY() + bossImageView.getFitHeight() == standable.getTranslateY() && verticalSpeed > 0) {
                    return;
                }
            }
            bossImageView.setTranslateY(bossImageView.getTranslateY() + (verticalSpeed > 0 ? 1 : -1));
        }
    }
}
