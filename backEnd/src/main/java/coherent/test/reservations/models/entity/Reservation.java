package coherent.test.reservations.models.entity;


import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public class Reservation {

    private Integer id;
    @NotBlank(message = "Name is mandatory")
    private String clientFullName;
    @Positive
    @NotNull(message = "RoomNumber is mandatory")
    private Integer roomNumber;
    @NotEmpty(message = "Reservation List cannot be empty")
    private List<LocalDate> reservationDates;

    public Reservation() {
    }

    public Reservation(String clientFullName, Integer roomNumber, List<LocalDate> reservationDates) {
        this.clientFullName = clientFullName;
        this.roomNumber = roomNumber;
        this.reservationDates = reservationDates;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientFullName() {
        return clientFullName;
    }

    public void setClientFullName(String clienteFullName) {
        this.clientFullName = clienteFullName;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public List<LocalDate> getReservationDates() {
        return reservationDates;
    }

    public void setReservationDates(List<LocalDate> reservationDates) {
        this.reservationDates = reservationDates;
    }
}
