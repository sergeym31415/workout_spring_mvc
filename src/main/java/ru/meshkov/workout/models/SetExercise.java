package ru.meshkov.workout.models;

import jakarta.persistence.*;

@Entity
@Table(name = "set_exercises")
public class SetExercise {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    //Подходов
    @Column(name = "approache")
    private int approache;

    @Column(name = "repeats")
    private int countRepeats;

    @Column(name = "weight")
    private double weight;

    @Column(name = "day_of_week")
    private int dayOfWeek;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_exercise")
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
