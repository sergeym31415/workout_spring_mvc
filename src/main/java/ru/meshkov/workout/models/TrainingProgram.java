package ru.meshkov.workout.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "training_program")
public class TrainingProgram {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "program_name")
    @NotEmpty(message = "program name should not be empty")
    private String programName;

//    @OneToMany(mappedBy = "trainingProgram")
//    private List<Athlete> athletes;

    @OneToMany(mappedBy = "trainingProgram")
    private List<SetExercise> setOfExercises = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

   // public List<Athlete> getAthletes() {
   //     return athletes;
  //  }

  //  public void setAthletes(List<Athlete> athletes) {
   //     this.athletes = athletes;
  //  }

    public List<SetExercise> getSetOfExercises() {
        return setOfExercises;
    }

    public void setSetOfExercises(List<SetExercise> setOfExercises) {
        this.setOfExercises = setOfExercises;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    @Override
    public String toString() {
        return "TrainingProgram{" +
                "id=" + id +
                ", programName='" + programName + '\'' +
                '}';
    }

    public TrainingProgram() {
    }
}
