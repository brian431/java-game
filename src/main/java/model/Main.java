package model;

import scenes.Level2;
import scenes.Level3;
import scenes.MyMenu;
import scenes.Level1;
import scenes.Level;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static Stage stage;
    public static MyMenu menu = new MyMenu();
    public static Level1 level1;
    public static Level2 level2;
    public static Level3 level3;
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        stage.setResizable(false);
        stage.setTitle("Hello!");

        stage.setScene(menu.scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}