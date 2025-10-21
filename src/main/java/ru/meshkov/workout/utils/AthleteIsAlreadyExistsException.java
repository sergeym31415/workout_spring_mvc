package ru.meshkov.workout.utils;

public class AthleteIsAlreadyExistsException extends RuntimeException {
    public AthleteIsAlreadyExistsException(String msg) {
        super(msg);
    }
}
