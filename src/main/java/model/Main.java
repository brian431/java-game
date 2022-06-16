package model;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import scenes.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application {

    public static Stage stage;
    //    public static StageSelect menu = new StageSelect();
    public static Level1 level1;
    public static Level2 level2;
    public static Level3 level3;
    public static MainMenu Mainmenu;
    public static Tutorial tutorial;

    public static Settlement settlement;
    public static HashMap<KeyCode, Boolean> KeyCodes = new HashMap<>();
    public static ArrayList<Node> standables = new ArrayList<>();
    public static ArrayList<Projectile> projectiles = new ArrayList<>();
    //Settlement element
    public static boolean win = Boolean.FALSE;
    public static int remainHealth = 3;
    public static long startTime;
    public static long endTime;

    static {
        try {
            Mainmenu = new MainMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Main.stage = stage;
        stage.setResizable(false);
        stage.setTitle("Hello!");
        stage.setScene(Mainmenu.scene);
        stage.show();
    }

}