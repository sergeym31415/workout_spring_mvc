package ru.meshkov.workout.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.meshkov.workout.models.Athlete;
import ru.meshkov.workout.models.SetExercise;
import ru.meshkov.workout.models.TrainingProgram;
import ru.meshkov.workout.repositories.AthletesRepository;
import ru.meshkov.workout.repositories.SetsExercisesRepository;
import ru.meshkov.workout.utils.AthleteIsAlreadyExistsException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class SetsExercisesService {
    private final SetsExercisesRepository setsExercisesRepository;

    @Autowired
    public SetsExercisesService(SetsExercisesRepository setsExercisesRepository) {
        this.setsExercisesRepository = setsExercisesRepository;
    }

    public List<SetExercise> findAll() {
        return setsExercisesRepository.findAll();
    }
    public List<SetExercise> findAllEmpty() {
        return setsExercisesRepository.findByTrainingProgramIsNull();
    }

    public SetExercise findOne(int id) {
        Optional<SetExercise> setExercise = setsExercisesRepository.findById(id);
        return setExercise.orElse(null);
    }

    @Transactional
    public void setTrainingProgramForListOfSetExercises(List<SetExercise> list, TrainingProgram trainingProgram) {
        for (SetExercise setExercise : list) {
            SetExercise s  = findOne(setExercise.getId());
            s.setTrainingProgram(trainingProgram);
        }
    }

    @Transactional
    public void save(SetExercise setExercise) {
        setsExercisesRepository.save(setExercise);
    }

    @Transactional
    public void update(int id, SetExercise setExercise) {
        SetExercise updatedSetExercise = findOne(id);
        updatedSetExercise.setExercise(setExercise.getExercise());
        updatedSetExercise.setApproache(setExercise.getApproache());
        updatedSetExercise.setCountRepeats(setExercise.getCountRepeats());
        updatedSetExercise.setName(setExercise.getName());
        updatedSetExercise.setDayOfWeek(setExercise.getDayOfWeek());
        updatedSetExercise.setWeight(setExercise.getWeight());
        setsExercisesRepository.save(updatedSetExercise);
    }
}
