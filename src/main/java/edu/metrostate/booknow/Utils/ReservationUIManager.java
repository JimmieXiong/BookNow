package edu.metrostate.booknow.Utils;

import edu.metrostate.booknow.Controllers.CreateReviewController;
import edu.metrostate.booknow.DAO.ReservationDAO;
import edu.metrostate.booknow.Models.Reservation;
import edu.metrostate.booknow.Services.ReservationService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;

public class ReservationUIManager {
    private final ReservationService reservationService;

    public ReservationUIManager() {
        this.reservationService = new ReservationService(new ReservationDAO(new DBConnection()));
    }

    public void setUpTableColumns(TableColumn<Reservation, String> restaurantNameColumn, TableColumn<Reservation, String> reservationDateColumn, TableColumn<Reservation, String> timeSlotColumn, TableColumn<Reservation, String> tableNumberColumn, TableColumn<Reservation, Button> actionColumn) {
        restaurantNameColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantName"));
        reservationDateColumn.setCellValueFactory(new PropertyValueFactory<>("reservationDate"));
        timeSlotColumn.setCellValueFactory(new PropertyValueFactory<>("timeSlot"));
        tableNumberColumn.setCellValueFactory(new PropertyValueFactory<>("tableNumber"));
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("actionButton"));
    }

    public void loadReservations(String username, ObservableList<Reservation> reservationsList) {
        reservationsList.clear();
        reservationsList.addAll(reservationService.getUserReservations(username));
        for (Reservation reservation : reservationsList) {
            reservation.setActionButton(createActionButton(reservation, reservationsList));
        }
    }

    private Button createActionButton(Reservation reservation, ObservableList<Reservation> reservationsList) {
        Button actionButton;
        if (!reservation.checkReservationDateTimePassed()) {
            actionButton = new Button("Cancel Reservation");
            actionButton.setOnAction(e -> {
                System.out.println("Cancel Reservation button clicked for reservation ID: " + reservation.getReservationId());
                cancelReservation(reservation.getReservationId(), reservationsList);
            });
        } else if (reservationService.checkReservationHasReview(reservation.getReservationId())) {
            actionButton = new Button("View Your Review");
            actionButton.setOnAction(e -> UIUtil.displayScene(getClass().getResource("/edu/metrostate/booknow/ReviewView.fxml"), e));
        } else {
            actionButton = new Button("Leave a Review");
            actionButton.setOnAction(e -> handleLeaveReviewAction(e, reservation.getReservationId()));
        }
        actionButton.setStyle("-fx-font-weight: bold; -fx-text-fill: #003580;");
        return actionButton;
    }

    public void cancelReservation(int reservationId, ObservableList<Reservation> reservationsList) {
        System.out.println("ReservationUIManager: Canceling reservation with ID: " + reservationId);
        reservationService.cancelReservation(reservationId);

        // Remove the canceled reservation from the list
        reservationsList.removeIf(reservation -> reservation.getReservationId() == reservationId);
    }

    private void handleLeaveReviewAction(ActionEvent event, int reservationId) {
        URL url = getClass().getResource("/edu/metrostate/booknow/CreateReviewView.fxml");
        UIUtil.displaySceneWithController(url, event, controller -> {
            ((CreateReviewController) controller).setReservationId(reservationId);
        });
    }
}
