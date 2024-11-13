package edu.metrostate.booknow.Services;

import edu.metrostate.booknow.DAO.ReservationDAO;
import edu.metrostate.booknow.Models.Reservation;
import javafx.collections.ObservableList;

public class ReservationService {
    private final ReservationDAO reservationDAO;

    public ReservationService(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    /**
     * Retrieves all reservations for a given username.
     *
     * @param username The username of the user.
     * @return A list of reservations for the user.
     */
    public ObservableList<Reservation> getUserReservations(String username) {
        return reservationDAO.getUserReservations(username);
    }

    /**
     * Cancels a reservation by its ID.
     *
     * @param reservationId The ID of the reservation to cancel.
     */
    public void cancelReservation(int reservationId) {
        reservationDAO.cancelReservation(reservationId);
    }

    /**
     * Retrieves a reservation by its ID.
     *
     * @param reservationId The ID of the reservation.
     * @return The reservation object, or null if not found.
     */
    public Reservation getReservationById(int reservationId) {
        return reservationDAO.getReservationById(reservationId);
    }

    /**
     * Checks if a reservation has an associated review.
     *
     * @param reservationId The ID of the reservation.
     * @return True if a review exists for the reservation, false otherwise.
     */
    public boolean checkReservationHasReview(int reservationId) {
        return reservationDAO.checkReservationHasReview(reservationId);
    }
}
