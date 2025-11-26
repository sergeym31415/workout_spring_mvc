package ru.meshkov.workout;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.meshkov.workout.controllers.RestAthletesController;
import ru.meshkov.workout.dto.AthleteDTO;
import ru.meshkov.workout.models.Athlete;
import ru.meshkov.workout.services.AthletesService;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class RestAthletesControllerTest {
    @Mock
    AthletesService athletesService;



    @InjectMocks
    RestAthletesController restAthletesController;

    @Test
    void whenGetAllThenCountOfAthletesGreater0() {
        List<Athlete> athletes = List.of(new Athlete("login1", "pass1", "user1", new Date(2000)),
                new Athlete("login2", "pass2", "user2", new Date(3000)));
        doReturn(athletes).when(athletesService).findAll();
        List<Athlete> responseAthletes = this.restAthletesController.getAthletes();

        Assertions.assertNotNull(responseAthletes);
        Assertions.assertEquals(athletes, responseAthletes);
        Assertions.assertEquals(2, responseAthletes.size());
    }

//    @Test
//    void whenGetAthleteByIdThenOk() {
//        Athlete athlete = new Athlete(7, "logun1", "pass1", "user1", new Date());
//        doReturn(athlete).when(athletesService).findOne(7);
//        AthleteDTO responseAthleteDto = this.restAthletesController.getAthleteById(7);
//
//    }


}
