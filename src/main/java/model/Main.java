package model;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import scenes.Level1;
import scenes.Level2;
import scenes.Level3;
import scenes.MyMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application {

    public static Stage stage;
    public static MyMenu menu = new MyMenu();
    public static Level1 level1;
    public static Level2 level2;
    public static Level3 level3;

    public static HashMap<KeyCode, Boolean> KeyCodes = new HashMap<>();
    public static ArrayList<Node> standables = new ArrayList<>();
    public static ArrayList<Projectile> projectiles = new ArrayList<>();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Main.stage = stage;
        stage.setResizable(false);
        stage.setTitle("Hello!");
        stage.setScene(menu.scene);
        stage.show();
    }
}