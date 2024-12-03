package edu.metrostate.booknow.Controllers;

import edu.metrostate.booknow.DAO.ReservationDAO;
import edu.metrostate.booknow.DAO.ReviewDAO;
import edu.metrostate.booknow.Utils.DBConnection;
import edu.metrostate.booknow.Models.Reservation;
import edu.metrostate.booknow.Services.ReservationService;
import edu.metrostate.booknow.Services.ReviewService;
import edu.metrostate.booknow.Utils.UIUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class CreateReviewController {

    private int reservationId;
    private Reservation reservation;
    @FXML
    private Label lbl_welcome;
    @FXML
    private Label lbl_restaurantName;
    @FXML
    private Label lbl_dateOfExperience;
    @FXML
    private ComboBox<Integer> combo_rating;
    @FXML
    private TextArea txt_reviewComment;

    private final DBConnection dbConnection;
    private final ReservationService reservationService;
    private final ReviewService reviewService;

    public CreateReviewController() {
        this.dbConnection = new DBConnection();
        this.reservationService = new ReservationService(new ReservationDAO(new DBConnection()));
        this.reviewService = new ReviewService(new ReviewDAO(dbConnection));
    }

    @FXML
    public void initialize() {
        lbl_welcome.setText("Welcome, " + UIUtil.USER);
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
        initializeReviewForm(reservationId);
    }

    private void initializeReviewForm(int reservationId) {
        reservation = reservationService.getReservationById(reservationId);
        if (reservation != null) {
            lbl_restaurantName.setText(reservation.getRestaurantName());
            lbl_dateOfExperience.setText(reservation.getReservationDate().toString());
        } else {
            UIUtil.displayAlert("Error", "Reservation not found.");
        }
    }

    public void onSubmitReviewClickAction(ActionEvent event) {
        Integer rating = combo_rating.getValue();
        String feedback = txt_reviewComment.getText();

        try {
            String resultMessage = reviewService.validateAndSubmitReview(UIUtil.USER, reservation.getRestaurantId(), reservation.getReservationId(), rating, feedback, reservation.getReservationDate());
            if (resultMessage.equals("Success")) {
                UIUtil.displayAlert("Success", "Review successfully submitted!");
            } else {
                UIUtil.displayAlert("Error", resultMessage);
            }
        } catch (Exception e) {
            UIUtil.displayAlert("Error", e.getMessage());
        }
    }

    public void onSearchRestaurantsClickAction(ActionEvent event) {
        UIUtil.displayScene(getClass().getResource("/edu/metrostate/booknow/BookNowView.fxml"), event);
    }
}