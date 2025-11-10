package ru.meshkov.workout.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.meshkov.workout.dto.ExerciseDTO;
import ru.meshkov.workout.dto.SetExerciseDTO;
import ru.meshkov.workout.models.Exercise;
import ru.meshkov.workout.models.SetExercise;
import ru.meshkov.workout.services.ExercisesService;
import ru.meshkov.workout.services.SetsExercisesService;

import java.util.List;

@Controller
@RequestMapping("/sets_exercises")
public class SetsExercisesController {
    private final SetsExercisesService setsExercisesService;
    private final ExercisesService exercisesService;
    private final ModelMapper modelMapper;

    @Autowired
    public SetsExercisesController(SetsExercisesService setsExercisesService, ExercisesService exercisesService,
                                   ModelMapper modelMapper) {
        this.setsExercisesService = setsExercisesService;
        this.exercisesService = exercisesService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public String index(Model model) {
        List<SetExercise> setExerciseList = setsExercisesService.findAll();
        List<SetExerciseDTO> setExerciseDTOs = setExerciseList.stream()
                .map(s -> modelMapper.map(s, SetExerciseDTO.class))
                .toList();
        model.addAttribute("sets", setExerciseDTOs);
        return "sets_exercises/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        SetExercise setExercise = setsExercisesService.findOne(id);
        SetExerciseDTO setExerciseDTO = modelMapper.map(setExercise, SetExerciseDTO.class);
        model.addAttribute("setExercise", setExerciseDTO);
        return "sets_exercises/show";
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("setExercise") SetExercise setExercise, Model model) {
        model.addAttribute("exercises", exercisesService.findAll()
                .stream()
                .map(e -> modelMapper.map(e, ExerciseDTO.class))
                .toList());
        return "sets_exercises/new";
    }

    @PostMapping()
    public String newSetExercise(@ModelAttribute("setExercise") @Valid SetExerciseDTO setExerciseDTO,
                                 BindingResult bindingResult,
                                 Model model) {
        SetExercise setExercise = modelMapper.map(setExerciseDTO, SetExercise.class);
        if (bindingResult.hasErrors()) {
            model.addAttribute("exercises", exercisesService.findAll()
                    .stream()
                    .map(e -> modelMapper.map(e, Exercise.class))
                    .toList());
            return "sets_exercises/new";
        }
        setsExercisesService.save(setExercise);
        return "redirect:/sets_exercises";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        SetExercise setExercise = setsExercisesService.findOne(id);
        SetExerciseDTO setExerciseDTO = modelMapper.map(setExercise, SetExerciseDTO.class);
        model.addAttribute("setExercise", setExerciseDTO);
        model.addAttribute("exercises", exercisesService.findAll()
                .stream()
                .map(e -> modelMapper.map(e, Exercise.class))
                .toList());
        return "sets_exercises/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         Model model,
                         @ModelAttribute("setExercise") @Valid SetExerciseDTO setExerciseDTO,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("exercises", exercisesService.findAll()
                    .stream()
                    .map(e -> modelMapper.map(e, Exercise.class))
                    .toList());
            return "sets_exercises/edit";
        }
        SetExercise setExercise = modelMapper.map(setExerciseDTO, SetExercise.class);
        setsExercisesService.save(setExercise);
        return "redirect:/sets_exercises";
    }
}
