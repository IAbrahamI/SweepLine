package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("menu.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Voronoi Diagramm");
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}