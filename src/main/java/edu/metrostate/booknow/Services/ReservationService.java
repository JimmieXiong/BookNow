package edu.metrostate.booknow.Services;

import edu.metrostate.booknow.DAO.ReservationDAO;
import edu.metrostate.booknow.Models.Reservation;
import javafx.collections.ObservableList;

public class ReservationService {

    private final ReservationDAO reservationDAO;

    public ReservationService(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    public ObservableList<Reservation> getUserReservations(String username) {
        return reservationDAO.getUserReservations(username);
    }

    public void cancelReservation(int reservationId) {
        System.out.println("ReservationService: Canceling reservation with ID: " + reservationId);
        reservationDAO.cancelReservation(reservationId);
    }

    public Reservation getReservationById(int reservationId) {
        return reservationDAO.getReservationById(reservationId);
    }

    public boolean checkReservationHasReview(int reservationId) {
        return reservationDAO.hasReview(reservationId);
    }
}