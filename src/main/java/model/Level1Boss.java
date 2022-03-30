package model;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import scenes.Level1;

import java.util.Random;

import static model.Main.level1;
import static model.Main.projectiles;

public class Level1Boss {

    public int hp = 1000;
    public int phase = 1;
    public int bossWidth = 450;
    public int bossHeight = 250;
    public int verticalSpeed = 13;
    public int moveTime = 2000;
    public int bulletsPerRound = 10;
    public int bulletsInterval = 650;
    public int timeBeforeMove = 700;
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

        KeyFrame readyToMoveKF1 = new KeyFrame(Duration.millis(phase1CurrentTime), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                canShoot = false;
                playReadyToMoveAnimation();
            }
        });
        phase1CurrentTime += timeBeforeMove;

        KeyFrame moveKF = new KeyFrame(Duration.millis(phase1CurrentTime), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                canShoot = false;
                if(!facingRight) moveLeft();
                else moveRight();

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
        tl.getKeyFrames().addAll(readyToMoveKF1, moveKF,newKF);
        tl.play();
    }

    public void startPhase2Cycle() {
        bulletsInterval = 500;
        phase1CurrentTime = 0;
        phase1ing = true;

        bulletsPerRound = random.nextInt(5) + 8;
        canShoot = true;
        phase1CurrentTime += bulletsPerRound * bulletsInterval;

        KeyFrame readyToMoveKF1 = new KeyFrame(Duration.millis(phase1CurrentTime), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                canShoot = false;
                playReadyToMoveAnimation();
            }
        });
        phase1CurrentTime += timeBeforeMove;

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

        KeyFrame readyToMoveKF2 = new KeyFrame(Duration.millis(phase1CurrentTime), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                canShoot = false;
                playReadyToMoveAnimation();
            }
        });
        phase1CurrentTime += timeBeforeMove;

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
        tl.getKeyFrames().addAll(readyToMoveKF1, moveLeftKF, shootKF, readyToMoveKF2, moveRightKF, newKF);
        tl.play();
    }

    public void moveLeft() {
        facingRight = true;
        TranslateTransition tt = new TranslateTransition();
        tt.setDuration(Duration.millis(moveTime));
        tt.setNode(bossImageView);
        tt.setByX(-Main.level1.WIDTH - bossWidth + Level1.howManyBossToShow);
        tt.setInterpolator(Interpolator.EASE_OUT);
        tt.setCycleCount(1);
        tt.play();
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(moveTime), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bossImageView.setImage(facingRightImage);
                tt.setByX(Level1.howManyBossToShow);
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
        tt.setByX(level1.WIDTH + bossWidth - Level1.howManyBossToShow);
        tt.setInterpolator(Interpolator.EASE_OUT);
        tt.setCycleCount(1);
        tt.play();
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(moveTime), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bossImageView.setImage(facingLeftImage);
                tt.setByX(-Level1.howManyBossToShow);
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
                hp -= 5;
            }
        }
    }

    public void bossShooting() {
        if (canShoot)
            shoot();
    }

    public void shoot() {
        if (!shooting) {
            Projectile projectile = new Projectile("bossBullet", facingRight ? bossImageView.getTranslateX() + bossWidth : bossImageView.getTranslateX(), bossImageView.getTranslateY() + bossHeight / 2, new Point2D(facingRight ? 1 : -1, 0));
            projectile.projectileImage.setImage(new Image("rock.png"));
            projectile.projectileImage.setPreserveRatio(false);
            projectile.projectileImage.setFitHeight(70);
            projectile.projectileImage.setFitWidth(150);

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

    public void playReadyToMoveAnimation() {
        bossImageView.setImage(new Image("rock.png"));
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(timeBeforeMove), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bossImageView.setImage(new Image(!facingRight ? "Level1BossFacingRight.png" : "Level1BossFacingLeft.png"));
            }
        }));
        tl.play();
    }

    public void playTransformAnimation() {
        bossImageView.setImage(new Image("rock.png"));
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(2000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bossImageView.setImage(new Image(facingRight ? "Level1BossFacingRight.png" : "Level1BossFacingLeft.png"));
                phase = 2;
            }
        }));
        tl.play();
    }


    public void update() {
        bossShooting();
        detectBullet();
        moveY();
        if(hp == 500) phase = 0;

        if (phase == 1 && !phase1ing) {
            startPhase1Cycle();
        }else if(phase == 0 && !phase1ing) {
            playTransformAnimation();
        }else if(phase == 2) {
            startPhase2Cycle();
        }
    }
}

