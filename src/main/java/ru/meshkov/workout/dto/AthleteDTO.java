package ru.meshkov.workout.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import ru.meshkov.workout.models.TrainingProgram;
import ru.meshkov.workout.utils.UsefulFunctions;

import java.util.Date;

public class AthleteDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Login could not be empty")
    @Size(min = 3, message = "Login should be equal or more 3")
    private String login;

    private String password;

    private String role;

    @Size(min = 2, message = "Name should be equal or more 2")
    private String name;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;

    @NotNull(message = "should be filled")
    private Double bodyWeight;

    @NotNull(message = "should be filled")
    private Integer bodyHeight;

    private TrainingProgram trainingProgram;

    public TrainingProgram getTrainingProgram() {
        return trainingProgram;
    }

    public void setTrainingProgram(TrainingProgram trainingProgram) {
        this.trainingProgram = trainingProgram;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Double getBodyWeight() {
        return bodyWeight;
    }

    public void setBodyWeight(Double bodyWeight) {
        this.bodyWeight = bodyWeight;
    }

    public Integer getBodyHeight() {
        return bodyHeight;
    }

    public void setBodyHeight(Integer bodyHeight) {
        this.bodyHeight = bodyHeight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBodyMassIndex() {
        if(bodyHeight == null || bodyWeight == null || bodyHeight == 0 || bodyWeight == 0) {
            return -1;
        }
        double bmi = bodyWeight / ((bodyHeight / 100.0) * (bodyHeight / 100.0));
        return UsefulFunctions.round(bmi, 2);
    }
}
