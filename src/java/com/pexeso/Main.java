package com.pexeso;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application{

  @Override
  public void start(Stage primaryStage) throws Exception{
    // main content
    Parent root = FXMLLoader.load(getClass().getResource("playground-view.fxml"));
    StackPane stackPane = new StackPane();
    stackPane.getChildren().add(root);

    // primaryStage -> outermost window
    // boardScene -> properties of the window
    primaryStage.setTitle("Pexeso");
    primaryStage.setOnCloseRequest(e -> Platform.exit());

    // playBoard size
    Scene boardScene = new Scene(stackPane, 1512,980);

    boardScene.getStylesheets().add(getClass().getResource("/com/pexeso/css/styles.css").toExternalForm()); // adding css to the sceneadding css to the scene
    primaryStage.setScene(boardScene);
    primaryStage.show();
  }
  public static void main(String[] args) {
    launch(args);
  }
}

