package ru.meshkov.workout.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


@Entity
@Table(name = "records")
public class Record {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_athlete")
    @NotNull
    private Athlete athlete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_exercise")
    @NotNull
    private Exercise exercise;

    @Column(name = "done_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date doneDate;

    @Column(name = "repeats")
    @Min(value = 1, message = "Repeats should not be less than 1")
    private int repeats;

    @Column(name = "weight")
    @Min(value = 0, message = "Weight should not be less than 0")
    private double weight;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public int getRepeats() {
        return repeats;
    }

    public void setRepeats(int repeats) {
        this.repeats = repeats;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Date getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(Date doneDate) {
        this.doneDate = doneDate;
    }

    public static Record getRecordFromDoneExercise(DoneExercise doneExercise) {
        Record record = new Record();
        record.setExercise(doneExercise.getExercise());
        record.setAthlete(doneExercise.getAthlete());
        record.setDoneDate(doneExercise.getDoneDate());
        record.setRepeats(doneExercise.getRepeats());
        record.setWeight(doneExercise.getWeight());
        return record;
    }

}
