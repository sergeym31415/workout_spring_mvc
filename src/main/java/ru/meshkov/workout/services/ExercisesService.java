package ru.meshkov.workout.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.meshkov.workout.models.Exercise;
import ru.meshkov.workout.repositories.ExercisesRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ExercisesService {
    private final ExercisesRepository exercisesRepository;

    @Autowired
    public ExercisesService(ExercisesRepository exercisesRepository) {
        this.exercisesRepository = exercisesRepository;
    }

    public List<Exercise> findAll() {
        return exercisesRepository.findAll();
    }

    public Exercise findOne(int id) {
       Optional<Exercise> exercise = exercisesRepository.findById(id);
        return exercise.orElse(null);
    }

    @Transactional
    public void save(Exercise exercise) {
        exercisesRepository.save(exercise);
    }
}
