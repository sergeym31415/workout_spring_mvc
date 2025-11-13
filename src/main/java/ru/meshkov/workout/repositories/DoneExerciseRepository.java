package ru.meshkov.workout.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.meshkov.workout.models.DoneExercise;

@Repository
public interface DoneExerciseRepository extends JpaRepository<DoneExercise, Integer> {
}
