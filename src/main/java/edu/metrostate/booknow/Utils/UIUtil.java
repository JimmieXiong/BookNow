package edu.metrostate.booknow.Utils;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.function.Consumer;

public class UIUtil {

    public static String USER = "";

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

    public static void displayScene(URL url, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            setScene(window, parent);
        } catch (IOException e) {
            displayAlert("Scene Switch Error", "Failed to switch scenes.");
        }
    }

    public static <T> void displaySceneWithController(URL url, ActionEvent event, Consumer<T> controllerConsumer) {
        try {
            FXMLLoader loader = new FXMLLoader(url);
            Parent parent = loader.load();
            T controller = loader.getController();
            controllerConsumer.accept(controller);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            setScene(window, parent);
        } catch (IOException e) {
            displayAlert("Scene Switch Error", "Failed to switch scenes.");
        }
    }

    public static void displayAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static <T> void populateComboBox(ComboBox<T> comboBox, List<T> items) {
        comboBox.setItems(FXCollections.observableArrayList(items));
        if (!items.isEmpty()) {
            comboBox.getSelectionModel().selectFirst();
        }
    }
}