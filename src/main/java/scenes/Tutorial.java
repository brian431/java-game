package scenes;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Main;
import model.Player;

import static model.Main.projectiles;

public class Tutorial {

    final int WIDTH = 1366;
    final int Height = 768;
    public Scene scene;
    public Pane pane;

    public Player player;
    public Rectangle container;
    public Rectangle mainFloor;

    AnimationTimer mainTimer;

    public Tutorial(){

        pane = new Pane();
        scene = new Scene(pane,WIDTH,Height);

        scene.setOnKeyPressed(keyEvent -> Main.KeyCodes.put(keyEvent.getCode(), true));
        scene.setOnKeyReleased(keyEvent -> Main.KeyCodes.put(keyEvent.getCode(), false));
        scene.setOnMousePressed(e -> Main.KeyCodes.put(KeyCode.J, true));
        scene.setOnMouseReleased(e -> Main.KeyCodes.put(KeyCode.J, false));

        mainTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                update();
            }
        };

        mainTimer.start();
        init();

    }
    public void init(){
        pane.setBackground(new Background(new BackgroundImage(new Image("TutorialBackGround.png"), BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT)));
        player = new Player();
        player.onTutorial = true;

        pane.getChildren().add(player.playerImageView);

        mainFloor = new Rectangle(0, 0, WIDTH, 123);
        mainFloor.setFill(Color.TRANSPARENT);
        pane.getChildren().add(mainFloor);
        mainFloor.setTranslateY(703);
        Main.standables.add(mainFloor);

        container = new Rectangle(-200, -200, 2000, 1600);
        container.setFill(Color.TRANSPARENT);
        pane.getChildren().add(container);
    }
    public void update(){
        player.update();
        for (int i = 0; i < projectiles.size(); ++i) {
            projectiles.get(i).move();
            if (!pane.getChildren().contains(projectiles.get(i).projectileImage)) {
                pane.getChildren().add(projectiles.get(i).projectileImage);
            }
            if (!container.contains(projectiles.get(i).projectileImage.getTranslateX(), projectiles.get(i).projectileImage.getTranslateY())) {
                pane.getChildren().remove(projectiles.get(i).projectileImage);
                projectiles.remove(i);
            }
        }
    }
}
