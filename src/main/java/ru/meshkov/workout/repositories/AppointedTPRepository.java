package ru.meshkov.workout.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.meshkov.workout.models.AppointedTP;
import ru.meshkov.workout.models.Athlete;
import ru.meshkov.workout.models.TrainingProgram;

import java.util.List;

@Repository
public interface AppointedTPRepository extends JpaRepository<AppointedTP, Integer> {
    List<AppointedTP> findAllByAthlete(Athlete athlete);

    AppointedTP findFirstByAthleteAndTrainingProgramAndEndDateIsNull(Athlete athlete,
                                                                     TrainingProgram trainingProgram);
}
