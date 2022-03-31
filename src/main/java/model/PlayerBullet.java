package model;

import javafx.geometry.Point2D;

public class PlayerBullet extends Projectile {

    public PlayerBullet(String type, double X, double Y, Point2D vector) {
        super(type, X, Y, vector);
    }
}
