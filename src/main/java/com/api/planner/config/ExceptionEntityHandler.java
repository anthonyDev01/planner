package com.api.planner.config;

import com.api.planner.entity.trip.exception.TripNotFoundExeception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionEntityHandler {
    @ExceptionHandler(TripNotFoundExeception.class)
    public ResponseEntity handlerTripNotFound(TripNotFoundExeception exeception){
        String errorMensage = "Viagem não encontrada";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMensage);
    }

}
