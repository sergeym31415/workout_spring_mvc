package ru.meshkov.workout.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.meshkov.workout.dto.AthleteDTO;
import ru.meshkov.workout.models.Athlete;
import ru.meshkov.workout.services.AthletesService;
import ru.meshkov.workout.validators.AthleteValidator;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private  final AthletesService athletesService;
    private final ModelMapper modelMapper;
    private final AthleteValidator athleteValidator;

    @Autowired
    public AuthController(AthletesService athletesService,
                          ModelMapper modelMapper,
                          AthleteValidator athleteValidator) {
        this.athletesService = athletesService;
        this.modelMapper = modelMapper;
        this.athleteValidator = athleteValidator;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("athlete") Athlete athlete) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("athlete") @Valid AthleteDTO athleteDTO, BindingResult bindingResult) {
        Athlete athlete = modelMapper.map(athleteDTO, Athlete.class);
        athleteValidator.validate(athlete, bindingResult);
        if ((bindingResult.hasErrors())) {
            return "auth/registration";
        }
        athletesService.save(athlete);
        return "redirect:/auth/login";
    }
}
