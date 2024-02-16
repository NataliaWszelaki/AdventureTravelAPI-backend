package com.crud.adventuretravel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AttractionNotFoundException.class)
    public ResponseEntity<Object> handleAttractionNotFoundException (AttractionNotFoundException exception) {
        return new ResponseEntity<>("Attraction with given ID doesn't exist.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Object> handleCustomerNotFoundException (CustomerNotFoundException exception) {
        return new ResponseEntity<>("Customer with given ID doesn't exist.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<Object> handleReservationNotFoundException (ReservationNotFoundException exception) {
        return new ResponseEntity<>("Reservation with given ID doesn't exist.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TourNotFoundException.class)
    public ResponseEntity<Object> handleTourNotFoundException (TourNotFoundException exception) {
        return new ResponseEntity<>("Tour with given ID doesn't exist.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AttractionAlreadyExistsException.class)
    public ResponseEntity<Object> handleAttractionAlreadyExistsException (AttractionAlreadyExistsException exception) {
        return new ResponseEntity<>("Attraction with given ID already exist.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<Object> handleCustomerAlreadyExistsException (CustomerAlreadyExistsException exception) {
        return new ResponseEntity<>("Customer with given ID already exist.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TourAlreadyExistsException.class)
    public ResponseEntity<Object> handleTourAlreadyExistsException (TourAlreadyExistsException exception) {
        return new ResponseEntity<>("Tour with given ID already exist.", HttpStatus.NOT_FOUND);
    }
}
