package ru.meshkov.workout.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.meshkov.workout.dto.AthleteDTO;
import ru.meshkov.workout.models.Athlete;
import ru.meshkov.workout.services.AthletesService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestAthletesController {
    private final AthletesService athletesService;
    private final ModelMapper modelMapper;

    @Autowired
    public RestAthletesController(AthletesService athletesService, ModelMapper modelMapper) {
        this.athletesService = athletesService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/athletes")
    public List<Athlete> getAthletes() {
        return athletesService.findAll();
    }

    @GetMapping("/athletes/{id}")
    public AthleteDTO getAthleteById(@PathVariable("id") int id) {
        Athlete athlete = athletesService.findOne(id);
        AthleteDTO athleteDTO = convertToAthleteDTO(athlete);
        return athleteDTO;
    }

    private AthleteDTO convertToAthleteDTO(Athlete athlete) {
        return modelMapper.map(athlete, AthleteDTO.class);
    }
}
