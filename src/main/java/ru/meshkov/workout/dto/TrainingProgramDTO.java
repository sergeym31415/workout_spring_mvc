package ru.meshkov.workout.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import ru.meshkov.workout.models.Athlete;
import ru.meshkov.workout.models.SetExercise;

import java.util.ArrayList;
import java.util.List;

public class TrainingProgramDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "program name should not be empty")
    private String programName;

    private List<Athlete> athletes;

    private List<SetExercise> setOfExercises = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public List<Athlete> getAthletes() {
        return athletes;
    }

    public void setAthletes(List<Athlete> athletes) {
        this.athletes = athletes;
    }

    public List<SetExercise> getSetOfExercises() {
        return setOfExercises;
    }

    public void setSetOfExercises(List<SetExercise> setOfExercises) {
        this.setOfExercises = setOfExercises;
    }
}
