package ru.meshkov.workout.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.meshkov.workout.models.Exercise;

@Repository
public interface ExercisesRepository extends JpaRepository<Exercise, Integer> {
}
