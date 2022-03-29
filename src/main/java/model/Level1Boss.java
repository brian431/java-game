package model;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Level1Boss {

    public int bossWidth = 400;
    public int bossHeight = 200;
    public int verticalSpeed = 13;
    public Image facingLeft= new Image("/Level1BossFacingLeft.png");
    public Image facingRight = new Image("/Level1BossFacingRight.png");
    public ImageView bossImageView;



    public Level1Boss() {
        bossImageView = new ImageView(facingLeft);
        bossImageView.setFitHeight(200);
        bossImageView.setFitWidth(400);

        Timeline bulletTimeline = new Timeline(new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                fireBullet();
            }
        }));
        bulletTimeline.setCycleCount(8);




        Timeline moveTimeline = new Timeline(new KeyFrame(Duration.millis(4000), new))
    }

    public void startPhase1Cycle() {
        fireBullet();
        moveLeft();
        fireBullet();
        moveRight();
    }

    public void fireBullet() {

    }

    public void moveLeft() {
        TranslateTransition tt = new TranslateTransition();
        tt.setDuration(Duration.millis(2000));
        tt.setNode(bossImageView);
        tt.setByX(-1366);
        tt.setInterpolator(Interpolator.EASE_OUT);
        tt.setCycleCount(1);
        tt.play();
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(2000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bossImageView.setImage(facingRight);
                tt.setByX(bossWidth);
                tt.play();
            }
        }));
        tl.play();
    }

    public void moveRight() {
        TranslateTransition tt = new TranslateTransition();
        tt.setDuration(Duration.millis(2000));
        tt.setNode(bossImageView);
        tt.setByX(1366);
        tt.setInterpolator(Interpolator.EASE_OUT);
        tt.setCycleCount(1);
        tt.play();
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(2000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bossImageView.setImage(facingLeft);
                tt.setByX(-bossWidth);
                tt.play();
            }
        }));
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
