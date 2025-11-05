package ru.meshkov.workout.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.meshkov.workout.models.SetExercise;
import ru.meshkov.workout.models.TrainingProgram;
import ru.meshkov.workout.services.SetsExercisesService;
import ru.meshkov.workout.services.TrainingProgramService;

import java.util.*;

@Controller
@RequestMapping("/training_programs")
public class TrainingProgramController {
    private final TrainingProgramService trainingProgramService;
    private final SetsExercisesService setsExercisesService;

    @Autowired
    public TrainingProgramController(TrainingProgramService trainingProgramService, SetsExercisesService setsExercisesService) {
        this.trainingProgramService = trainingProgramService;
        this.setsExercisesService = setsExercisesService;
    }

    @GetMapping
    public String index(Model model) {
        List<TrainingProgram> trainingProgramList = trainingProgramService.findAll();
        model.addAttribute("training_programs", trainingProgramList);
        return "training_programs/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        TrainingProgram trainingProgram = trainingProgramService.findById(id);
        model.addAttribute("trainingProgram", trainingProgram);
        model.addAttribute("dict", setsExercisesService.getDictOfSchedule(trainingProgram));
        return "training_programs/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        TrainingProgram trainingProgram = trainingProgramService.findById(id);
        List<SetExercise> sets_exercises = setsExercisesService.findAllNotBusy(id);
        model.addAttribute("trainingProgram", trainingProgram);
        model.addAttribute("sets_exercises", sets_exercises);
        return "training_programs/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("trainingProgram") @Valid TrainingProgram trainingProgram,
                         BindingResult bindingResult,
                         Model model,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("sets_exercises", setsExercisesService.findAllNotBusy(id));
            return "training_programs/edit";
        }
        trainingProgram = trainingProgramService.update(id, trainingProgram);
        setsExercisesService.setTrainingProgramForListOfSetExercises(trainingProgram.getSetOfExercises(), trainingProgram);
        return "redirect:/training_programs";
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("trainingProgram") TrainingProgram trainingProgram) {
        return "training_programs/new";
    }

    @PostMapping
    public String newTrainingProgram(@ModelAttribute("trainingProgram") TrainingProgram trainingProgram,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "training_programs/new";
        }
        trainingProgramService.save(trainingProgram);
        return "redirect:/training_programs";
    }
}
