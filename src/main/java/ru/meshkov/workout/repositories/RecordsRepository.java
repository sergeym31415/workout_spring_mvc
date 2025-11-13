package ru.meshkov.workout.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.meshkov.workout.models.Record;

import java.util.Optional;

@Repository
public interface RecordsRepository extends JpaRepository<Record, Integer> {
    @Query(value = "SELECT MAX(repeats) FROM records WHERE id_athlete=:athleteId and id_exercise=:exerciseId and weight=:weight", nativeQuery = true)
    Optional<Integer> findMaxRepeats(@Param("athleteId") Integer athleteId,
                                     @Param("exerciseId") Integer exerciseId,
                                     @Param("weight") double weight);
}
