package ru.meshkov.workout.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.meshkov.workout.models.Athlete;
import ru.meshkov.workout.models.SetExercise;

import java.util.List;
import java.util.Optional;

@Repository
public interface SetsExercisesRepository extends JpaRepository<SetExercise, Integer> {
    List<SetExercise> findByTrainingProgramIsNull();
}
