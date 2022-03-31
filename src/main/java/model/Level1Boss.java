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
import javafx.util.Duration;
import scenes.Level1;

import java.util.Random;

import static model.Main.level1;
import static model.Main.projectiles;

public class Level1Boss {

    public Random random = new Random(System.currentTimeMillis());

    public int hp = 1000;
    public int phase = 0;

    public int bossWidth = 450;
    public int bossHeight = 250;

    public int verticalSpeed = 13;
    public int moveTime = 2000;

    public int bulletWidth = 100;
    public int bulletHeight = 70;
    public int bulletsPerRound = 10;
    public int bulletsInterval = 550;
    public int timeBeforeMove = 500;
    public int phaseCurrentTime = 0;

    public int rockHeight = 80;
    public int rockWidth = 80;
    public int rockSpeed = 25;

    public boolean facingRight = false;
    public boolean canShoot = true;
    public boolean shooting = false;
    public boolean phaseing = false;
    public boolean canThrowRock = true;

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
        phaseCurrentTime = 0;
        phaseing = true;

        bulletsPerRound = random.nextInt(5) + 8;
        canShoot = true;
        phaseCurrentTime += bulletsPerRound * bulletsInterval;

        KeyFrame readyToMoveKF1 = new KeyFrame(Duration.millis(phaseCurrentTime), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                canShoot = false;
                playReadyToMoveAnimation();
            }
        });
        phaseCurrentTime += timeBeforeMove;

        KeyFrame moveKF = new KeyFrame(Duration.millis(phaseCurrentTime), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                canShoot = false;
                if(!facingRight) moveLeft();
                else moveRight();

            }
        });
        phaseCurrentTime += moveTime * 2;

        KeyFrame newKF = new KeyFrame(Duration.millis(phaseCurrentTime), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                phaseing = false;
            }
        });

        Timeline tl = new Timeline();
        tl.getKeyFrames().addAll(readyToMoveKF1, moveKF, newKF);
        tl.play();
    }

    public void startPhase2Cycle() {

        phaseCurrentTime = 0;
        phaseing = true;
        phaseCurrentTime += random.nextInt(3000);

        KeyFrame readyToMoveKF = new KeyFrame(Duration.millis(phaseCurrentTime), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                playReadyToMoveAnimation();
            }
        });
        phaseCurrentTime += timeBeforeMove;

        KeyFrame moveKF = new KeyFrame(Duration.millis(phaseCurrentTime), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!facingRight) moveLeft();
                else moveRight();
            }
        });
        phaseCurrentTime += moveTime * 2;

        KeyFrame newKF = new KeyFrame(Duration.millis(phaseCurrentTime), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                phaseing = false;
            }
        });

        Timeline tl = new Timeline();
        tl.getKeyFrames().addAll(readyToMoveKF, moveKF, newKF);
        tl.play();

    }

    public void moveLeft() {

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
                facingRight = true;
            }
        }));
        tl.play();

    }

    public void moveRight() {

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
                facingRight = false;
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
            if (Main.projectiles.get(i).projectileImage.getBoundsInParent().intersects(bossImageView.getBoundsInParent()) &&
                    (Main.projectiles.get(i).type.equals("playerBullet") || Main.projectiles.get(i).type.equals("shotgunBullet") || Main.projectiles.get(i).type.equals("trackBullet"))) {
                Main.level1.rootPane.getChildren().remove(Main.projectiles.get(i).projectileImage);
                Main.projectiles.remove(i);
                hp -= 6;
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
            projectile.projectileImage.setFitHeight(bulletHeight);
            projectile.projectileImage.setFitWidth(bulletWidth);

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
                bossImageView.setImage(new Image(facingRight ? "Level1BossFacingRight.png" : "Level1BossFacingLeft.png"));
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


    public void throwRock() {
        Timeline rockCd = new Timeline(new KeyFrame(Duration.millis(random.nextInt(2000) + 3000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                canThrowRock = true;
            }
        }));
        rockCd.play();
        Projectile rock = new Projectile("falling", level1.player.playerImageView.getTranslateX(), -30, new Point2D(0, 1));
        rock.projectileImage.setFitHeight(rockHeight);
        rock.projectileImage.setFitWidth(rockWidth);
        rock.bulletSpeed = rockSpeed;
        level1.rootPane.getChildren().add(rock.projectileImage);
        Timeline show = new Timeline(new KeyFrame(Duration.millis(300), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                projectiles.add(rock);
            }
        }));
        show.play();

    }

    public void update() {
        bossShooting();
        detectBullet();
        moveY();
        if(phase == 1 && hp <= 700) phase = 0;

        if (phase == 1 && !phaseing) {
            startPhase1Cycle();
        }else if(phase == 0 && !phaseing) {
            canShoot = false;
            playTransformAnimation();
        }else if(phase == 2 && !phaseing) {
            canShoot = false;
            startPhase2Cycle();

        }

        if(phase == 2 && canThrowRock) {
            canThrowRock = false;
            throwRock();
        }
    }
}

