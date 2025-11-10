package ru.meshkov.workout.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ru.meshkov.workout.models.Exercise;
import ru.meshkov.workout.models.TrainingProgram;

import java.time.DayOfWeek;

public class SetExerciseDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min=2, max = 100, message = "Name of set of exercise should beb etween 2 and 100")
    private String name;

    //Подходов
    @Min(value = 1, message = "Approache should not be less than 1")
    private int approache;

    @Min(value = 1, message = "Repeats should not be less than 1")
    private int countRepeats;

    @Min(value = 0, message = "Weight should not be less than 0")
    private double weight;

    @Min(value = 1, message = "Day of week should not be less than 1")
    @Max(value = 7, message = "Day of week should not be greater than 7")
    private int dayOfWeek;

    public String getDayOfWeekInString() {
        String day = DayOfWeek.of(dayOfWeek).name();
        return day;
    }

    @NotNull
    private Exercise exercise;

    private TrainingProgram trainingProgram;

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

    public int getApproache() {
        return approache;
    }

    public void setApproache(int approache) {
        this.approache = approache;
    }

    public int getCountRepeats() {
        return countRepeats;
    }

    public void setCountRepeats(int countRepeats) {
        this.countRepeats = countRepeats;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public TrainingProgram getTrainingProgram() {
        return trainingProgram;
    }

    public void setTrainingProgram(TrainingProgram trainingProgram) {
        this.trainingProgram = trainingProgram;
    }
}
