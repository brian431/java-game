package scenes;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import model.Main;

public class MainMenu {
    final int WIDTH = 1366;
    final int Height = 768;
    public Scene scene;
    public Pane pane;
    public HBox hbox;


    public MainMenu() {
        pane = new Pane();
        scene = new Scene(pane, WIDTH, Height);
        createHBox();
    }

    void createHBox() {
        hbox = new HBox(20);
        hbox.setPrefSize(1000, 500);
        createLevelButton("Level1");
        createLevelButton("Level2");
        createLevelButton("Level3");
        hbox.setAlignment(Pos.CENTER);
        pane.getChildren().add(hbox);
        hbox.setLayoutX(200);
    }

    void createLevelButton(String name) {
        Button startLevel = new Button(name);
        startLevel.setPrefSize(200, 200);

        startLevel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (name.equals("Level1")) {
                    Main.level1 = new Level1();
                    Main.stage.setScene(Main.level1.scene);
                } else if (name.equals("Level2")) {
                    Main.level2 = new Level2();
                    Main.stage.setScene(Main.level2.scene);
                } else if (name.equals("Level3")) {
                    Main.level3 = new Level3();
                    Main.stage.setScene(Main.level3.scene);
                }

            }
        });
        hbox.getChildren().add(startLevel);
    }
}
