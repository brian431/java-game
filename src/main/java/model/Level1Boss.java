package model;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import scenes.Level;
import scenes.Level1;

import java.io.File;
import java.util.Random;

import static model.Main.level1;
import static model.Main.projectiles;

public class Level1Boss {

    public Random random = new Random(System.currentTimeMillis());

    public int hp = 1000;
    public int phase = -1;
    public int frame0;
    public int frameThrow = 0;
    public int phase0SpriteNum = 0;
    public int throwSpriteNum = 0;
    public int readySpriteNum = 0;

    public int bossWidth = 400;
    public int bossHeight = 250;

    public int verticalSpeed = 15;
    public int jumpSpeed = 45;
    public int moveTime = 2000;
    public int moveTime2 = 1750;

    public int bulletWidth = 100;
    public int bulletHeight = 70;
    public int bulletsPerRound = 10;
    public int bulletsInterval = 600;
    public int timeBeforeMove = 1200;
    public int phaseCurrentTime = 0;

    public int rockHeight = 120;
    public int rockWidth = 80;
    public int rockSpeed = 22;

    public boolean facingRight = false;
    public boolean canShoot = true;
    public boolean tempCanShoot = true;
    public boolean shooting = false;
    public boolean phaseing = false;
    public boolean phase2ing = false;
    public boolean canThrowRock = true;
    public boolean invincible = false;
    public boolean transforming = true;
    public boolean calledT1 = false;
    public boolean readying = false;
    public boolean canRoar = true;

    public File file;
    public Media media;
    public MediaPlayer Player;

    public ImageView bossImageView;

    public Projectile rock;

    public Timeline shootCd;
    public Timeline showRock;
    public Timeline rockCd;


    public Level1Boss() {
        bossImageView = new ImageView("level1BossDefault.png");
        bossImageView.setFitHeight(bossHeight);
        bossImageView.setFitWidth(bossWidth);
        bossImageView.setRotationAxis(new Point3D(0, 1, 0));

        shootCd = new Timeline(new KeyFrame(Duration.millis(bulletsInterval), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                shooting = false;
            }
        }));

        showRock = new Timeline(new KeyFrame(Duration.millis(300), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                projectiles.add(rock);
            }
        }));

        rockCd = new Timeline(new KeyFrame(Duration.millis(random.nextInt(2000) + 3000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                canThrowRock = true;
            }
        }));


    }

    public void startPhase1Cycle() {
        phaseCurrentTime = 0;
        phaseing = true;

        bulletsPerRound = random.nextInt(5) + 8;
        canShoot = true;
        tempCanShoot = true;
        throwSpriteNum = 0;
        phaseCurrentTime += bulletsPerRound * bulletsInterval;

        KeyFrame readyToMoveKF1 = new KeyFrame(Duration.millis(phaseCurrentTime), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                canShoot = false;
                frameThrow = 0;
                throwSpriteNum = 0;
                readySpriteNum = 0;
                readying = true;
            }
        });
        phaseCurrentTime += timeBeforeMove;

        KeyFrame moveKF = new KeyFrame(Duration.millis(phaseCurrentTime), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                frameThrow = 0;
                throwSpriteNum = 0;
                canShoot = false;
                if (!facingRight) moveLeft();
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
        phase2ing = true;
        phaseCurrentTime += random.nextInt(3000);

        KeyFrame readyToMoveKF = new KeyFrame(Duration.millis(phaseCurrentTime), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                readySpriteNum = 0;
                readying = true;
            }
        });
        phaseCurrentTime += timeBeforeMove;

        KeyFrame moveKF = new KeyFrame(Duration.millis(phaseCurrentTime), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!facingRight) moveLeft();
                else moveRight();
            }
        });
        phaseCurrentTime += moveTime * 2;

        KeyFrame newKF = new KeyFrame(Duration.millis(phaseCurrentTime), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                phase2ing = false;
            }
        });

        Timeline tl = new Timeline();
        tl.getKeyFrames().addAll(readyToMoveKF, moveKF, newKF);
        tl.play();

    }

    public void moveLeft() {
        if (phase == 2) moveTime = moveTime2;
        int jumpOrNot = random.nextInt(3);
        TranslateTransition tt = new TranslateTransition();
        tt.setDuration(Duration.millis(moveTime));
        tt.setNode(bossImageView);
        tt.setByX(-Level.WIDTH - bossWidth + Level1.howManyBossToShow);
        tt.setInterpolator(Interpolator.EASE_OUT);
        tt.setCycleCount(1);
        tt.play();
        Timeline jumptime = new Timeline(new KeyFrame(Duration.millis(random.nextInt(150) + 240), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                verticalSpeed = -jumpSpeed;
            }
        }));
        if (!(jumpOrNot == 1) && phase == 2) jumptime.play();
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(moveTime), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bossImageView.setRotate(180);
                bossImageView.setImage(new Image("level1BossPhase0-6.png"));
                tt.setByX(Level1.howManyBossToShow);
                tt.play();
                facingRight = true;
            }
        }));
        tl.play();

    }

    public void moveRight() {
        if (phase == 2) moveTime = moveTime2;
        int jumpOrNot = random.nextInt(3);
        TranslateTransition tt = new TranslateTransition();
        tt.setDuration(Duration.millis(moveTime));
        tt.setNode(bossImageView);
        tt.setByX(Level.WIDTH + bossWidth - Level1.howManyBossToShow);
        tt.setInterpolator(Interpolator.EASE_OUT);
        tt.setCycleCount(1);
        tt.play();
        Timeline jumptime = new Timeline(new KeyFrame(Duration.millis(random.nextInt(150) + 240), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                verticalSpeed = -jumpSpeed;
            }
        }));
        if (!(jumpOrNot == 1) && phase == 2) jumptime.play();
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(moveTime), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bossImageView.setRotate(0);
                bossImageView.setImage(new Image("level1BossPhase0-6.png"));
                tt.setByX(-Level1.howManyBossToShow);
                tt.play();
                facingRight = false;
            }
        }));
        tl.play();

    }

    public void moveY() {

        if (verticalSpeed < 30) verticalSpeed += 2;
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
        if (!invincible) {
            for (int i = 0; i < Main.projectiles.size(); ++i) {
                if (Main.projectiles.get(i).projectileImage.getBoundsInParent().intersects(bossImageView.getBoundsInParent()) &&
                        (Main.projectiles.get(i).type.equals("playerBullet") || Main.projectiles.get(i).type.equals("shotgunBullet") || Main.projectiles.get(i).type.equals("trackBullet"))) {
                    Main.level1.rootPane.getChildren().remove(Main.projectiles.get(i).projectileImage);
                    Main.projectiles.remove(i);
                    hp -= 95;
                    file = new File("src\\main\\resources\\bullet2.wav");
                    media = new Media(file.toURI().toString());
                    Player = new MediaPlayer(media);
                    Player.setVolume(0.25);
                    Player.play();
                }
            }
        }
    }

    public void shoot() {
        Projectile projectile = new Projectile("bossBullet", facingRight ? bossImageView.getTranslateX() + 30 : bossImageView.getTranslateX() + 290, bossImageView.getTranslateY() + bossHeight / 2 + 20, new Point2D(facingRight ? 1 : -1, 0));
        projectile.bulletSpeed = 15;
        projectile.projectileImage.setImage(new Image("rock.png"));
        projectile.projectileImage.setPreserveRatio(false);
        projectile.projectileImage.setFitHeight(bulletHeight);
        projectile.projectileImage.setFitWidth(bulletWidth);

        projectiles.add(projectile);
    }

    public void playReadyToMoveAnimation() {
        ++frame0;
        if (frame0 == 8) {
            ++readySpriteNum;
            frame0 = 0;
        }
        //if(readySpriteNum == 4 && bossImageView.getTranslateY() == 453) bossImageView.setTranslateY(403);
        if (readySpriteNum <= 8) {
            String s = "level1BossReady-" + (readySpriteNum + 1) + ".png";
            bossImageView.setImage(new Image(s));
        }
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(timeBeforeMove), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (facingRight) bossImageView.setRotate(180);
                else bossImageView.setRotate(0);
                bossImageView.setImage(new Image("level1BossPhase0-6.png"));
                canRoar = true;
                readying = false;
            }
        }));
        tl.play();
    }

    public void playTransformAnimation() {
        ++frame0;
        if (frame0 == 10) {
            ++phase0SpriteNum;
            frame0 = 0;
        }
        if (phase0SpriteNum == 6) phase0SpriteNum = 0;
        String s = "level1BossPhase0-" + (phase0SpriteNum + 1) + ".png";
        bossImageView.setImage(new Image(s));
        if (!calledT1) {
            Timeline tl = new Timeline(new KeyFrame(Duration.millis(4000), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (facingRight) bossImageView.setRotate(180);
                    else bossImageView.setRotate(0);
                    bossImageView.setImage(new Image("level1BossDefault.png"));
                    if (phase == 0) phase = 2;
                    else if (phase == -1) phase = 1;
                    invincible = false;
                    transforming = false;
                }
            }));
            tl.play();
            calledT1 = true;
        }


    }


    public void throwRock() {

        rockCd.play();
        rock = new Projectile("falling", level1.player.playerImageView.getTranslateX(), -30, new Point2D(0, 1));
        rock.projectileImage.setImage(new Image("fireball.png"));
        rock.projectileImage.setRotate(0);
        rock.projectileImage.setFitHeight(rockHeight);
        rock.projectileImage.setFitWidth(rockWidth);
        rock.bulletSpeed = rockSpeed;
        level1.rootPane.getChildren().add(rock.projectileImage);

        showRock.play();

    }

    public void shootAnimation() {
        if (!facingRight) bossImageView.setRotate(0);
        else bossImageView.setRotate(180);
        ++frameThrow;

        if (frameThrow == 4) {
            ++throwSpriteNum;
            frameThrow = 0;
        }
        if (throwSpriteNum == 8) {
            throwSpriteNum = 0;
        }
        if (throwSpriteNum == 2 && tempCanShoot) {
            shoot();
            tempCanShoot = false;
        }
        if (throwSpriteNum == 3) tempCanShoot = true;
        String s = "level1BossThrowing-" + (throwSpriteNum + 1) + ".png";
        bossImageView.setImage(new Image(s));
    }

    public void update() {
        detectBullet();
        moveY();
        if (phase == 1 && hp <= 600) {
            phase = 0;
            calledT1 = false;
        }
        if (readying) {
            if (canRoar) {
                File file = new File("src\\main\\resources\\monster2.wav");
                Media media = new Media(file.toURI().toString());
                MediaPlayer Player = new MediaPlayer(media);
                Player.setVolume(0.5);
                Player.play();
                canRoar = false;
            }
            playReadyToMoveAnimation();
        }
        if (phase == 1 && !phaseing) {
            startPhase1Cycle();
        } else if (phase == 1 && canShoot) {
            shootAnimation();
        } else if ((phase == 0 && canShoot) && phaseing) {
            shootAnimation();
        } else if ((phase == -1 || phase == 0) && !phaseing) {
            invincible = true;
            canShoot = false;
            transforming = true;
            playTransformAnimation();
        } else if (phase == 2 && !phase2ing) {
            canShoot = false;
            startPhase2Cycle();
        }

        if (phase == 2 && canThrowRock) {
            canThrowRock = false;
            throwRock();
        }
    }
}

