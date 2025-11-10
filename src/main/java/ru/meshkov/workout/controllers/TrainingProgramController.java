package ru.meshkov.workout.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.meshkov.workout.dto.SetExerciseDTO;
import ru.meshkov.workout.dto.TrainingProgramDTO;
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
    private final ModelMapper modelMapper;

    @Autowired
    public TrainingProgramController(TrainingProgramService trainingProgramService,
                                     SetsExercisesService setsExercisesService,
                                     ModelMapper modelMapper) {
        this.trainingProgramService = trainingProgramService;
        this.setsExercisesService = setsExercisesService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public String index(Model model) {
        List<TrainingProgram> trainingProgramList = trainingProgramService.findAll();
        List<TrainingProgramDTO> trainingProgramDTOs = trainingProgramList.stream()
                .map(t -> modelMapper.map(t, TrainingProgramDTO.class))
                .toList();
        model.addAttribute("training_programs", trainingProgramDTOs);
        return "training_programs/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        TrainingProgram trainingProgram = trainingProgramService.findById(id);
        TrainingProgramDTO trainingProgramDTO = modelMapper.map(trainingProgram, TrainingProgramDTO.class);
        model.addAttribute("trainingProgram", trainingProgramDTO);
        model.addAttribute("dict", setsExercisesService.getDictOfSchedule(trainingProgram));
        return "training_programs/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        TrainingProgram trainingProgram = trainingProgramService.findById(id);
        List<SetExercise> sets_exercises = setsExercisesService.findAllNotBusy(id);

        TrainingProgramDTO trainingProgramDTO = modelMapper.map(trainingProgram, TrainingProgramDTO.class);
        List<SetExerciseDTO> setExerciseDTOs = sets_exercises.stream()
                .map(s -> modelMapper.map(s, SetExerciseDTO.class))
                .toList();

        model.addAttribute("trainingProgram", trainingProgramDTO);
        model.addAttribute("sets_exercises", setExerciseDTOs);
        return "training_programs/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("trainingProgram") @Valid TrainingProgramDTO trainingProgramDTO,
                         BindingResult bindingResult,
                         Model model,
                         @PathVariable("id") int id) {
        TrainingProgram trainingProgram = modelMapper.map(trainingProgramDTO, TrainingProgram.class);
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
    public String newTrainingProgram(@ModelAttribute("trainingProgram") TrainingProgramDTO trainingProgramDTO,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "training_programs/new";
        }
        TrainingProgram trainingProgram = modelMapper.map(trainingProgramDTO, TrainingProgram.class);
        trainingProgramService.save(trainingProgram);
        return "redirect:/training_programs";
    }
}
