package ru.meshkov.workout.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import ru.meshkov.workout.utils.UsefulFunctions;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "athlete")
public class Athlete {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "login")
    @NotEmpty(message = "Login could not be empty")
    @Size(min = 3, message = "Login should be equal or more 3")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "name")
    @Size(min = 2, message = "Name should be equal or more 2")
    private String name;

    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;

    @Column(name = "body_weight")
    @NotNull(message = "should be filled")
    private Double bodyWeight;

    @Column(name = "body_height")
    @NotNull(message = "should be filled")
    private Integer bodyHeight;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_training_program")
    private TrainingProgram trainingProgram;

    public Athlete() {
    }

    public Athlete(String login, String password, String name, Date birthDate) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.birthDate = birthDate;
    }

    public Athlete(int id, String login, String password, String name, Date birthDate) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.birthDate = birthDate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public TrainingProgram getTrainingProgram() {
        return trainingProgram;
    }

    public void setTrainingProgram(TrainingProgram trainingProgram) {
        this.trainingProgram = trainingProgram;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Athlete athlete = (Athlete) o;
        return id == athlete.id && Objects.equals(login, athlete.login) && Objects.equals(password, athlete.password) && Objects.equals(name, athlete.name) && Objects.equals(birthDate, athlete.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, name, birthDate);
    }

    @Override
    public String toString() {
        return "Athlete{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
