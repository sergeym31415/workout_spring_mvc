package ru.meshkov.workout.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.meshkov.workout.models.Athlete;
import ru.meshkov.workout.models.SetExercise;
import ru.meshkov.workout.models.TrainingProgram;

import java.util.List;
import java.util.Optional;

@Repository
public interface SetsExercisesRepository extends JpaRepository<SetExercise, Integer> {
    List<SetExercise> findByTrainingProgramIsNull();
    List<SetExercise> findByTrainingProgramOrderByDayOfWeekAsc(TrainingProgram trainingProgram);

    List<SetExercise> findByTrainingProgram(TrainingProgram trainingProgram);

    @Query(value = "SELECT * FROM set_exercises WHERE id_training_program = :idTrainingProgram or id_training_program is null", nativeQuery = true)
    List<SetExercise> findByIdTrainingProgramIsNotBusy(@Param("idTrainingProgram") int idTrainingProgram);

    @Modifying
    @Query(value = "UPDATE set_exercises SET id_training_program=null WHERE id_training_program = :idTrainingProgram", nativeQuery = true)
    void setIdTrainingProgramNullByIdTrainingProgram(@Param("idTrainingProgram") int idTrainingProgram);


}
