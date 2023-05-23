package coherent.test.reservations.controllers;

import coherent.test.reservations.models.entity.Reservation;
import coherent.test.reservations.models.services.ReservationSetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@CrossOrigin(origins = {"${frontEnd.url}"})
public class ReservationController {

    @Autowired
    ReservationSetService reservationsSetService;

    @GetMapping("/reservations")
    public ResponseEntity<?> getAllReservations(){
        Map<String, Object> response = new HashMap<>();

        Set<Reservation> reservations = reservationsSetService.getReservations();

        response.put("registers",reservations);
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);

    }

    @PostMapping("/reservation")
    public ResponseEntity<?> createReservation(@Valid @RequestBody Reservation reservation, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = getErrorsFromReservation(null,reservation,result);

        if(errors!=null){
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        reservationsSetService.addReservation(reservation);

        response.put("message", "The reservation was created successfully");
        response.put("register", reservation);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);



    }

    @PutMapping("/reservation/{id}")
    public ResponseEntity<?> updateReservation(@PathVariable Integer id, @Valid @RequestBody Reservation reservation, BindingResult result){
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = getErrorsFromReservation(id,reservation,result);

        if(errors!=null){
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        Reservation updatedReservation = reservationsSetService.updateReservation(id,reservation);

        if(updatedReservation == null){
            response.put("message", "Reservation not found");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        response.put("message", "The reservation was updated successfully");
        response.put("register", updatedReservation);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }

    public Map<String, String> getErrorsFromReservation(Integer id, Reservation reservation, BindingResult result){
        //If we get errors from Validation
        Map<String, String> errors = new HashMap<>();
        if(result.hasErrors()) {
            result.getFieldErrors().forEach(error->{
                errors.put(error.getField(),error.getDefaultMessage());
            });
            return errors;
        }

        //If someone wants to reserve an already reserved room
        List<LocalDate> datesTaken = reservationsSetService.DatesOfReservationAlreadyTaken(id,reservation);
        if(datesTaken.size()>0){
            String error = "The following dates are already taken ";
            for(LocalDate date:datesTaken){
                error+=" <"+date+"> ";
            }
            errors.put("reservationDates",error);
            return errors;
        }

        return null;

    }


}
