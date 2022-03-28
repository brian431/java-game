package model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import scenes.Level1;

import static model.Main.level1;

public class Player {
    public static String STANDING = "-fx-background-color: transparent";//;-fx-background-image: url(/images/cuphead.png)
    public int hp = 3;
    public int horizontalSpeed = 5;
    public int verticalSpeed = 7;
    public int sprintLength = 2;
    public int sprintLoop = 0;
    public Timeline sprintCd;
    public ImageView playerImage;
    public Image facingRightImage = new Image("/cuphead.png");
    public Image facingLeftImage = new Image("/cupheadFacingLeft.png");
    public Rectangle hitbox;
    boolean canjump = false;
    boolean facingRight = true;
    boolean canSprint = true;


    public Player() {
        hitbox = new Rectangle(500, 500);
        hitbox.setStyle(STANDING);
        playerImage = new ImageView(facingRightImage);
        playerImage.setFitHeight(150);
        playerImage.setFitWidth(95.45);
        playerImage.setPreserveRatio(true);


        sprintCd = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                canSprint = false;
                if (sprintLoop < 400) {
                    sprint();
                } else {
                    if (sprintLoop == 800) {
                        canSprint = true;
                        sprintLoop = 0;
                    }
                }
                sprintLoop += 10;
            }
        }));
        sprintCd.setCycleCount(800);
        //playerImage.setImage();
    }

    public void movePlayer() {

        if (verticalSpeed < 17) verticalSpeed += 2;
        moveY();
        if (Main.KeyCodes.getOrDefault(KeyCode.A, false) == true) {
            facingRight = false;
            playerImage.setImage(facingLeftImage);
            if (playerImage.getTranslateX() > horizontalSpeed) {
                playerImage.setTranslateX(playerImage.getTranslateX() - 12);
            }
        }

        if (Main.KeyCodes.getOrDefault(KeyCode.D, false) == true) {
            facingRight = true;
            playerImage.setImage(facingRightImage);
            if (playerImage.getTranslateX() + playerImage.getFitWidth() < Level1.WIDTH - horizontalSpeed) {
                playerImage.setTranslateX(playerImage.getTranslateX() + 12);
            }
        }

        if (Main.KeyCodes.getOrDefault(KeyCode.SPACE, false) == true && canjump) {
            level1.label1.setText("hi");
            verticalSpeed = -35;
            canjump = false;
        }

        if (Main.KeyCodes.getOrDefault(KeyCode.SHIFT, false) == true) {
            if (canSprint) sprintCd.play();
        }
    }

    public void moveY() {
        //int temp = verticalSpeed;
        for (int i = 0; i < Math.abs(verticalSpeed); ++i) {
            for (Node standable : Main.standables) {
                if (playerImage.getTranslateY() + playerImage.getFitHeight() == standable.getTranslateY() && verticalSpeed > 0) {
                    canjump = true;
                    return;
                }
            }
            playerImage.setTranslateY(playerImage.getTranslateY() + (verticalSpeed > 0 ? 1 : -1));
        }
    }

    public void sprint() {
        boolean tempFace = facingRight;
        for (int i = 0; i < sprintLength; i += 1) {
            if (tempFace) {
                if (playerImage.getTranslateX() + playerImage.getFitWidth() < Level1.WIDTH) {
                    playerImage.setTranslateX(playerImage.getTranslateX() + 1);
                }
            } else if (playerImage.getTranslateX() > 0) {
                playerImage.setTranslateX(playerImage.getTranslateX() - 1);
            }
        }
    }


}
