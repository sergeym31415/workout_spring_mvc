package ru.meshkov.workout.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.meshkov.workout.models.Athlete;
import ru.meshkov.workout.models.DoneExercise;

import java.util.List;

@Repository
public interface DoneExerciseRepository extends JpaRepository<DoneExercise, Integer> {
    List<DoneExercise> findAllByAthlete(Athlete athlete);
}
