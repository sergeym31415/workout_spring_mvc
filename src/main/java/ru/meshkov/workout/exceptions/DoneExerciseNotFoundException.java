package ru.meshkov.workout.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DoneExerciseNotFoundException extends RuntimeException {
    public DoneExerciseNotFoundException(String message) {
        super(message);
    }
}
