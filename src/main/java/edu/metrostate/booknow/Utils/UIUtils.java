package edu.metrostate.booknow.Utils;

import edu.metrostate.booknow.Controllers.RestaurantBoxController;
import edu.metrostate.booknow.Models.Restaurant;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

/**
 * The UIUtils class provides utility methods for UI-related tasks in a JavaFX application.
 */

public class UIUtils {

    private static final String pathToRestaurantBoxFXML = "/edu/metrostate/booknow/RestaurantBox.fxml";

    private UIUtils() {
    }

    /**
     * Populates the given ComboBox with a list of items and selects the first item if the list is not empty.
     *
     * @param comboBox the ComboBox to populate with items
     * @param items the list of items to set in the ComboBox
     */
    public static <T> void populateComboBox(ComboBox<T> comboBox, List<T> items) {
        comboBox.setItems(FXCollections.observableArrayList(items));
        if (!items.isEmpty()) {
            comboBox.getSelectionModel().selectFirst();
        }
    }

    /**
     * Creates and returns a VBox containing the details of the specified Restaurant.
     * Loads the UI elements from an FXML file and sets the restaurant data.
     *
     * @param restaurant the Restaurant object containing details to be displayed
     * @return VBox containing the restaurant details, or null if an error occurs during loading
     */
    public static VBox createRestaurantBox(Restaurant restaurant) {
        try {
            FXMLLoader loader = new FXMLLoader(UIUtils.class.getResource(pathToRestaurantBoxFXML));
            VBox restaurantBox = loader.load();
            RestaurantBoxController controller = loader.getController();
            controller.setRestaurantData(restaurant);
            return restaurantBox;
        } catch (IOException e) {
            showErrorAlert("Error Loading Restaurant Box", "Failed to load restaurant details.");
            return null;
        }
    }

    /**
     * Switches the current scene to a new one specified by the provided FXML file URL.
     *
     * @param url the URL of the FXML file to load
     * @param event the ActionEvent that triggered the scene switch
     */
    public static void switchScene(URL url, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            setScene(window, parent);
        } catch (IOException e) {
            showErrorAlert("Scene Switch Error", "Failed to switch scenes.");
        }
    }

    /**
     * Sets the scene of the specified window to the provided parent node, preserving the window's
     * dimensions and maximization state.
     *
     * @param window the Stage object representing the main window
     * @param parent the Parent node to set as the new scene
     */
    private static void setScene(Stage window, Parent parent) {
        boolean isMaximized = window.isMaximized();
        double currentWidth = window.getWidth();
        double currentHeight = window.getHeight();
        Scene scene = new Scene(parent, currentWidth, currentHeight);
        window.setScene(scene);
        if (isMaximized) {
            window.setMaximized(true);
        } else {
            window.setWidth(currentWidth);
            window.setHeight(currentHeight);
        }
        window.show();
    }

    /**
     * Displays an image in the specified ImageView component.
     *
     * @param imagePath The path to the image file to be displayed.
     * @param imageView The ImageView component where the image will be displayed.
     */
    public static void displayImage(String imagePath, ImageView imageView) {
        if (imagePath == null || imagePath.isEmpty()) {
            System.err.println("Image path is null or empty.");
            return;
        }
        try {
            Image restaurantImage = new Image(UIUtils.class.getResource("/" + imagePath).toExternalForm());
            imageView.setImage(restaurantImage);
        } catch (NullPointerException e) {
            System.err.println("Image not found: " + imagePath);
        }
    }

    /**
     * Handles the login process and switches the scene or shows an error alert based on the login result.
     *
     * @param event An ActionEvent that triggered the login process.
     * @param loginSuccess A boolean indicating whether the login was successful.
     * @param successPath A String representing the path to the FXML file to load if the login is successful.
     * @param failureMessage A String containing the message to be displayed if the login fails.
     */
    public static void handleLogin(ActionEvent event, boolean loginSuccess, String successPath, String failureMessage) {
        if (loginSuccess) {
            switchScene(UIUtils.class.getResource(successPath), event);
        } else {
            showErrorAlert("Login Failed", failureMessage);
        }
    }

    /**
     * Displays an alert with the specified type, title, and message.
     *
     * @param alertType the type of the alert (e.g., INFORMATION, ERROR, etc.)
     * @param title     the title of the alert dialog
     * @param message   the message to be displayed in the alert dialog
     */
    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays an informational alert with the specified title and message.
     *
     * @param title the title of the alert dialog
     * @param message the message to be displayed in the alert dialog
     */
    public static void showInfoAlert(String title, String message) {
        showAlert(Alert.AlertType.INFORMATION, title, message);
    }

    /**
     * Displays an error alert with the specified title and message.
     *
     * @param title the title of the alert dialog
     * @param message the message to be displayed in the alert dialog
     */
    public static void showErrorAlert(String title, String message) {
        showAlert(Alert.AlertType.ERROR, title, message);
    }

    /**
     * Displays a success alert with the specified title and message.
     *
     * @param title the title of the alert dialog
     * @param message the message to be displayed in the alert dialog
     */
    public static void showSuccessAlert(String title, String message) {
        showAlert(Alert.AlertType.INFORMATION, title, message);
    }

    /**
     * Logs the specified SQL exception and shows an error alert to the user.
     *
     * @param e       the SQLException that occurred
     * @param message a custom message to log along with the exception details
     */
    public static void showSQLException(SQLException e, String message) {
        // Log the SQL exception
        System.err.println(message + ": " + e.getMessage());
        // Show an alert to the user with a general message
        showErrorAlert("Database Error", "An error occurred while accessing the database. Please try again.");
    }

}