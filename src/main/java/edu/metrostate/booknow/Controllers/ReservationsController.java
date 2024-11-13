package edu.metrostate.booknow.Controllers;

import edu.metrostate.booknow.Models.Reservation;
import edu.metrostate.booknow.Utils.ReservationUIManager;
import edu.metrostate.booknow.Utils.UIUtil;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ReservationsController {
    @FXML
    private Label lbl_welcome;
    @FXML
    private TableView<Reservation> reservationsTable;
    @FXML
    private TableColumn<Reservation, String> restaurantNameColumn;
    @FXML
    private TableColumn<Reservation, String> reservationDateColumn;
    @FXML
    private TableColumn<Reservation, String> timeSlotColumn;
    @FXML
    private TableColumn<Reservation, String> tableNumberColumn;
    @FXML
    private TableColumn<Reservation, Button> actionColumn;

    private final ReservationUIManager reservationUIManager;

    public ReservationsController() {
        this.reservationUIManager = new ReservationUIManager();
    }

    @FXML
    public void initialize() {
        lbl_welcome.setText("Welcome, " + UIUtil.USER);
        reservationUIManager.setUpTableColumns(restaurantNameColumn, reservationDateColumn, timeSlotColumn, tableNumberColumn, actionColumn);
        loadReservations();
    }

    private void loadReservations() {
        ObservableList<Reservation> reservationList = reservationUIManager.loadReservations(UIUtil.USER);
        reservationsTable.setItems(reservationList);
    }

    public void onSearchRestaurantsClick(ActionEvent event) {
        UIUtil.displayScene(getClass().getResource("/edu/metrostate/booknow/BookNowView.fxml"), event);
    }
}
