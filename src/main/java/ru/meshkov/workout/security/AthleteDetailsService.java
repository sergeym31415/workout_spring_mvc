package ru.meshkov.workout.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.meshkov.workout.models.Athlete;
import ru.meshkov.workout.repositories.AthletesRepository;

import java.util.Optional;


@Service
public class AthleteDetailsService implements UserDetailsService {
    @Autowired
    private AthletesRepository athletesRepository;


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Athlete> athlete = athletesRepository.findByLogin(login);

        if(athlete.isEmpty()) {
            throw new UsernameNotFoundException("User not find");
        }
        return new AthleteDetails(athlete.get());
    }
}
