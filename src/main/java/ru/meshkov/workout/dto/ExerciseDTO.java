package ru.meshkov.workout.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import ru.meshkov.workout.models.SetExercise;

import java.util.ArrayList;
import java.util.List;

public class ExerciseDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name should not be empty")
    private String name;

    private String description;

    private List<SetExercise> setExercises = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SetExercise> getSetExercises() {
        return setExercises;
    }

    public void setSetExercises(List<SetExercise> setExercises) {
        this.setExercises = setExercises;
    }
}
