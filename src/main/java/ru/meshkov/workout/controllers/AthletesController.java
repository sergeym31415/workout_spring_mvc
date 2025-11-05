package ru.meshkov.workout.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;


import org.springframework.web.bind.annotation.*;
import ru.meshkov.workout.models.Athlete;
import ru.meshkov.workout.models.TrainingProgram;
import ru.meshkov.workout.services.AthletesService;
import ru.meshkov.workout.services.TrainingProgramService;
import ru.meshkov.workout.validators.AthleteValidator;


import java.util.List;

@Controller
@RequestMapping("/athletes")
public class AthletesController {
    private final AthletesService athletesService;
    private final TrainingProgramService trainingProgramService;
    private final AthleteValidator athleteValidator;

    @Autowired
    public AthletesController(AthletesService athletesService, TrainingProgramService trainingProgramService, AthleteValidator athleteValidator) {
        this.athletesService = athletesService;
        this.trainingProgramService = trainingProgramService;
        this.athleteValidator = athleteValidator;
    }

    @GetMapping()
    public String index(Model model) {
        List<Athlete> athletes = athletesService.findAll();
        model.addAttribute("athletes", athletes);
        return "athletes/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Athlete athlete = athletesService.findOne(id);
        model.addAttribute("athlete", athlete);
        return "athletes/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        Athlete athlete = athletesService.findOne(id);
        List<TrainingProgram> trainingPrograms = trainingProgramService.findAll();
        model.addAttribute("training_programs", trainingPrograms);
        model.addAttribute("athlete", athlete);
        return "athletes/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, Model model,
                         @ModelAttribute("athlete") @Valid Athlete athlete,
                         BindingResult bindingResult) {
        athleteValidator.validate(athlete, bindingResult);
        if (bindingResult.hasErrors()) {
            List<TrainingProgram> trainingPrograms = trainingProgramService.findAll();
            model.addAttribute("training_programs", trainingPrograms);
            return "athletes/edit";
        }
        athletesService.save(athlete);
        return "redirect:/athletes";
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("athlete") Athlete athlete) {
        return "athletes/new";
    }

    @PostMapping()
    public String newAthlete(@ModelAttribute("athlete") @Valid Athlete athlete, BindingResult bindingResult) {
        athleteValidator.validate(athlete, bindingResult);
        if ((bindingResult.hasErrors())) {
            return "athletes/new";
        }
        athletesService.save(athlete);
        return "redirect:/athletes";
    }
}
