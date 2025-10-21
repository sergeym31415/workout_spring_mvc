package ru.meshkov.workout.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.meshkov.workout.models.Exercise;
import ru.meshkov.workout.services.ExercisesService;

import java.util.List;

@Controller
@RequestMapping("/exercises")
public class ExercisesController {
    private final ExercisesService exercisesService;

    @Autowired
    public ExercisesController(ExercisesService exercisesService) {
        this.exercisesService = exercisesService;
    }

    @GetMapping()
    public String index(Model model) {
        List<Exercise> exercises = exercisesService.findAll();
        for (Exercise exercise : exercises) {
            System.out.println(exercise);
        }
        model.addAttribute("exercises", exercises);
        return "exercises/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Exercise exercise = exercisesService.findOne(id);
        model.addAttribute("exercise", exercise);
        return "exercises/show";
    }

    @PostMapping()
    public String newExercise(@ModelAttribute("exercise") @Valid Exercise exercise, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "exercises/new";
        }
        exercisesService.save(exercise);
        return "redirect:/exercises";
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("exercise") Exercise exercise) {
        return "exercises/new";
    }

}
