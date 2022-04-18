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
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import scenes.Level;

import static model.Main.projectiles;
import static model.Main.standables;

public class Player {

    public Image player = new Image("player.png");
    public Image playermotion1 = new Image("playermotion1.png");
    public Image playermotion2 = new Image("playermotion2.png");
    public Image playermotion3 = new Image("playermotion3.png");

    public int weaponMode = 0;
    public int frame = 0;
    public int spriteNum = 0;

    public double playerWidth = 95;
    public double playerHeight = 150;
    public int horizontalSpeed = 13;

    public int verticalSpeed = 7;
    public int jumpHeight = 38;

    public int dashLength = 330;
    public int dashDuration = 120;

    public int bulletsInterval = 200;

    public boolean canJump = false;
    public boolean canShoot = true;
    public boolean facingRight = true;
    public boolean canDash = true;
    public boolean canSwitchWeapon = true;
    public boolean invincible = false;
    public boolean isRunning = false;

    public Timeline dashCd;
    public Timeline shootCd;
    public Timeline shotgunShootCd;
    public Timeline trackgunShootCd;
    public Timeline invincibleCd;

    public ImageView playerImageView;

    public Level myLevel;

    public Player() {

        playerImageView = new ImageView(player);
        playerImageView.setFitHeight(150);
        playerImageView.setFitWidth(95);
        //playerImageView.setPreserveRatio(true);

        dashCd = new Timeline(new KeyFrame(Duration.millis(600), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                canDash = true;
            }
        }));
        dashCd.setCycleCount(1);

        shootCd = new Timeline(new KeyFrame(Duration.millis(bulletsInterval), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                canShoot = true;
            }
        }));


        trackgunShootCd = new Timeline(new KeyFrame(Duration.millis(bulletsInterval + 100), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                canShoot = true;
            }
        }));

        shotgunShootCd = new Timeline(new KeyFrame(Duration.millis(bulletsInterval + 200), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                canShoot = true;
            }
        }));

        invincibleCd = new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                invincible = false;
            }
        }));

        playerImageView.setRotationAxis(new Point3D(0, 1, 0));
    }

    public void movePlayer() {

        if (verticalSpeed < 30) verticalSpeed += 2;
        moveY();

        if ((!Main.KeyCodes.getOrDefault(KeyCode.A, false) && !Main.KeyCodes.getOrDefault(KeyCode.D, false)) || Main.KeyCodes.getOrDefault(KeyCode.W, false)) {
            playerImageView.setImage(player);
        }

        /** Key A */
        if (Main.KeyCodes.getOrDefault(KeyCode.A, false)) {
            if (facingRight) {
                facingRight = false;
                playerImageView.setRotate(180);
            }
            isRunning = true;


            if (!Main.KeyCodes.getOrDefault(KeyCode.W, false)) {
                if (playerImageView.getTranslateX() > horizontalSpeed) {
                    playerImageView.setTranslateX(playerImageView.getTranslateX() - horizontalSpeed);
                }
                if (!canJump && playerImageView.getTranslateX() > 3) {
                    //playerImageView.setTranslateX(playerImageView.getTranslateX() - 3);
                }
                if (spriteNum == 0) {
                    playerImageView.setImage(playermotion1);
                } else if (spriteNum == 1) {
                    playerImageView.setImage(playermotion2);
                } else if (spriteNum == 2) {
                    playerImageView.setImage(playermotion3);
                }
            }


        }

        /** Key D */
        if (Main.KeyCodes.getOrDefault(KeyCode.D, false)) {
            if (!facingRight) {
                facingRight = true;
                playerImageView.setRotate(0);
            }

            isRunning = true;


            if (!Main.KeyCodes.getOrDefault(KeyCode.W, false)) {
                if (playerImageView.getTranslateX() + playerImageView.getFitWidth() < Level.WIDTH - horizontalSpeed) {
                    playerImageView.setTranslateX(playerImageView.getTranslateX() + horizontalSpeed);
                }
                if (!canJump && playerImageView.getTranslateX() + playerImageView.getFitWidth() < Level.WIDTH - 3) {
                    //playerImageView.setTranslateX(playerImageView.getTranslateX() + 3);
                }
                if (spriteNum == 0) {
                    playerImageView.setImage(playermotion1);
                } else if (spriteNum == 1) {
                    playerImageView.setImage(playermotion2);
                } else if (spriteNum == 2) {
                    playerImageView.setImage(playermotion3);
                }
            }


        }

        /** Key Space */
        if (Main.KeyCodes.getOrDefault(KeyCode.SPACE, false) && canJump) {
            verticalSpeed = -jumpHeight;
            canJump = false;
        }

        /** Key Shift */
        if (Main.KeyCodes.getOrDefault(KeyCode.SHIFT, false)) {
            if (canDash) dash();
        }

        /** Key J */
        if (Main.KeyCodes.getOrDefault(KeyCode.J, false)) {
            if (canShoot) shoot();
        }

        /** Key R */
        if (Main.KeyCodes.getOrDefault(KeyCode.R, false)) {
            if (canSwitchWeapon) {
                weaponMode = (weaponMode + 1) % 3;
                canSwitchWeapon = false;
                Timeline switchWeaponCd = new Timeline(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        canSwitchWeapon = true;
                    }
                }));
                switchWeaponCd.play();
            }
        }
    }

    public void moveY() {

        for (int i = 0; i < Math.abs(verticalSpeed); ++i) {
            if (!standables.isEmpty()) {
                for (Node standable : Main.standables) {
                    if (playerImageView.getTranslateY() + playerImageView.getFitHeight() == standable.getTranslateY() && verticalSpeed > 0) {
                        canJump = true;
                        return;
                    }
                }

            }
            playerImageView.setTranslateY(playerImageView.getTranslateY() + (verticalSpeed > 0 ? 1 : -1));
        }
    }

    public void dash() {
        if (canDash) {
            canDash = false;
            boolean tempFace = facingRight;
            int toWall = (int) (tempFace ? (Level.WIDTH - playerImageView.getTranslateX() - playerWidth) : playerImageView.getTranslateX());
            toWall = Math.min(toWall, dashLength);

            TranslateTransition tt = new TranslateTransition(Duration.millis(dashDuration * toWall / dashLength), playerImageView);
            tt.setByX(tempFace ? toWall : -toWall);
            tt.setInterpolator(Interpolator.LINEAR);
            tt.play();

            dashCd.play();
        }

    }


    public void shoot() {
        /** Shoot one bullet and start a timer */
        canShoot = false;


        /** Normal gun **/
        if (weaponMode == 0) {
            projectiles.add(new Projectile("playerBullet", facingRight ? playerImageView.getTranslateX() + playerWidth : playerImageView.getTranslateX(), playerImageView.getTranslateY() + playerHeight / 2,
                    new Point2D((Main.KeyCodes.getOrDefault(KeyCode.D, false) || Main.KeyCodes.getOrDefault(KeyCode.A, false)) || !Main.KeyCodes.getOrDefault(KeyCode.W, false) ? (facingRight ? 1 : -1) : 0, Main.KeyCodes.getOrDefault(KeyCode.W, false) ? -1 : 0)));
            shootCd.play();
        }

        /** Shotgun */
        else if (weaponMode == 1) {
            for (int degree = -24; degree <= 24; degree += 12)
                projectiles.add(new ShotgunBullet("shotgunBullet", facingRight ? playerImageView.getTranslateX() + playerWidth : playerImageView.getTranslateX(), playerImageView.getTranslateY() + playerHeight / 2,
                        new Point2D((Main.KeyCodes.getOrDefault(KeyCode.D, false) || Main.KeyCodes.getOrDefault(KeyCode.A, false)) || !Main.KeyCodes.getOrDefault(KeyCode.W, false) ? (facingRight ? 1 : -1) : Math.cos(Math.toRadians(90 + degree)) / Math.sin(Math.toRadians(90 + degree)), Main.KeyCodes.getOrDefault(KeyCode.W, false) ? (Main.KeyCodes.getOrDefault(KeyCode.D, false) || Main.KeyCodes.getOrDefault(KeyCode.A, false) ? Math.tan(Math.toRadians(degree)) - 1 : -1) : Math.tan(Math.toRadians(degree)))));
            shotgunShootCd.play();
        }


        /** TrackingGun */
        else if (weaponMode == 2) {
            projectiles.add(new TrackBullet("trackBullet", facingRight ? playerImageView.getTranslateX() + playerWidth : playerImageView.getTranslateX(), playerImageView.getTranslateY() + playerHeight / 2,
                    new Point2D((Main.KeyCodes.getOrDefault(KeyCode.D, false) || Main.KeyCodes.getOrDefault(KeyCode.A, false)) || !Main.KeyCodes.getOrDefault(KeyCode.W, false) ? (facingRight ? 1 : -1) : 0, Main.KeyCodes.getOrDefault(KeyCode.W, false) ? -1 : 0)));
            trackgunShootCd.play();
        }
    }

    public void detectBadThings() {
        /**
         * check if player hits projectiles or the boss
         *  if hit the boss, start invincible timer
         */

        for (int i = 0; i < Main.projectiles.size(); ++i) {
            if (Main.projectiles.get(i).projectileImage.getBoundsInParent().intersects(playerImageView.getBoundsInParent()) && (Main.projectiles.get(i).type.equals("bossBullet") || Main.projectiles.get(i).type.equals("falling"))) {

                myLevel.healthes.getChildren().remove(myLevel.healthes.getChildren().size() - 1);
                myLevel.rootPane.getChildren().remove(Main.projectiles.get(i).projectileImage);
                Main.projectiles.remove(i);

                invincible = true;
                invincibleCd.play();
            }
        }

        if (playerImageView.getBoundsInParent().intersects(myLevel.bossHitbox.getBoundsInParent()) && !invincible) {

            myLevel.healthes.getChildren().remove(myLevel.healthes.getChildren().size() - 1);

            invincible = true;

            invincibleCd.play();
        }
    }

    public void update() {
        //detectBadThings();
        movePlayer();
        if (isRunning) {
            ++frame;
            if (frame == 8) {
                ++spriteNum;
                frame = 0;
            }
            if (spriteNum == 3) {
                spriteNum = 0;
            }
        }


    }
}
