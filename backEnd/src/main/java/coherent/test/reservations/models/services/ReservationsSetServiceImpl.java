package coherent.test.reservations.models.services;

import coherent.test.reservations.models.entity.Reservation;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservationsSetServiceImpl implements ReservationSetService{

    @Autowired
    ReservationStatefulStateService reservationsStatefulStateService;
    private Set<Reservation> reservations;
    private Integer nextId = 1;

    public Set<Reservation> getReservations() {
        //We order the set before
        Set<Reservation> orderedSet = new TreeSet<>(Comparator.comparing(Reservation::getId));
        orderedSet.addAll(reservations);
        return orderedSet;
    }

    @Override
    public void addReservation(Reservation reservation){
        reservation.setId(nextId++);
        reservations.add(reservation);
    }

    @Override
    public Reservation updateReservation(Integer reservationId, Reservation reservation){
        Reservation updatedReservation = findReservationById(reservationId);
        if(updatedReservation == null){
            return null;
        }

        updatedReservation.setClientFullName(reservation.getClientFullName());
        updatedReservation.setRoomNumber(reservation.getRoomNumber());
        updatedReservation.setReservationDates(reservation.getReservationDates());
        return updatedReservation;

    }

    @Override
    public Reservation findReservationById(Integer id){
        Reservation reservationFound = null;
        for (Reservation reservation : reservations) {
            if(reservation.getId() == id){
                reservationFound = reservation;
                break;
            }
        }
        return reservationFound;
    }

    @Override
    public List<LocalDate> DatesOfReservationAlreadyTaken(Integer id,Reservation reservation){
        List<LocalDate> datesToReservate = reservation.getReservationDates();
        List<LocalDate> datesTaken = new ArrayList<>();

        //we get all the dates that are assigned to that room
        //if we are making a post the reservation Id would be null and that works perfect
        Set<LocalDate> datesOfReservationsAtThatRoom = getDatesWithDistinctIdByRoom(id,reservation.getRoomNumber());
        //If there is no any other reservation at that room we return our empty list
        if(datesOfReservationsAtThatRoom.size()==0){
            return datesTaken;
        }
        //Otherwise we return our date list for any use
        for(LocalDate date: datesToReservate){
            if(datesOfReservationsAtThatRoom.contains(date)){
                datesTaken.add(date);
            }
        }
        return datesTaken;

    }

    //Gets all dates, without counting the id of the reservation (only apply on update)
    private Set<LocalDate> getDatesWithDistinctIdByRoom(Integer id,Integer roomNumber){
        return reservations.stream().
                filter(reservation1 -> reservation1.getRoomNumber().equals(roomNumber) && !(reservation1.getId().equals(id))).
                flatMap(reservation1 -> reservation1.getReservationDates().stream()).
                collect(Collectors.toSet());

    }

    @PostConstruct
    private void initializeStatus() throws Exception {
        reservations = reservationsStatefulStateService.loadFromStateFile();
        setInitialId();
    }

    private void setInitialId(){
        //If the array is 0, the initial value should be 1,so we don´t do anything
        if(reservations.size()>0){
            Reservation maxIdValueReservation = reservations.stream().max(Comparator.comparing(Reservation::getId)).get();
            nextId = maxIdValueReservation.getId()+1;
        }
    }

    @PreDestroy
    private void saveReservations() throws IOException {
        reservationsStatefulStateService.saveToStateFile(reservations);
    }


}
