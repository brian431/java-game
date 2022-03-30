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
import javafx.util.Duration;

import java.util.Random;

import static model.Main.projectiles;

public class Level1Boss {

    public int hp = 1000;
    public int phase = 1;
    public int bossWidth = 400;
    public int bossHeight = 250;
    public int verticalSpeed = 13;
    public int moveTime = 2000;
    public int bulletsPerRound = 10;
    public int bulletsInterval = 650;
    public int phase1CurrentTime = 0;

    public Random random = new Random(System.currentTimeMillis());

    public boolean facingRight = false;
    public boolean canShoot = true;
    public boolean shooting = false;
    public boolean phase1ing = false;

    public Image facingLeftImage = new Image("/Level1BossFacingLeft.png");
    public Image facingRightImage = new Image("/Level1BossFacingRight.png");
    public ImageView bossImageView;

    Timeline bulletTimeline;


    public Level1Boss() {
        bossImageView = new ImageView(facingLeftImage);
        bossImageView.setFitHeight(bossHeight);
        bossImageView.setFitWidth(bossWidth);

    }

    public void startPhase1Cycle() {
        phase1CurrentTime = 0;
        phase1ing = true;

        bulletsPerRound = random.nextInt(5) + 8;
        canShoot = true;
        phase1CurrentTime += bulletsPerRound * bulletsInterval;
        KeyFrame moveLeftKF = new KeyFrame(Duration.millis(phase1CurrentTime), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                canShoot = false;
                moveLeft();

            }
        });
        phase1CurrentTime += moveTime * 2;

        KeyFrame shootKF = new KeyFrame(Duration.millis(phase1CurrentTime), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                canShoot = true;

            }
        });
        bulletsPerRound = random.nextInt(5) + 8;
        phase1CurrentTime += bulletsPerRound * bulletsInterval;

        KeyFrame moveRightKF = new KeyFrame(Duration.millis(phase1CurrentTime), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                canShoot = false;
                moveRight();
            }
        });
        phase1CurrentTime += moveTime * 2;

        KeyFrame newKF = new KeyFrame(Duration.millis(phase1CurrentTime), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                phase1ing = false;
            }
        });

        Timeline tl = new Timeline();
        tl.getKeyFrames().addAll(moveLeftKF, shootKF, moveRightKF, newKF);
        tl.play();
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
        for (int i = 0; i < Main.projectiles.size(); ++i) {
            if (Main.projectiles.get(i).projectileImage.getBoundsInParent().intersects(bossImageView.getBoundsInParent()) && Main.projectiles.get(i).type.equals("playerBullet")) {
                Main.level1.rootPane.getChildren().remove(Main.projectiles.get(i).projectileImage);
                Main.projectiles.remove(i);
                hp -= 2;
            }
        }
    }

    public void bossShooting() {
        if (canShoot)
            shoot();
    }

    public void shoot() {
        if (!shooting) {
            Projectile projectile = new Projectile("bossBullet", facingRight ? bossImageView.getTranslateX() + bossWidth : bossImageView.getTranslateX(), bossImageView.getTranslateY() + bossHeight / 2, facingRight, false);
            projectile.projectileImage.setImage(new Image("rock.png"));
            projectile.projectileImage.setFitHeight(100);
            projectile.projectileImage.setFitWidth(100);

            projectiles.add(projectile);
            shooting = true;
            Timeline shootCd = new Timeline(new KeyFrame(Duration.millis(bulletsInterval), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    shooting = false;
                }
            }));
            shootCd.play();
        }
    }

    public void update() {
        bossShooting();
        detectBullet();
        moveY();
        if (phase == 1 && !phase1ing) {
            startPhase1Cycle();
        }
    }
}

