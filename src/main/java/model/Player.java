package model;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import scenes.Level1;

import static model.Main.level1;

public class Player {

    public Image facingRightImage = new Image("/cuphead.png");
    public Image facingLeftImage = new Image("/cupheadFacingLeft.png");

    public int hp = 3;
    public double playerWidth = 95.45;
    public double playerHeight = 150;
    public int horizontalSpeed = 13;
    public int verticalSpeed = 7;
    public int jumpHeight = 37;
    public int dashLength = 360;
    public int dashLoop = 0;
    public boolean canjump = false;
    public boolean facingRight = true;
    public boolean canDash = true;
    public Timeline dashCd;
    public ImageView playerImageView;
    int dashDuration = 200;


    public Player() {

        playerImageView = new ImageView(facingRightImage);
        playerImageView.setFitHeight(playerHeight);
        playerImageView.setFitWidth(playerWidth);
        playerImageView.setPreserveRatio(true);
    }

    public void movePlayer() {

        if (verticalSpeed < 30) verticalSpeed += 2;
        moveY();

        if (Main.KeyCodes.getOrDefault(KeyCode.A, false) == true) {
            facingRight = false;
            playerImageView.setImage(facingLeftImage);
            if (playerImageView.getTranslateX() > horizontalSpeed) {
                playerImageView.setTranslateX(playerImageView.getTranslateX() - horizontalSpeed);
            }
            if (!canjump && playerImageView.getTranslateX() > 3) {
                playerImageView.setTranslateX(playerImageView.getTranslateX() - 3);
            }
        }

        if (Main.KeyCodes.getOrDefault(KeyCode.D, false) == true) {
            facingRight = true;
            playerImageView.setImage(facingRightImage);
            if (playerImageView.getTranslateX() + playerImageView.getFitWidth() < Level1.WIDTH - horizontalSpeed) {
                playerImageView.setTranslateX(playerImageView.getTranslateX() + horizontalSpeed);
            }
            if (!canjump && playerImageView.getTranslateX() + playerImageView.getFitWidth() < Level1.WIDTH - 3) {
                playerImageView.setTranslateX(playerImageView.getTranslateX() + 3);
            }
        }

        if (Main.KeyCodes.getOrDefault(KeyCode.SPACE, false) == true && canjump) {
            level1.label1.setText("hi");
            verticalSpeed = -jumpHeight;
            canjump = false;
        }

        if (Main.KeyCodes.getOrDefault(KeyCode.SHIFT, false) == true) {
            if (canDash) dash();
        }
    }

    public void moveY() {

        for (int i = 0; i < Math.abs(verticalSpeed); ++i) {
            for (Node standable : Main.standables) {
                if (playerImageView.getTranslateY() + playerImageView.getFitHeight() == standable.getTranslateY() && verticalSpeed > 0) {
                    canjump = true;
                    return;
                }
            }
            playerImageView.setTranslateY(playerImageView.getTranslateY() + (verticalSpeed > 0 ? 1 : -1));
        }
    }


    public void startDashCd() {

        dashCd = new Timeline(new KeyFrame(Duration.millis(700), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                canDash = true;
            }
        }));
        dashCd.setCycleCount(1);
        dashCd.play();
    }


    public void dash() {
        if (canDash) {
            canDash = false;
            boolean tempFace = facingRight;
            int toWall = (int) (tempFace ? (Level1.WIDTH - playerImageView.getTranslateX() - playerWidth) : playerImageView.getTranslateX());
            toWall = Math.min(toWall, dashLength);

            TranslateTransition tt = new TranslateTransition(Duration.millis(dashDuration * toWall / dashLength), playerImageView);
            tt.setByX(tempFace ? toWall : -toWall);
            tt.setInterpolator(Interpolator.LINEAR);
            tt.play();

            startDashCd();
        }

    }


}
