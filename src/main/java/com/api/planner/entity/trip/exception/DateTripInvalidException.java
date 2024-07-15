package com.api.planner.entity.trip.exception;

public class DateTripInvalidException extends RuntimeException{
    public DateTripInvalidException(String mensagem){
        super(mensagem);
    }
}
