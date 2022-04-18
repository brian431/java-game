package model;

import javafx.geometry.Point2D;

public class BossBullet extends Projectile {

    public BossBullet(String type, double X, double Y, Point2D vector) {

        super("BossBullet", X, Y, vector);
    }
}
