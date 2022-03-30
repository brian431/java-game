package model;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Projectile {

    public double bulletSpeed = 14;
    public ImageView projectileImage = new ImageView(new Image("bullet.png"));
    public Point2D vector;
    public String type;


    public Projectile(String type, double X, double Y, Point2D vector ) {
        this.type = type;
        projectileImage.setTranslateX(X);
        projectileImage.setFitHeight(9);
        projectileImage.setFitWidth(30);
        projectileImage.setTranslateY(Y);
        projectileImage.setRotate(-vector.angle(1,0));
        this.vector = vector;

    }

    public void move() {
        projectileImage.setTranslateX(projectileImage.getTranslateX()+(int)(vector.getX()/vector.distance(0,0)*bulletSpeed));
        projectileImage.setTranslateY(projectileImage.getTranslateY()+(int)(vector.getY()/vector.distance(0,0)*bulletSpeed));

    }

    public void renewTarget(Point2D a){
        projectileImage.setRotate(-new Point2D(a.getX()-projectileImage.getTranslateX() , a.getY()-projectileImage.getTranslateY()).angle(1,0));
        this.vector = new Point2D(a.getX()-projectileImage.getTranslateX() , a.getY()-projectileImage.getTranslateY());
    }
}
