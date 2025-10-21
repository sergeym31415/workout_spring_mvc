package ru.meshkov.workout.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.meshkov.workout.models.Athlete;

import java.util.Optional;

@Repository
public interface AthletesRepository extends JpaRepository<Athlete, Integer> {
    Optional<Athlete> findByLogin(String login);
}
