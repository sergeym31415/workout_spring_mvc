package ru.meshkov.workout.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.meshkov.workout.models.DoneExercise;
import ru.meshkov.workout.models.Record;
import ru.meshkov.workout.repositories.DoneExerciseRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DoneExerciseService {
    private final DoneExerciseRepository doneExerciseRepository;
    private final RecordsService recordsService;
    private final ModelMapper modelMapper;

    @Autowired
    public DoneExerciseService(DoneExerciseRepository doneExerciseRepository,
                               RecordsService recordsService,
                               ModelMapper modelMapper) {
        this.doneExerciseRepository = doneExerciseRepository;
        this.recordsService = recordsService;
        this.modelMapper = modelMapper;
    }

    public List<DoneExercise> findAll() {
        return doneExerciseRepository.findAll();
    }

    public Optional<DoneExercise> findById(int id) {
        return doneExerciseRepository.findById(id);
    }

    @Transactional
    public void save(DoneExercise doneExercise) {
        doneExerciseRepository.save(doneExercise);
        Record record = Record.getRecordFromDoneExercise(doneExercise);
        if (recordsService.isCorrect(record)) {
            recordsService.save(record);
        }
    }
}
