package ru.meshkov.workout.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.meshkov.workout.models.SetExercise;
import ru.meshkov.workout.models.TrainingProgram;
import ru.meshkov.workout.repositories.SetsExercisesRepository;
import ru.meshkov.workout.utils.DayOfWeekRus;

import java.time.DayOfWeek;
import java.util.*;

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

    public List<SetExercise> findAllNotBusy(int programId) {
        return setsExercisesRepository.findByIdTrainingProgramIsNotBusy(programId);
    }


    public SetExercise findOne(int id) {
        Optional<SetExercise> setExercise = setsExercisesRepository.findById(id);
        return setExercise.orElse(null);
    }

    @Transactional
    public void setTrainingProgramForListOfSetExercises(List<SetExercise> list, TrainingProgram trainingProgram) {
        setsExercisesRepository.setIdTrainingProgramNullByIdTrainingProgram(trainingProgram.getId());
        setsExercisesRepository.setIdTrainingProgramForList(trainingProgram.getId(),
                list.stream()
                .map(s -> s.getId())
                .toList());
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

    public Map<String, List<SetExercise>> getDictOfSchedule(TrainingProgram trainingProgram) {
        Map<String, List<SetExercise>> map = new LinkedHashMap<>();
        List<SetExercise> setExerciseList = setsExercisesRepository.findByTrainingProgramOrderByDayOfWeekAsc(trainingProgram);
        for (SetExercise setExercise : setExerciseList) {
            String dayOfWeek = DayOfWeekRus.valueOf(DayOfWeek.of(setExercise.getDayOfWeek()).name()).getTitle();
            if (map.containsKey(dayOfWeek)) {
                List list = map.get(dayOfWeek);
                list.add(setExercise);
            } else {
                List<SetExercise> list = new ArrayList<>();
                list.add(setExercise);
                map.put(dayOfWeek, list);
            }
        }

        return map;
    }
}
