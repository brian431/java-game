package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Projectile {

    public String type;
    public Image image = new Image("/bullet.png");
    public double bulletSpeed = 14;
    public ImageView projectileImage;
    boolean right = true;
    boolean up = true;


    public Projectile(String type, double X, double Y, boolean right, boolean up) {
        this.type = type;
        this.right = right;
        this.up = up;
        projectileImage = new ImageView(image);
        projectileImage.setFitHeight(9);
        projectileImage.setFitWidth(30);
        projectileImage.setPreserveRatio(true);
        projectileImage.setTranslateX(X);
        projectileImage.setTranslateY(Y);

    }

    public void move() {

        if (right)
            projectileImage.setTranslateX(projectileImage.getTranslateX() + bulletSpeed);
        else
            projectileImage.setTranslateX(projectileImage.getTranslateX() - bulletSpeed);
        if (up)
            projectileImage.setTranslateY(projectileImage.getTranslateY() - bulletSpeed);

    }
}
