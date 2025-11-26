package ru.meshkov.workout.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AthleteNotCreatedException extends RuntimeException {
    public AthleteNotCreatedException(String message) {
        super(message);
    }
}
