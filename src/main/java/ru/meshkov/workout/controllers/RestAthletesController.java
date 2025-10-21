package ru.meshkov.workout.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.meshkov.workout.models.Athlete;
import ru.meshkov.workout.services.AthletesService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestAthletesController {
    private final AthletesService athletesService;

    @Autowired
    public RestAthletesController(AthletesService athletesService) {
        this.athletesService = athletesService;
    }

    @GetMapping("/athletes")
    public List<Athlete> getAthletes() {
        return athletesService.findAll();
    }

    @GetMapping("/athletes/{id}")
    public Athlete getAthleteById(@PathVariable("id") int id) {
        return athletesService.findOne(id);
    }
}
