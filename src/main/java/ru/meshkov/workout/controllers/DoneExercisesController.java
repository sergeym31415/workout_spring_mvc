package ru.meshkov.workout.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.meshkov.workout.models.DoneExercise;
import ru.meshkov.workout.services.DoneExerciseService;

import java.util.List;

@Controller
@RequestMapping("/done_exercises")
public class DoneExercisesController {
    private final DoneExerciseService doneExerciseService;

    @Autowired
    public DoneExercisesController(DoneExerciseService doneExerciseService) {
        this.doneExerciseService = doneExerciseService;
    }

    @GetMapping
    public String index(Model model) {
        List<DoneExercise> doneExerciseList = doneExerciseService.findAll();
        model.addAttribute("doneExerciseList", doneExerciseList);
        return "done_exercises/index";
    }
}
