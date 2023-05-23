package coherent.test.reservations.models.services;

import coherent.test.reservations.models.entity.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ReservationSetService {

    Set<Reservation> getReservations();
    void addReservation(Reservation reservation);
    Reservation updateReservation(Integer reservationId, Reservation reservation);
    Reservation findReservationById(Integer id);
    List<LocalDate> DatesOfReservationAlreadyTaken(Integer id,Reservation reservation);

}
