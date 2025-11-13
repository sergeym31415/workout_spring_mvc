package ru.meshkov.workout.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.meshkov.workout.models.Athlete;
import ru.meshkov.workout.models.TrainingProgram;
import ru.meshkov.workout.repositories.AthletesRepository;
import ru.meshkov.workout.utils.AthleteIsAlreadyExistsException;
import ru.meshkov.workout.utils.AthleteNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AthletesService {
    private final AthletesRepository athletesRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppointedTPService appointedTPService;
    private final TrainingProgramService trainingProgramService;

    @Autowired
    public AthletesService(AthletesRepository athletesRepository,
                           PasswordEncoder passwordEncoder,
                           AppointedTPService appointedTPService,
                           TrainingProgramService trainingProgramService) {
        this.athletesRepository = athletesRepository;
        this.passwordEncoder = passwordEncoder;
        this.appointedTPService = appointedTPService;
        this.trainingProgramService = trainingProgramService;
    }

    public List<Athlete> findAll() {
        return athletesRepository.findAll();
    }

    public Optional<Athlete> findOne(int id) throws AthleteNotFoundException {
        Optional<Athlete> athlete = athletesRepository.findById(id);
        return athlete;
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

        boolean isNewAthlete = athlete.getId() == 0;
        if (!isNewAthlete) {
            TrainingProgram oldTrainingProgram = athletesRepository.findById(athlete.getId()).get().getTrainingProgram();
            if (oldTrainingProgram != null && athlete.getTrainingProgram()!=oldTrainingProgram) {
                appointedTPService.endOldTrainingProgram(oldTrainingProgram, athlete);
            }
            if(athlete.getTrainingProgram()!=null && athlete.getTrainingProgram() != oldTrainingProgram) {
                appointedTPService.startNewTrainingProgram(athlete.getTrainingProgram(), athlete);
            }
        }
        athletesRepository.save(athlete);
    }


}
