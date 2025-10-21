package ru.meshkov.workout.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.meshkov.workout.models.SetExercise;
import ru.meshkov.workout.services.ExercisesService;
import ru.meshkov.workout.services.SetsExercisesService;

import java.util.List;

@Controller
@RequestMapping("/sets_exercises")
public class SetsExercisesController {
    private final SetsExercisesService setsExercisesService;
    private final ExercisesService exercisesService;

    @Autowired
    public SetsExercisesController(SetsExercisesService setsExercisesService, ExercisesService exercisesService) {
        this.setsExercisesService = setsExercisesService;
        this.exercisesService = exercisesService;
    }

    @GetMapping()
    public String index(Model model) {
        List<SetExercise> setExerciseList = setsExercisesService.findAll();
        model.addAttribute("sets", setExerciseList);
        return "sets_exercises/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        SetExercise setExercise = setsExercisesService.findOne(id);
        model.addAttribute("setExercise", setExercise);
        return "sets_exercises/show";
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("setExercise") SetExercise setExercise, Model model) {
        model.addAttribute("exercises", exercisesService.findAll());
        return "sets_exercises/new";
    }

    @PostMapping()
    public String newSetExercise(@ModelAttribute("setExercise") SetExercise setExercise, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "sets_exercises/new";
        }
        setsExercisesService.save(setExercise);
        return "redirect:/sets_exercises";
    }
}
