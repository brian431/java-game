package scenes;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import model.Main;

import java.io.IOException;

import static javafx.scene.control.ContentDisplay.GRAPHIC_ONLY;

public class MainMenu {
    final int WIDTH = 1366;
    final int Height = 768;
    public Scene scene;
    public Pane pane;
    public VBox Vbox;
    //private Label Title = new Label("奇怪的動作遊戲");
    Image TitleImage = new Image("Title.png");
    ImageView Title = new ImageView(TitleImage);

    public MainMenu() throws IOException {

        pane = new Pane();
        Title.relocate(250,75);
        pane.getChildren().add(Title);

        pane.setBackground(new Background(new BackgroundImage(new Image("MainMenu.png"), BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT)));
        scene = new Scene(pane, WIDTH, Height);
        createVBox();
        pane.getChildren().add(Vbox);
    }


    void createVBox() {
        Vbox = new VBox(30);
        Vbox.setPrefSize(1000,500);
        //hbox.setPrefSize(1000, 500);
        HBox Hbox = new HBox(10);
        //hbox.setAlignment(Pos.CENTER);
        //pane.getChildren().add(hbox);
        //hbox.setLayoutX(200);
        Vbox.getChildren().add(createLevelButton("Start",317,100));
        Hbox.getChildren().addAll(createLevelButton("Tutorial",170 ,100),createLevelButton("Leave",120,100));
        Vbox.getChildren().add(Hbox);
        Vbox.relocate(490,333);

    }

    Button createLevelButton(String name , int width , int height) {
        Button startLevel = new Button("");
        startLevel.setPrefSize(width, height);
        startLevel.setContentDisplay(GRAPHIC_ONLY);
        startLevel.setStyle("-fx-background-color: transparent");

        if(name.equals("Start")) {
            ImageView view = new ImageView(new Image("Start.png"));
            view.setFitWidth(width);
            view.setPreserveRatio(false);
            view.setFitHeight(height);
            startLevel.setGraphic(view);
        }else if(name.equals("Tutorial")) {
            ImageView view = new ImageView(new Image("Tutorial.png"));
            view.setFitWidth(width);
            view.setPreserveRatio(false);
            view.setFitHeight(height);
            startLevel.setGraphic(view);
        }else if(name.equals("Leave")) {
            ImageView  view = new ImageView(new Image("Exit.png"));
            view.setFitWidth(width);
            view.setPreserveRatio(false);
            view.setFitHeight(height);
            startLevel.setGraphic(view);
        }
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
                } else if (name.equals("Start")) {
                    Main.level1 = new Level1();
                    Main.stage.setScene(Main.level1.scene);


                } else if (name.equals("Tutorial")) {
                    Main.tutorial = new Tutorial();
                    Main.stage.setScene(Main.tutorial.scene);
                } else if (name.equals("Leave")) {
                    Main.stage.close();
                }

            }
        });
        return startLevel;
    }
}
