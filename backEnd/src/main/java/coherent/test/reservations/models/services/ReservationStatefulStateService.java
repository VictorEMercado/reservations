package coherent.test.reservations.models.services;

import coherent.test.reservations.models.entity.Reservation;

import java.io.IOException;
import java.util.Set;

public interface ReservationStatefulStateService {
    Set<Reservation> loadFromStateFile() throws IOException;
    void saveToStateFile(Set<Reservation> reservations) throws IOException;

}
