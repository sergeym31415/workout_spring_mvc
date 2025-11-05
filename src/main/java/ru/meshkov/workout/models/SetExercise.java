package ru.meshkov.workout.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.DayOfWeek;
import java.util.Date;

@Entity
@Table(name = "set_exercises")
public class SetExercise {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min=2, max = 100, message = "Name of set of exercise should beb etween 2 and 100")
    @Column(name = "name")
    private String name;

    //Подходов

    @Column(name = "approache")
    @Min(value = 1, message = "Approache should not be less than 1")
    private int approache;


    @Column(name = "repeats")
    @Min(value = 1, message = "Repeats should not be less than 1")
    private int countRepeats;

    @Column(name = "weight")
    @Min(value = 0, message = "Weight should not be less than 0")
    private double weight;

    @Column(name = "day_of_week")
    @Min(value = 1, message = "Day of week should not be less than 1")
    @Max(value = 7, message = "Day of week should not be greater than 7")
    private int dayOfWeek;

    public String getDayOfWeekInString() {
        String day = DayOfWeek.of(dayOfWeek).name();
        return day;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_exercise")
    @NotNull
    private Exercise exercise;

    @ManyToOne
    @JoinColumn(name = "id_training_program")
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

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public TrainingProgram getTrainingProgram() {
        return trainingProgram;
    }

    public void setTrainingProgram(TrainingProgram trainingProgram) {
        this.trainingProgram = trainingProgram;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public SetExercise() {
    }

    @Override
    public String toString() {
        return "SetExercise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", approache=" + approache +
                ", countRepeats=" + countRepeats +
                ", weight=" + weight +
                ", dayOfWeek=" + dayOfWeek +
                ", exercise=" + exercise +
                '}';
    }
}
