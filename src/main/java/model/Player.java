package model;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Player {
    public int blood = 3;
    public ImageView playerImage;
    public Rectangle hitbox;
    public static String STANDING= "-fx-background-color: transparent;-fx-background-image: url(/images/cuphead.png)";



    public Player() {
        hitbox = new Rectangle(500, 500);
        hitbox.setStyle(STANDING);
        //playerImage.setImage(new Image("/images/cuphead.png"));
    }


}
