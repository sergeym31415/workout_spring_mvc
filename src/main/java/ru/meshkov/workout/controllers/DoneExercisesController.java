package ru.meshkov.workout.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.meshkov.workout.exceptions.AthleteNotFoundException;
import ru.meshkov.workout.exceptions.DoneExerciseNotFoundException;
import ru.meshkov.workout.models.DoneExercise;
import ru.meshkov.workout.services.DoneExerciseService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/done_exercises")
public class DoneExercisesController {
    private final DoneExerciseService doneExerciseService;

    @Autowired
    public DoneExercisesController(DoneExerciseService doneExerciseService) {
        this.doneExerciseService = doneExerciseService;
    }

    @ExceptionHandler(DoneExerciseNotFoundException.class)
    public ResponseEntity<String> handle() {
        return ResponseEntity.internalServerError().body("Done exercise not found");
    }


    @GetMapping({"","/"})
    public String index(Model model) {
        List<DoneExercise> doneExerciseList = doneExerciseService.findAll();
        model.addAttribute("doneExerciseList", doneExerciseList);
        return "done_exercises/index";
    }
}
