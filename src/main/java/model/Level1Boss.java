package model;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import static model.Main.projectiles;

public class Level1Boss {

    public int hp = 1000;
    public int maxHp = 2000;
    public int bossWidth = 400;
    public int bossHeight = 250;
    public int verticalSpeed = 13;
    public int moveTime = 2000;
    public boolean facingRight = false;


    public Image facingLeftImage = new Image("/Level1BossFacingLeft.png");
    public Image facingRightImage = new Image("/Level1BossFacingRight.png");
    public ImageView bossImageView;

    Timeline bulletTimeline;


    public Level1Boss() {
        bossImageView = new ImageView(facingLeftImage);
        bossImageView.setFitHeight(bossHeight);
        bossImageView.setFitWidth(bossWidth);

        bulletTimeline = new Timeline(new KeyFrame(Duration.millis(800), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                fireBullet();
            }
        }));
        bulletTimeline.setCycleCount(10);


        //Timeline moveTimeline = new Timeline(new KeyFrame(Duration.millis(4000), new))
    }

    public Timeline getPhase1Cycle() {
        bulletTimeline.play();
        KeyFrame moveLeftKF = new KeyFrame(Duration.millis(8000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                moveLeft();
            }
        });

        KeyFrame shootKF = new KeyFrame(Duration.millis(12000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bulletTimeline.play();
            }
        });

        KeyFrame moveRightKF = new KeyFrame(Duration.millis(20000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                moveRight();
            }
        });

        Timeline tl = new Timeline();
        tl.getKeyFrames().addAll(moveLeftKF, shootKF, moveRightKF);
        return tl;
    }

    public void fireBullet() {
        Projectile projectile = new Projectile("bossBullet", facingRight ? bossImageView.getTranslateX() + bossWidth : bossImageView.getTranslateX(), bossImageView.getTranslateY() + bossHeight / 2, facingRight,false);
        projectile.projectileImage.setImage(new Image("rock.png"));
        projectile.projectileImage.setFitHeight(80);
        projectile.projectileImage.setFitWidth(80);

        projectiles.add(projectile);
    }

    public void moveLeft() {
        facingRight = true;
        TranslateTransition tt = new TranslateTransition();
        tt.setDuration(Duration.millis(moveTime));
        tt.setNode(bossImageView);
        tt.setByX(-1366);
        tt.setInterpolator(Interpolator.EASE_OUT);
        tt.setCycleCount(1);
        tt.play();
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(moveTime), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bossImageView.setImage(facingRightImage);
                tt.setByX(bossWidth);
                tt.play();
            }
        }));
        tl.play();
    }

    public void moveRight() {
        facingRight = false;
        TranslateTransition tt = new TranslateTransition();
        tt.setDuration(Duration.millis(moveTime));
        tt.setNode(bossImageView);
        tt.setByX(1366);
        tt.setInterpolator(Interpolator.EASE_OUT);
        tt.setCycleCount(1);
        tt.play();
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(moveTime), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bossImageView.setImage(facingLeftImage);
                tt.setByX(-bossWidth);
                tt.play();
            }
        }));
        tl.play();
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

    public void detectBullet() {
        for(int i = 0; i < Main.projectiles.size(); ++i) {
            if(Main.projectiles.get(i).projectileImage.getBoundsInParent().intersects(bossImageView.getBoundsInParent()) && Main.projectiles.get(i).type.equals("playerBullet")) {
                Main.level1.rootPane.getChildren().remove(Main.projectiles.get(i).projectileImage);
                Main.projectiles.remove(i);
                hp -= 2;
            }
        }
    }
}
