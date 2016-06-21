package main;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import methodoptimization.MainWindowController;

public class Main extends Application {
    private MainWindowController controller;
    private FXMLLoader loader;
    
    @Override
    public void start(Stage stage) {
        loader = new FXMLLoader(MainWindowController.class.getResource("MainWindow.fxml"));
        try {
            Parent root = loader.load();
        
            Scene scene = new Scene(root);

            stage.setTitle("Java Method Optimozation v1.0");
            controller = (MainWindowController)loader.getController();
            controller.setStage(stage);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
