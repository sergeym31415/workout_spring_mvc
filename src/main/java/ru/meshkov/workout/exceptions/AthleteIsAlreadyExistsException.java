package ru.meshkov.workout.exceptions;

public class AthleteIsAlreadyExistsException extends RuntimeException {
    public AthleteIsAlreadyExistsException(String msg) {
        super(msg);
    }
}
