package edu.metrostate.booknow.Utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * Utility class for switching scenes in a JavaFX application.
 */

public class SwitchSceneUtil {

    // Switches the current scene to a new scene specified by the given URL
    public static void switchScene(URL url, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(url);
        Parent parent = loader.load();

        // Get the current stage (window)
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Store the current maximized state and window dimensions
        boolean isMaximized = window.isMaximized();
        double currentWidth = window.getWidth();
        double currentHeight = window.getHeight();

        // Create the new scene with the same dimensions
        Scene scene = new Scene(parent, currentWidth, currentHeight);
        window.setScene(scene);

        // Restore the previous maximized state or dimensions
        if (isMaximized) {
            window.setMaximized(true);
        } else {
            window.setWidth(currentWidth);
            window.setHeight(currentHeight);
        }

        // Show the updated window
        window.show();
    }
}
