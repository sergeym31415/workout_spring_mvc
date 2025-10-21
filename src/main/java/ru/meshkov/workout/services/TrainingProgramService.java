package ru.meshkov.workout.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.meshkov.workout.models.TrainingProgram;
import ru.meshkov.workout.repositories.TrainingProgramRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TrainingProgramService {
    private final TrainingProgramRepository trainingProgramRepository;

    @Autowired
    public TrainingProgramService(TrainingProgramRepository trainingProgramRepository) {
        this.trainingProgramRepository = trainingProgramRepository;
    }

    public TrainingProgram findById(int id) {
        Optional<TrainingProgram> trainingProgram = trainingProgramRepository.findById(id);
        return trainingProgram.orElse(null);
    }

    public List<TrainingProgram> findAll() {
        List<TrainingProgram> trainingPrograms = trainingProgramRepository.findAll();
        return trainingPrograms;
    }

    @Transactional
    public void save(TrainingProgram trainingProgram) {
        trainingProgramRepository.save(trainingProgram);
    }

    @Transactional
    public TrainingProgram update(int id, TrainingProgram trainingProgram) {
        TrainingProgram updatedTrainingProgram = findById(id);
        updatedTrainingProgram.setProgramName(trainingProgram.getProgramName());
        updatedTrainingProgram.setAthletes(trainingProgram.getAthletes());
        updatedTrainingProgram.setSetOfExercises(trainingProgram.getSetOfExercises());
        trainingProgramRepository.save(updatedTrainingProgram);
        return updatedTrainingProgram;
    }
}
