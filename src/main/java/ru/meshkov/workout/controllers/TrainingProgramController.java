package ru.meshkov.workout.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.meshkov.workout.models.SetExercise;
import ru.meshkov.workout.models.TrainingProgram;
import ru.meshkov.workout.services.SetsExercisesService;
import ru.meshkov.workout.services.TrainingProgramService;

import java.util.List;

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
        return "training_programs/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id//,
                       //@ModelAttribute("setExercice") SetExercise setExercise
    ) {
        TrainingProgram trainingProgram = trainingProgramService.findById(id);
        List<SetExercise> sets_exercises = setsExercisesService.findAllEmpty();
        model.addAttribute("trainingProgram", trainingProgram);
        model.addAttribute("sets_exercises", sets_exercises);
        return "training_programs/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("trainingProgram") TrainingProgram trainingProgram,
                         //@ModelAttribute("setExercice") SetExercise setExercice,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "training_programs/edit";
        }

        trainingProgram = trainingProgramService.update(id, trainingProgram);
        setsExercisesService.setTrainingProgramForListOfSetExercises(trainingProgram.getSetOfExercises(), trainingProgram);
//        SetExercise setExerciceNew = setsExercisesService.findOne(setExercice.getId());
//        setExerciceNew.setTrainingProgram(trainingProgram);
//        List<SetExercise> list = trainingProgram.getSetOfExercises();
//        list.add(setExerciceNew);
//        trainingProgram.setSetOfExercises(list);
//        setsExercisesService.save(setExerciceNew);
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
