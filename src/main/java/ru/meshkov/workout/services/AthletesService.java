package ru.meshkov.workout.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.meshkov.workout.models.Athlete;
import ru.meshkov.workout.repositories.AthletesRepository;
import ru.meshkov.workout.utils.AthleteIsAlreadyExistsException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AthletesService {
    private final AthletesRepository athletesRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AthletesService(AthletesRepository athletesRepository, PasswordEncoder passwordEncoder) {
        this.athletesRepository = athletesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Athlete> findAll() {
        return athletesRepository.findAll();
    }

    public Athlete findOne(int id) {
        Optional<Athlete> athlete = athletesRepository.findById(id);
        return athlete.orElse(null);
    }

    public Optional<Athlete> findByLogin(String login) {
        Optional<Athlete> athlete = athletesRepository.findByLogin(login);
        return athlete;
    }

    public void loadAthleteByLogin(String login) throws AthleteIsAlreadyExistsException {
        Optional<Athlete> athlete = athletesRepository.findByLogin(login);
        if (athlete.isEmpty()) {
            throw new AthleteIsAlreadyExistsException("Athlete is already exist");
        }
    }

    @Transactional
    public void save(Athlete athlete) {
        String encodedPassword = passwordEncoder.encode(athlete.getPassword());
        athlete.setPassword(encodedPassword);
        athlete.setRole("USER");
        athletesRepository.save(athlete);
    }
}
