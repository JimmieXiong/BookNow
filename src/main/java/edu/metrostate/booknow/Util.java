package edu.metrostate.booknow;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Util {

    // Holds the username of the current user
    private static String currentUser = "";

    public static String getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(String user) {
        currentUser = user;
    }

    // Changes the current scene to a new scene specified by the given URL.
    public static void displayScene(URL url, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(url);
        Parent parent = loader.load();

        // Get the current stage (window)
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Check if the window is maximized before switching scenes
        boolean isMaximized = window.isMaximized();

        // Get the current window size (if it's not maximized)
        double currentWidth = window.getWidth();
        double currentHeight = window.getHeight();

        // Create the new scene with the same dimensions
        Scene scene = new Scene(parent, currentWidth, currentHeight);

        // Set the scene to the window
        window.setScene(scene);

        // Restore the maximized state if it was maximized
        if (isMaximized) {
            window.setMaximized(true);
        } else {
            // If not maximized, preserve the previous window size
            window.setWidth(currentWidth);
            window.setHeight(currentHeight);
        }
        // Show the updated window
        window.show();
    }

    // Displays alert dialog
    public static void displayAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}