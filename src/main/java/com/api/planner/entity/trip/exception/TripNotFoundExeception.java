package com.api.planner.entity.trip.exception;

public class TripNotFoundExeception extends RuntimeException{
    public TripNotFoundExeception(String message){
        super(message);
    }
}
