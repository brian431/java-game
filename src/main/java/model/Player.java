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
import scenes.Level;

import static model.Main.projectiles;
import static model.Main.standables;

public class Player {

    public Image facingRightImage = new Image("/cuphead.png");
    public Image facingLeftImage = new Image("/cupheadFacingLeft.png");

    public int hp = 3;

    public int weaponMode = 0;

    public double playerWidth = 95.45;
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

    public Timeline dashCd;
    public Timeline shootCd;
    public Timeline shotgunShootCd;
    public Timeline trackgunShootCd;
    public Timeline invincibleCd;

    public ImageView playerImageView;

    public Level myLevel;

    public Player() {

        playerImageView = new ImageView(facingRightImage);
        playerImageView.setFitHeight(playerHeight);
        playerImageView.setFitWidth(playerWidth);
        playerImageView.setPreserveRatio(true);

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

        trackgunShootCd = new Timeline(new KeyFrame(Duration.millis(bulletsInterval + 80), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                canShoot = true;
            }
        }));

        shotgunShootCd = new Timeline(new KeyFrame(Duration.millis(bulletsInterval), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                canShoot = true;
            }
        }));

        invincibleCd= new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                invincible = false;
            }
        }));
    }

    public void movePlayer() {

        if (verticalSpeed < 30) verticalSpeed += 2;
        moveY();

        /** Key A */
        if (Main.KeyCodes.getOrDefault(KeyCode.A, false) && !Main.KeyCodes.getOrDefault(KeyCode.W, false)) {
            facingRight = false;
            playerImageView.setImage(facingLeftImage);
            if (playerImageView.getTranslateX() > horizontalSpeed) {
                playerImageView.setTranslateX(playerImageView.getTranslateX() - horizontalSpeed);
            }
            if (!canJump && playerImageView.getTranslateX() > 3) {
                //playerImageView.setTranslateX(playerImageView.getTranslateX() - 3);
            }
        }

        /** Key D */
        if (Main.KeyCodes.getOrDefault(KeyCode.D, false) && !Main.KeyCodes.getOrDefault(KeyCode.W, false)) {
            facingRight = true;
            playerImageView.setImage(facingRightImage);
            if (playerImageView.getTranslateX() + playerImageView.getFitWidth() < Level.WIDTH - horizontalSpeed) {
                playerImageView.setTranslateX(playerImageView.getTranslateX() + horizontalSpeed);
            }
            if (!canJump && playerImageView.getTranslateX() + playerImageView.getFitWidth() < Level.WIDTH - 3) {
                //playerImageView.setTranslateX(playerImageView.getTranslateX() + 3);
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
        if(Main.KeyCodes.getOrDefault(KeyCode.R, false)) {
            if(canSwitchWeapon) {
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
            if(!standables.isEmpty()) {
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
        if(weaponMode == 0) {
            projectiles.add(new Projectile("playerBullet", facingRight ? playerImageView.getTranslateX() + playerWidth : playerImageView.getTranslateX(), playerImageView.getTranslateY() + playerHeight / 2, new Point2D(facingRight ? 1 : -1, Main.KeyCodes.getOrDefault(KeyCode.W, false) ? -1 : 0)));
            shootCd.play();
        }

        /** Shotgun */
        else if(weaponMode == 1) {
            projectiles.add(new ShotgunBullet("shotgunBullet", facingRight ? playerImageView.getTranslateX() + playerWidth : playerImageView.getTranslateX(), playerImageView.getTranslateY() + playerHeight / 2, new Point2D(facingRight ? 1 : -1, Main.KeyCodes.getOrDefault(KeyCode.W, false) ? -1 : 0)));
            shotgunShootCd.play();
        }

        /** TrackingGun */
        else if(weaponMode == 2) {
            projectiles.add(new TrackBullet("trackBullet", facingRight ? playerImageView.getTranslateX() + playerWidth : playerImageView.getTranslateX(), playerImageView.getTranslateY() + playerHeight / 2, new Point2D(facingRight ? 1 : -1, Main.KeyCodes.getOrDefault(KeyCode.W, false) ? -1 : 0)));
            trackgunShootCd.play();
        }
    }

    public void detectBadThings() {
        /**
         * check if player hits projectiles or the boss
         *  if hit the boss, start invincible timer
         */

        for (int i = 0; i < Main.projectiles.size(); ++i) {
            if (Main.projectiles.get(i).projectileImage.getBoundsInParent().intersects(playerImageView.getBoundsInParent()) && (Main.projectiles.get(i).type.equals("bossBullet") || Main.projectiles.get(i).type.equals("falling")))  {

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
        detectBadThings();
        movePlayer();
    }
}
