package ru.meshkov.workout.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.meshkov.workout.models.AppointedTP;
import ru.meshkov.workout.models.Athlete;
import ru.meshkov.workout.models.TrainingProgram;
import ru.meshkov.workout.repositories.AppointedTPRepository;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AppointedTPService {
    private final AppointedTPRepository appointedTPRepository;

    @Autowired
    public AppointedTPService(AppointedTPRepository appointedTPRepository) {
        this.appointedTPRepository = appointedTPRepository;
    }

    public List<AppointedTP> findAll() {
        return appointedTPRepository.findAll();
    }

    public List<AppointedTP> findByAthlete(Athlete athlete) {
        return appointedTPRepository.findAllByAthlete(athlete);
    }

    @Transactional
    public void startNewTrainingProgram(TrainingProgram trainingProgram, Athlete athlete) {
        if(trainingProgram != null) {
            AppointedTP appointedTP = new AppointedTP();
            appointedTP.setTrainingProgram(trainingProgram);
            appointedTP.setAthlete(athlete);
            appointedTP.setStartDate(new Date());

            appointedTPRepository.save(appointedTP);
        }
    }

    @Transactional
    public void endOldTrainingProgram(TrainingProgram trainingProgram, Athlete athlete) {
        AppointedTP appointedTP = appointedTPRepository.findFirstByAthleteAndTrainingProgramAndEndDateIsNull(athlete, trainingProgram);
        appointedTP.setEndDate(new Date());

        appointedTPRepository.save(appointedTP);
    }
}
