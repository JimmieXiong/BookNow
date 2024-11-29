package edu.metrostate.booknow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BookNowApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BookNowApplication.class.getResource("LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1400, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("BookNow");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}