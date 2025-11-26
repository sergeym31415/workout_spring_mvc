package ru.meshkov.workout.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.meshkov.workout.models.Athlete;
import ru.meshkov.workout.models.Record;
import ru.meshkov.workout.repositories.RecordsRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RecordsService {
    private final RecordsRepository recordsRepository;

    @Autowired
    public RecordsService(RecordsRepository recordsRepository) {
        this.recordsRepository = recordsRepository;
    }

    @Transactional
    public void save(Record record) {
        recordsRepository.save(record);
    }

    public int getMaxRepeatsByWeight(int athleteId, int exerciseId, double weight) {
        Optional<Integer> optionalRepeats = recordsRepository.findMaxRepeats(athleteId, exerciseId, weight);
        if (optionalRepeats.isPresent()) {
            return optionalRepeats.get();
        }
        return 0;
    }

    public boolean isCorrect(Record record) {
        boolean isRecord = record.getRepeats() > 0 && record.getRepeats() > getMaxRepeatsByWeight(
                record.getAthlete().getId(), record.getExercise().getId(), record.getWeight());
        return isRecord;
    }

    public List<Record> findAll() {
        return recordsRepository.findAll();
    }

    public List<Record> findAllByAthlete(Athlete athlete) {return recordsRepository.findAllByAthlete(athlete);}

    public Optional<Record> findById(int id) {
        return recordsRepository.findById(id);
    }

}
